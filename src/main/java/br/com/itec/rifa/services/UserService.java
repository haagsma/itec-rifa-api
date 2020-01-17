package br.com.itec.rifa.services;

import br.com.itec.rifa.models.Image;
import br.com.itec.rifa.models.User;
import br.com.itec.rifa.repositories.StatusRepository;
import br.com.itec.rifa.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UserService {

    ModelMapper mapper = new ModelMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private JwtService jwtService;

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public Boolean uploadImgPerfil(Long id, MultipartFile file) throws IOException {
        if (!file.getOriginalFilename().toLowerCase().contains(".png") &&
                !file.getOriginalFilename().toLowerCase().contains(".jpg") &&
                !file.getOriginalFilename().toLowerCase().contains(".jpeg")
        ) return false;

        Path fileStorageLocation = Paths.get("C:/Users/jhaagsma/Pictures/upload/perfil-" + id + ".png");
        Files.copy(file.getInputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
        return true;
    }
    public Resource loadImg(Long id) throws MalformedURLException {
        Path path = Paths.get("C:/Users/jhaagsma/Pictures/upload/perfil-" + id + ".png");
        Resource resource = new UrlResource(path.toUri());
        if(resource.exists()) {
            return resource;
        } else {
            return null;
        }
    }
}
