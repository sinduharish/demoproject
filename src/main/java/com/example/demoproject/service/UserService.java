package com.example.demoproject.service;

import com.example.demoproject.dto.UserRequest;
import com.example.demoproject.model.User1;
import com.example.demoproject.repository.UserRepository;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;


    @Transactional
    public User1 saveUser(UserRequest request){
        User1 user = User1.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName()).build();
        userRepository.save(user);
        return user;
    }
    public List<UserRequest> getUsers() {
        ModelMapper modelMapper = new ModelMapper();

        List<User1> user1 = userRepository.findAll();
        return user1.stream().map(user -> modelMapper.map(user, UserRequest.class))
                .collect(Collectors.toList());

    }

    public void deleteUser(long id) {
        Optional<User1> deleteUser = userRepository.findById(id);
        if(deleteUser.isPresent()) {
            userRepository.delete(deleteUser.get());
        }
    }


    @Transactional
    public void updateUser(long id, UserRequest user) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<User1> updateUser = userRepository.findById(id);
        if (updateUser.isEmpty()) {
            throw new RuntimeException("User does not exist");
        }
        User1 user2 = updateUser.get();
        modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());
        modelMapper.addMappings(new PropertyMap<UserRequest, User1>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
        modelMapper.map(user, user2);
        userRepository.save(user2);

    }

}
