package com.abalitskyy.deploydemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HelloController.class, properties = "APP_VERSION=a1b2c3d4e5f6")
class HelloControllerWebTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void helloReturnsMessageAndVersionAsJson() throws Exception {
        mvc.perform(get("/api/hello").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("Good morning from deploy-demo!"))
            .andExpect(jsonPath("$.version").value("a1b2c3d4e5f6"));
    }

    @Test
    void postIsNotAllowed() throws Exception {
        mvc.perform(post("/api/hello"))
            .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void unknownPathReturnsNotFound() throws Exception {
        mvc.perform(get("/api/does-not-exist"))
            .andExpect(status().isNotFound());
    }
}