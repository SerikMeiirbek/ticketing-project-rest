package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/task")
@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapper> getTasks(){
        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved", taskService.listAllTasks(), HttpStatus.OK));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("taskId") Long taskId){
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully retrieved", taskService.findById(taskId), HttpStatus.OK));
    }

    @PostMapping()
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO task){
        taskService.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Task is created", HttpStatus.CREATED));
    }

    @DeleteMapping("TaskId")
    public ResponseEntity<ResponseWrapper> deleteTasks(@PathVariable Long taskId){
        taskService.delete(taskId);
        return ResponseEntity.ok(new ResponseWrapper("Task is deleted", HttpStatus.OK));
    }

    @PutMapping()
    public ResponseEntity<ResponseWrapper> updateTasks(@RequestBody TaskDTO task){
        taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated", task, HttpStatus.OK));
    }

    @GetMapping("/employee/pending-tasks")
    public ResponseEntity<ResponseWrapper> employeePendingTasks(){
        List<TaskDTO> taskDTOList = taskService.listAllTasksByStatusIsNot(Status.COMPLETE);

        return ResponseEntity.ok(new ResponseWrapper("Task is successfully retrieved", taskDTOList, HttpStatus.OK));
    }

    @PutMapping("/employee/update/")
    public ResponseEntity<ResponseWrapper> employeeUpdateTask(@RequestBody TaskDTO task){
        taskService.updateStatus(task);
        return ResponseEntity.ok(new ResponseWrapper("Task is updated", HttpStatus.OK));
    }

    @GetMapping("/employee/archive")
    public ResponseEntity<ResponseWrapper> employeeArchivedTasks(){
        List<TaskDTO> taskDTO = taskService.listAllTasksByStatus(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks are retrieve", taskDTO, HttpStatus.OK));
    }



}
