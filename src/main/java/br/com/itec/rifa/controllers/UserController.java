package br.com.itec.rifa.controllers;

import br.com.itec.rifa.dto.UserLoginDto;
import br.com.itec.rifa.dto.UserRequestLoginDto;
import br.com.itec.rifa.models.Status;
import br.com.itec.rifa.models.User;
import br.com.itec.rifa.repositories.StatusRepository;
import br.com.itec.rifa.repositories.UserRepository;
import br.com.itec.rifa.services.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    public ResponseEntity<?> getUsers() {
        System.out.println("getUsers");
        List<User> userList = userRepository.findForName();
        for (User user: userList) System.out.println(user);
        return new ResponseEntity<>(HttpStatus.OK);
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
        user.setCredits(new Long(0));
        userRepository.save(user);

        return new ResponseEntity<>(jwtService.encode(user), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody User userRequest) {

        User user = userRepository.findById(userRequest.getId()).get();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody User userRequest) {

        User user = userRepository.findById(userRequest.getId()).get();
        user.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
