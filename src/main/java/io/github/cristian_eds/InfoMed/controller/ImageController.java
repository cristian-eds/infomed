package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("personId") String personId) throws BadRequestException {
        if(file.isEmpty()) throw new BadRequestException("The file is empty.");
        return ResponseEntity.ok(imageService.upload(file, personId));
    }
}
