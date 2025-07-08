package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final PersonService personService;
    private static final String UPLOAD_DIR = "uploads/images/person/";

    public String upload(MultipartFile file, String personId) {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        createUploadDirIfNotExists(uploadPath);

        personService.findById(UUID.fromString(personId));

        String imageName = generateFileImageName(file.getOriginalFilename(), personId);
        Path imagePath = uploadPath.resolve(imageName);

        try {
            Files.deleteIfExists(imagePath);
            Files.copy(file.getInputStream(), imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imagePath.toString();
    }

    private void createUploadDirIfNotExists(Path uploadPath) {
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String generateFileImageName(String originalFileName, String personId) {
        String fileExtension = "";
        if(originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        return personId.concat(fileExtension);
    }

}
