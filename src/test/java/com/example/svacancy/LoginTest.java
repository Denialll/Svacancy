//package com.example.sportmenu;
//
//import com.example.svacancy.controller.VacancyController;
//import org.junit.jupiter.api.Test;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.core.StringContains.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class LoginTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private VacancyController controller;
//
//    @Test
//    public void startLoad() throws Exception {
//        this.mockMvc.perform(get("/"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("Hello, friend")))
//                .andExpect(content().string(containsString("Click <a href=\"/main\">here</a> for registration or login.")));
//    }
//
//    @Test
//    public void faialLoginTest() throws Exception {
//        this.mockMvc.perform(get("/main"))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("http://localhost/login"));
//    }
//
//    @Test
//    public void correctLoginTest() throws Exception {
//        this.mockMvc.perform(formLogin().user("1").password("1"))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/main"));
//    }
//
//    @Test
//    public void failLoginTest() throws Exception {
//        this.mockMvc.perform(post("/login")
//                        .param("username", "12")
//                        .param("password", "18"))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/login?error=true"));
//    }
//}
