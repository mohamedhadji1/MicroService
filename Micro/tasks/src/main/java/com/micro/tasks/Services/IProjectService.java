package com.micro.tasks.Services;

import com.micro.tasks.Entities.*;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Optional;

public interface IProjectService {
    /******Tasks*****/
    List<Task> getAllTasks();
    Optional<Task> getTaskById(int id);
    Task createTask(Task task);
    Task updateTask(Task task);
    void deleteTask(int id);
    String getAttachmentFilename(int id);
    Resource downloadTaskAttachment(int taskId);
    List<Task> searchTasksByDescription(String keyword);
    List<Task> searchTasksByProgress(String keyword);
    List<Task> searchTasksByDuration(String keyword);
    List<Task> searchTasksBySupervisorName(String keyword);
    List<Task> searchTasksByStudentName(String keyword);

    /*******USER*********/
    List<User> getAllUsers();
    Optional<User> getUserById(int id);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(int id);
    /*******InternShips********/
    List<Internship> getAllinternships();
    Optional<Internship> getinternshipById(int id);
    Internship createInternship(Internship internship);
    Internship updateinternship(Internship internship);
    void deleteinternship(int id);
    /*******Documents*********/
    List<Documents> getAlldocuments();
    Optional<Documents> getdocumentsById(int id);
    Documents createdocuments(Documents documents);
    Documents updatedocuments(Documents documents);
    void deletedocuments(int id);

    /**************Offer******************/
    /*************Company******************/
    List<Company> getAllcompany();

    Optional<Company> getCompanyById(int idComp);

    Company updatecompany(Company company);

    void deletecompany(int idComp);

    /************Monitoring**************/
    List<MonitoringNote> getAllMonitoringNotes();
    Optional<MonitoringNote> getMonitoringNoteById(int id);
    MonitoringNote createMonitoringNote(MonitoringNote monitoringNote);
    MonitoringNote updateMonitoringNote(MonitoringNote monitoringNote);
    void deleteMonitoringNoteById(int id);
    List<MonitoringNote> getMonitoringNotesByStatus(Status status);
    /************Notification**************/
    Notification saveNotification(Notification notification);
    /*****ChatMessage********/
    ChatMessage saveMessage(ChatMessage message);
    List<ChatMessage> getAllMessages();
    List<ChatMessage> getMessagesBetweenSupervisorAndStudent(int supervisorId, int studentId);
    /*****TurnIn********/
    TurnIn submitTurnIn(TurnIn turnIn);

    List<TurnIn> getAllTurnIns();

    TurnIn getTurnInById(int turnInId);

    List<TurnIn> getTurnInsByStudentId(int studentId);
}

