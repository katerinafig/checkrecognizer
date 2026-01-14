package com.example.checkrecognzer.controllers;

import com.example.checkrecognzer.services.ChatService;
import com.example.checkrecognzer.services.CheckRecognizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/check")
public class CheckController {
    @Autowired
    private CheckRecognizerService checkRecognizerService;
    @Autowired
    private ChatService chatService;

    @PostMapping("/info")
    public ResponseEntity<?> getCheckInfo(
            @RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            var checkInfo = checkRecognizerService.getCheckInfo(inputStream, file.getContentType());
            return ResponseEntity.ok(checkInfo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

    @PostMapping("/message")
    public ResponseEntity<?> sendMessage(@RequestBody String message) {
        var answer = chatService.sendMessage(message);
        return ResponseEntity.ok(answer);
    }
}