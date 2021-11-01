package com.example.demo.controllers;

import com.example.demo.services.SendMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
public class SendmessageController {
    @Autowired
    private SendMessage sendMessage;

    @GetMapping
    public String send() {
        sendMessage.sendMessage();
        return "ok";
    }
}
