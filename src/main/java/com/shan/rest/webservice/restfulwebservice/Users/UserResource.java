package com.shan.rest.webservice.restfulwebservice.Users;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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
    public ResponseEntity createNewUser(@Valid @RequestBody User user){
        User createduser = userDaoService.save(user);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createduser.getId()).toUri()).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id){
        User user = userDaoService.delete(id);
        if(user == null){
            throw new UserNotFoundException("id: "+id);
        }
    }

    @GetMapping("/filtering")
    public MappingJacksonValue dynamicFilteringExample(){
        List<User> user = userDaoService.findAll();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name","birthDate");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filterProvider);
        return mapping;
    }
}
