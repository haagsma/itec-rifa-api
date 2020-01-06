package br.com.itec.rifa.services;

import br.com.itec.rifa.models.Image;
import br.com.itec.rifa.models.Item;
import br.com.itec.rifa.models.Status;
import br.com.itec.rifa.models.User;
import br.com.itec.rifa.repositories.ImageRepository;
import br.com.itec.rifa.repositories.ItemRepository;
import br.com.itec.rifa.repositories.StatusRepository;
import br.com.itec.rifa.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

    public Page<Item> getList(Pageable pageable) {
        return itemRepository.findAllByStatusTag("EM_SORTEIO",pageable);
    }

    public Item register(Item item) {
        Status status = statusRepository.findByTag("EM_SORTEIO");
        item.setStatus(status);
        return itemRepository.save(item);
    }
    public void uploadItem(Long id, MultipartFile file) throws IOException {
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
    }
}
