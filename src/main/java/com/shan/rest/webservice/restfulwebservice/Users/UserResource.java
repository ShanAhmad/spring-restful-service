package com.shan.rest.webservice.restfulwebservice.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
public class UserResource {
    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userDaoService.findAll();
    }
    @GetMapping("/users/{id}")
    public User retrieveSingleUser(@PathVariable Integer id){
        User user = userDaoService.findOne(id);
        if(user == null){
            throw new UserNotFoundException("id: "+id);
        }
        return user;
    }
    @PostMapping("/users")
    public ResponseEntity createNewUser(@RequestBody User user){
        User createduser = userDaoService.save(user);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createduser.getId()).toUri()).build();
    }
}
