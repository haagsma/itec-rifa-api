package br.com.itec.rifa.services;

import br.com.itec.rifa.models.Notification;
import br.com.itec.rifa.models.Status;
import br.com.itec.rifa.repositories.NotificationRepository;
import br.com.itec.rifa.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Transactional
    public List<Notification> getNotificationsByUserToAvisosPage(Long id) {
        Status status = statusRepository.findByTag("LIDO");
        notificationRepository.setStatusByToUserId(status, id);
        return notificationRepository.findAllByToUserIdOrToUserIsNullOrderByIdDesc(id);
    }

    public Integer countNotificationsNoReadByUserId(Long id) {
        return notificationRepository.countByToUserIdOrToUserIsNullAndStatusTagIn(id, Collections.singletonList("NAO_LIDO"));
    }
}
