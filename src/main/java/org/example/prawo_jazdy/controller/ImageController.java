package org.example.prawo_jazdy.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class ImageController {
    @GetMapping("/images/{number}")
    public ResponseEntity<byte[]> image(@PathVariable String number){
        ClassPathResource resource;
        resource = new ClassPathResource("files/images/s"+number+".png");
        if(!resource.exists()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        byte[] imageBytes;
        try {
            imageBytes = Files.readAllBytes(resource.getFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageBytes,headers, HttpStatus.OK);
    }
}
