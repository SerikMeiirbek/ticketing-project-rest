package com.cydeo.controller;

import com.cydeo.annotation.DefaultExceptionMessage;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.exception.TicketingProjectException;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> getAllUsers(){
        List<UserDTO> userDTOList = userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper("Users are successfully retrieved", userDTOList, HttpStatus.OK));
    }

    @GetMapping("/{userName}")
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> getUserByUserName(@PathVariable("userName") String userName){
        UserDTO user = userService.findByUserName(userName);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully retrieved", user, HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO user){
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("User is created", HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO user){
        userService.update(user);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully updated", user, HttpStatus.OK));
    }

    @DeleteMapping("/{userName}")
    @RolesAllowed("Admin")
    @DefaultExceptionMessage(defaultMessage = "Failed to delete user")
    public ResponseEntity<ResponseWrapper> deleteUserByUserName(@PathVariable("userName") String username) throws TicketingProjectException {
        userService.delete(username);
        return ResponseEntity.ok(new ResponseWrapper("User is deleted", HttpStatus.OK));
        //return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper("User is successfully created",HttpStatus.CREATED));
        //204 - HttpStatus.NO_CONTENT

    }





}
