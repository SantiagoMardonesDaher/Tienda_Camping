package com.uade.tpo.demo.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.uade.tpo.demo.entity.Image;
import com.uade.tpo.demo.service.ImageService;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("images")
public class ImagesController {
    @Autowired
    private ImageService imageService;

    @CrossOrigin
    @GetMapping("/{ImageID}")
    public ResponseEntity<ImageResponse> displayImage(@PathVariable long ImageID) throws IOException, SQLException {
        Image image = imageService.viewById(ImageID);
        String encodedString = Base64.getEncoder()
                .encodeToString(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().body(ImageResponse.builder().file(encodedString).id(ImageID).build());
    }
@PostMapping()
    public ResponseEntity<Map<String, Object>> addImagePost(@RequestParam("file") MultipartFile file)
            throws IOException, SerialException, SQLException {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "No file uploaded"));
        }
 
        // Convert file to Blob
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
 
        // Save image via imageService
        Image savedImage = imageService.create(Image.builder().image(blob).build());
 
        // Return the image ID
        Map<String, Object> response = new HashMap<>();
        response.put("status", "created");
        response.put("imageId", savedImage.getId());
 
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
 
 
}
