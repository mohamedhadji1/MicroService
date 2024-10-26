package com.micro.tasks.Controller;

import com.micro.tasks.Entities.Notification;
import com.micro.tasks.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import com.micro.tasks.Config.AutoIncrementUtil;

import com.micro.tasks.Repositories.UserRepository;
import com.micro.tasks.Services.IProjectService;
import com.micro.tasks.models.NotificationModel;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {

        @Autowired
        private IProjectService iProjectService;
        @Autowired
        private AutoIncrementUtil autoIncrementUtil;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/notification")
    //@SendTo("/topic/notification")
    public Notification sendNotification(NotificationModel notificationModel) {
        System.out.println(notificationModel);
        User sender = userRepository.findById(notificationModel.senderId())
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User recipient = userRepository.findById(notificationModel.receiverId())
                .orElseThrow(() -> new IllegalArgumentException("Recipient not found"));
        Notification notif = new Notification();
        notif.setSender(sender);
        notif.setReceiver(recipient);
        notif.setDescription(notificationModel.description());
        int id = autoIncrementUtil.getNextSequence("votre_sequence");
        notif.setId(id);
        var savednotif = iProjectService.saveNotification(notif);
        simpMessagingTemplate.convertAndSend("/topic/notification", savednotif);
        return savednotif;
    }
}