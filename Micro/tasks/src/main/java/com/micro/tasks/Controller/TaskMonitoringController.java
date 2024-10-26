package com.micro.tasks.Controller;

import com.micro.tasks.Repositories.TaskRepository;
import com.micro.tasks.Services.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.micro.tasks.Config.AutoIncrementUtil;
@RestController
@RequestMapping("/api/monitoring")
public class TaskMonitoringController {

    @Autowired
    private IProjectService iProjectService;
    @Autowired
    private AutoIncrementUtil autoIncrementUtil;
    @Autowired
    private TaskRepository taskRepository;


}
