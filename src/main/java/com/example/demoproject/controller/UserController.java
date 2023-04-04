package com.example.demoproject.controller;

import com.example.demoproject.dto.Response;
import com.example.demoproject.dto.UserRequest;
import com.example.demoproject.model.User1;
import com.example.demoproject.service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/save")
    public ResponseEntity<Response> saveUser(@RequestBody UserRequest request) {
        userService.saveUser(request);
        return new ResponseEntity<>(Response.builder().success(true).build(), HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List> getUsers() {

        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(Response.builder().success(true).build(),HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable long id, @RequestBody UserRequest request) {
        userService.updateUser(id,request);
        return new ResponseEntity<>(Response.builder().success(true).build(),HttpStatus.OK);
    }



}
