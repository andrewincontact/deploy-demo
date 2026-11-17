package com.abalitskyy.deploydemo.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloControllerTest {

    @Test
    void returnsTheConfiguredVersion() {
        var controller = new HelloController("abc123");
        assertThat(controller.hello()).containsEntry("version", "abc123");
    }
}

