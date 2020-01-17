package br.com.itec.rifa.controllers;

import br.com.itec.rifa.dto.UserDto;
import br.com.itec.rifa.dto.UserLoginDto;
import br.com.itec.rifa.dto.UserRequestLoginDto;
import br.com.itec.rifa.models.Status;
import br.com.itec.rifa.models.User;
import br.com.itec.rifa.repositories.StatusRepository;
import br.com.itec.rifa.repositories.UserRepository;
import br.com.itec.rifa.services.JwtService;
import br.com.itec.rifa.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RequestMapping("/user")
@RestController
public class UserController {

    ModelMapper mapper = new ModelMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getUsers() {
        System.out.println("getUsers");
        List<User> userList = userRepository.findForName();
        System.out.println("-----------------------------");
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDto userDto = mapper.map(user, UserDto.class);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestLoginDto userRequest) {

        User user = userRepository.findByEmail(userRequest.getEmail());

        if (user == null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        if (!new BCryptPasswordEncoder().matches(userRequest.getPassword(), user.getPassword())) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        UserLoginDto userLoginDto = mapper.map(user, UserLoginDto.class);
        userLoginDto.setToken(jwtService.encode(user));

        return new ResponseEntity<>(userLoginDto, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestLoginDto userRequestLoginDto) {

        if (userRepository.findByEmail(userRequestLoginDto.getEmail()) != null) return new ResponseEntity<>(HttpStatus.FOUND);
        User user = new User();
        mapper.map(userRequestLoginDto, user);
        Status status = statusRepository.findByTag("ATIVO");
        user.setStatus(status);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setCredits(0L);
        userRepository.save(user);
        return new ResponseEntity<>(jwtService.encode(user), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserRequestLoginDto userRequest) {

        User user = userRepository.findById(userRequest.getId()).get();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        System.out.println(userRequest.getPassword());
        if (userRequest.getPassword() != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        }
        userRepository.save(user);
        UserLoginDto userLoginDto = mapper.map(user, UserLoginDto.class);
        userLoginDto.setToken(jwtService.encode(user));
        return new ResponseEntity<>(userLoginDto, HttpStatus.OK);
    }

    @PostMapping("/upload/img-perfil")
    public ResponseEntity<?> uploadImgPerfil(@RequestParam("id") Long id,@RequestParam("img") MultipartFile file) throws IOException {
        List<String> errors = new ArrayList<>();
        if (!userService.uploadImgPerfil(id, file))
            errors.add("A imagem não é aceita pelo nosso sistema!");
        return new ResponseEntity<>(errors, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody User userRequest) {

        User user = userRepository.findById(userRequest.getId()).get();
        user.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable("id") Long id, HttpServletRequest request) throws MalformedURLException {
        Resource resource = userService.loadImg(id);
        String contentType = "image/png";

        if (resource == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

}
