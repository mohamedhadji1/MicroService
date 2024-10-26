package com.micro.tasks.Config;

import com.micro.tasks.Entities.*;
import com.micro.tasks.Repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final TaskRepository taskRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final NotificationRepository notificationRepository;
    private final TurnInRepository turnInRepository;
    private final MonitoringNoteRepository monitoringNoteRepository;
    public DatabaseInitializer(
                               UserRepository userRepository,
                               CompanyRepository companyRepository,
                               TaskRepository taskRepository,
                               ChatMessageRepository chatMessageRepository,
                               NotificationRepository notificationRepository,
                               TurnInRepository turnInRepository,
                               MonitoringNoteRepository monitoringNoteRepository
    ) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.taskRepository=taskRepository;
        this.chatMessageRepository=chatMessageRepository;
        this.notificationRepository=notificationRepository;
        this.turnInRepository=turnInRepository;
        this.monitoringNoteRepository=monitoringNoteRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User user = new User();
            userRepository.save(user);
        }

        if (companyRepository.count() == 0) {
            Company company = new Company();
            companyRepository.save(company);
        }

        if (taskRepository.count() == 0) {
            Task task = new Task();
            taskRepository.save(task);
        }if (chatMessageRepository.count() == 0) {
            ChatMessage chatrepo = new ChatMessage();
            chatMessageRepository.save(chatrepo);
        }if (notificationRepository.count() == 0) {
            Notification notification = new Notification();
            notificationRepository.save(notification);
        }if (turnInRepository.count() == 0) {
            TurnIn turnin = new TurnIn();
            turnInRepository.save(turnin);
        }
        if (monitoringNoteRepository.count() == 0) {
            MonitoringNote notes = new MonitoringNote();
            monitoringNoteRepository.save(notes);
        }
    }
}