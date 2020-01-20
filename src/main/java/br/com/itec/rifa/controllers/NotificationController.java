package br.com.itec.rifa.controllers;

import br.com.itec.rifa.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/notification")
@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getNotifications(@PathVariable Long id) {
        return new ResponseEntity<>(notificationService.getNotificationsByUserToAvisosPage(id), HttpStatus.OK);
    }

    @GetMapping("/no-read/{id}")
    public ResponseEntity<?> getCountNotificationsNoRead(@PathVariable Long id) {
        return new ResponseEntity<>(notificationService.countNotificationsNoReadByUserId(id), HttpStatus.OK);
    }
}
