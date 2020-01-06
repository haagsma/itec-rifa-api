package br.com.itec.rifa.controllers;

import br.com.itec.rifa.models.Item;
import br.com.itec.rifa.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RequestMapping("/item")
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("")
    public ResponseEntity<?> getList(Pageable pageable) {
        return new ResponseEntity<>(itemService.getList(pageable), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Item item) {
        return new ResponseEntity<>(itemService.register(item), HttpStatus.OK);
    }

    @GetMapping("/sortear/{id}")
    public ResponseEntity<?> sortear(@PathVariable Long id) {
        // @TODO
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("item") Long id, @RequestParam("file") List<MultipartFile> multipartFile) throws Exception {
        multipartFile.forEach(img -> {
            try {
                itemService.uploadItem(id, img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
