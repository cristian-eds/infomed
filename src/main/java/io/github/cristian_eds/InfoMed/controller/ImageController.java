package io.github.cristian_eds.InfoMed.controller;

import io.github.cristian_eds.InfoMed.controller.dto.ImagePersonUrlDTO;
import io.github.cristian_eds.InfoMed.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ImagePersonUrlDTO> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("personId") String personId) throws BadRequestException {
        if(file.isEmpty()) throw new BadRequestException("The file is empty.");
        return ResponseEntity.ok(new ImagePersonUrlDTO(imageService.upload(file, personId)));
    }

    @GetMapping(value = "{id}", produces = {
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE
    })
    public ResponseEntity<?> getImage(@PathVariable String id){
        return imageService.getImage(id)
                .map(bytes ->  ResponseEntity.ok().body(bytes))
                .orElse(ResponseEntity.notFound().build());
    }
}
