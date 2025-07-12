package io.github.cristian_eds.InfoMed.service;

import io.github.cristian_eds.InfoMed.exception.custom.FileOperationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final String UPLOAD_DIR = "src/main/resources/static/images/person/";

    public String upload(MultipartFile file, String personId) {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        createUploadDirIfNotExists(uploadPath);

        String imageName = generateFileImageName(file.getOriginalFilename(), personId);
        Path imagePath = uploadPath.resolve(imageName);

        try {
            deleteImageByIdNameIfExists(personId);
            Files.copy(file.getInputStream(), imagePath);
            return "/image/"+personId;
        } catch (IOException e) {
            throw new FileOperationException(e.getMessage());
        }
    }

    private void createUploadDirIfNotExists(Path uploadPath) {
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new FileOperationException(e.getMessage());
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


    public Optional<byte[]> getImage(String idImagePerson) {
        Optional<File> file = verifyIfImageExists(idImagePerson);
        if(file.isPresent()) {
            try {
                return Optional.of(Files.readAllBytes(file.get().toPath()));
            } catch (IOException e) {
                throw new FileOperationException(e.getMessage());
            }
        }
        return Optional.empty();

    }

    private Optional<File> verifyIfImageExists(String idImagePerson){
        File file = new File(UPLOAD_DIR);
        if(file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for(File fileInDirectory : files) {
                String substring = fileInDirectory.getName().substring(0, fileInDirectory.getName().lastIndexOf("."));
                if(substring.equals(idImagePerson)) return Optional.of(fileInDirectory);
            }
        }
        return Optional.empty();
    }

    public void deleteImageByIdNameIfExists(String idName) {
        Optional<File> file = verifyIfImageExists(idName);
        if(file.isPresent()) {
            File file1 = file.get();
            try {
                Files.deleteIfExists(file1.toPath());
            } catch (IOException e) {
                throw new FileOperationException(e.getMessage());
            }
        }

    }


}
