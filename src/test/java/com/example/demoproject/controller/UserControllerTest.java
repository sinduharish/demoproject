package com.example.demoproject.controller;

import com.example.demoproject.dto.Response;
import com.example.demoproject.dto.UserRequest;
import com.example.demoproject.model.User1;
import com.example.demoproject.repository.UserRepository;
import com.example.demoproject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup()  {
       // this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

    }


    @SneakyThrows
    @Test
    public void testSaveUser_Success() {
        //Given
        UserRequest request = UserRequest.builder()
                .firstName("John")
                .lastName("Smith")
                .build();


        // When
        mockMvc.perform(
                        post("/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())

                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)));

    }


    @Test
    public void testSaveUserWithNullFirstName() {

//        UserRequest request = new UserRequest();
//        request.setFirstName(null);
//        request.setLastName("Smith");
//
//        ResponseEntity<Response> response = userController.saveUser(request);
//
//        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
//
//        verify(userService, never()).saveUser(request);
//
//        assertThat(response.getBody().getError(), equalTo("First name cannot be null"));
    }

    @SneakyThrows
    @Test
    public void testGetUsers(){
        //Given
        List<UserRequest> request = Arrays.asList(
                new UserRequest("john","Mathew"),
                new UserRequest("Naina","Singh")
        );

        when(userService.getUsers()).thenReturn(request);

        // When
        mockMvc.perform(
                        get("/users")
                                .header("Authorization", "Bearer dummy")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))

                .andDo(MockMvcResultHandlers.print())

                //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].firstName").value(request.get(0).getFirstName()))
                .andExpect(jsonPath("$.[0].lastName").value(request.get(0).getLastName()))
                .andExpect(jsonPath("$.[1].firstName").value(request.get(1).getFirstName()))
                .andExpect(jsonPath("$.[1].lastName").value(request.get(1).getLastName()));
    }

    @SneakyThrows
    @Test
    public void testDeleteUser(){

        //Given
        long id = 1L;
        doNothing().when(userService).deleteUser(id);

        //When
        ResponseEntity<Response> response = userController.deleteUser(id);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
    }


    @Test
    public void testUpdateUser() {
        //Given
        long id = 1L;
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("John ");
        userRequest.setLastName("Doe");
        doNothing().when(userService).updateUser(id, userRequest);

        //When
        ResponseEntity<Response> response = userController.updateUser(id, userRequest);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
    }

}
