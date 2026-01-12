package com.example.checkrecognzer.controllers;

import com.example.checkrecognzer.services.CheckRecognizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/info")
    public ResponseEntity<?> getCarCounts(
                                          @RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            var carCount = checkRecognizerService.getCheckInfo(inputStream, file.getContentType());
            return ResponseEntity.ok(carCount);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }
}