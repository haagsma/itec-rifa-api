package br.com.itec.rifa.services;

import br.com.itec.rifa.models.Image;
import br.com.itec.rifa.models.Item;
import br.com.itec.rifa.models.Status;
import br.com.itec.rifa.models.User;
import br.com.itec.rifa.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<Item> getList(Pageable pageable) {
        return itemRepository.findAllByStatusTag("EM_SORTEIO",pageable);
    }

    public Item register(Item item) {
        Status status = statusRepository.findByTag("EM_SORTEIO");
        item.setStatus(status);
        return itemRepository.save(item);
    }
    public Boolean uploadItem(Long id, MultipartFile file) throws IOException {
        // png
        if (!file.getOriginalFilename().toLowerCase().contains(".png") &&
            !file.getOriginalFilename().toLowerCase().contains(".jpg") &&
            !file.getOriginalFilename().toLowerCase().contains(".jpeg")
        ) return false;

        Item item = itemRepository.findById(id).get();
        Integer count = imageRepository.countByItem(item);
        Image image = new Image();
        String name = null;
        if (count > 0) {
            Integer max = imageRepository.findMaxOrderByItem(item);
            image.setOrder(max + 1);
            name = item.getId() + "-" + image.getOrder() + ".png";
        } else {
            image.setOrder(1);
            name = item.getId() + "-1.png";
        }
        image.setItem(item);
        image.setPath("C:/Users/jhaagsma/Pictures/upload/" + name);
        image.setLink("http://localhost/img/" + name);
        Path fileStorageLocation = Paths.get(image.getPath());
        Files.copy(file.getInputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
        imageRepository.save(image);
        return true;
    }

    public Item findItemById(Long id) {
        return itemRepository.findById(id).get();
    }

    public Resource loadImg(Long id) throws MalformedURLException {
        Image image = imageRepository.findById(id).get();
        Path path = Paths.get(image.getPath());
        Resource resource = new UrlResource(path.toUri());
        if(resource.exists()) {
            return resource;
        } else {
            return null;
        }
    }

    public Item sorteio(Long itemId) {
        Item item = itemRepository.findById(itemId).get();

        if (item.getWinner() != null) return null;

        Long winnerId =  ticketRepository.sortTicket(itemId);
        User user = userRepository.findById(winnerId).get();
        Status status = statusRepository.findByTag("SORTEADO");
        item.setStatus(status);
        item.setUpdateDate(new Date());
        item.setWinner(user);
        itemRepository.save(item);
        return item;
    }
}
