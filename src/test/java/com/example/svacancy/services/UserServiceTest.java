//package com.example.sportmenu.services;
//
//import com.example.svacancy.Model.User;
//import com.example.svacancy.Model.enums.Role;
//import com.example.svacancy.repos.UserRepo;
//import com.example.svacancy.services.MailSender;
//import com.example.svacancy.services.UserService;
//import org.hamcrest.CoreMatchers;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Collections;
//
//@SpringBootTest
//class UserServiceTest {
//
//    @Autowired
//    private UserService userService;
//
//    @MockBean
//    private UserRepo userRepo;
//
//    @MockBean
//    private MailSender mailSender;
//
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//
//
//    @Test
//    void addUser() {
//        User user = new User();
//
//        user.setEmail("mail@mail.ru");
//
//        boolean isUserCreated = userService.createNewUser(user);
//
//        Assertions.assertTrue(isUserCreated);
//        Assertions.assertNotNull(user.getActivationCode());
//        Assertions.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
//
//        Mockito.verify(userRepo, Mockito.times(1)).save(user);
//        Mockito.verify(mailSender, Mockito.times(1)).send(
//                ArgumentMatchers.eq(user.getEmail()),
//                ArgumentMatchers.eq("Activation code"),
//                ArgumentMatchers.contains("Welcome to my first simple site!")
//                );
//    }
//
//    @Test
//    public void addUserFailTest(){
//        User user = new User();
//
//        user.setUsername("Daniel");
//
//        Mockito.doReturn(new User())
//                .when(userRepo)
//                .findByUsername("Daniel");
//
//        boolean isUserCreated = userService.createNewUser(user);
//
//        Assertions.assertFalse(isUserCreated);
//
//        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
//        Mockito.verify(mailSender, Mockito.times(0)).send(
//                ArgumentMatchers.anyString(),
//                ArgumentMatchers.anyString(),
//                ArgumentMatchers.anyString()
//        );
//    }
//
//    @Test
//    public void accessActivationUser() {
//        User user = new User();
//
//        user.setActivationCode("bingo");
//
//        Mockito.doReturn(user)
//                .when(userRepo)
//                .findByActivationCode("activate");
//
//        boolean isUserActivated = userService.activateUser("activate");
//
//        Assertions.assertTrue(isUserActivated);
//        Assertions.assertNull(user.getActivationCode());
//
//        Mockito.verify(userRepo, Mockito.times(1)).save(user);
//    }
//
//    @Test
//    public void failureActivationUser(){
//        boolean isUserActivated = userService.activateUser("activate");
//
//        Assertions.assertFalse(isUserActivated);
//
//        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any());
//    }
//
//}