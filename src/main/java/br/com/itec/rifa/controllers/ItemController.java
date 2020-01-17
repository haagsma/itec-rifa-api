package br.com.itec.rifa.controllers;

import br.com.itec.rifa.models.Item;
import br.com.itec.rifa.services.ItemService;
import br.com.itec.rifa.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/item")
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/view-app/{id}")
    public ResponseEntity<?> getList(@PathVariable Long id, Pageable pageable) {
        return new ResponseEntity<>(itemService.getList(id, pageable), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Item item) {
        return new ResponseEntity<>(itemService.register(item), HttpStatus.OK);
    }

    @GetMapping("/sortear/{id}")
    public ResponseEntity<?> sortear(@PathVariable Long id) {

        Item item = itemService.sorteio(id);

        if (item == null) return new ResponseEntity<>("Item já foi sorteado!", HttpStatus.OK);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("item") Long id, @RequestParam("file") List<MultipartFile> multipartFile) throws Exception {
        List<String> errors = new ArrayList<>();
        for (MultipartFile img: multipartFile) {
            try {
                if (!itemService.uploadItem(id, img)) errors.add(img.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(errors, HttpStatus.OK);
    }
    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable("id") Long id, HttpServletRequest request) throws MalformedURLException {
        Resource resource = itemService.loadImg(id);
        String contentType = "image/png";

        if (resource == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

}
