package com.springboot.first.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.first.app.model.Image;
import com.springboot.first.app.model.Manager;
import com.springboot.first.app.service.ImageService;

@RestController
@RequestMapping("/api/image")
public class ImageController {
	
	private ImageService imageService;
	
	public ImageController(ImageService imageService) {
		super();
		this.imageService = imageService;
	}

	@PostMapping()
	public ResponseEntity<Image> saveManager(@RequestParam("file") MultipartFile file) throws IOException{
		Image img = new Image();
		String fileName = file.getOriginalFilename();
		img.setNameImage(file.getOriginalFilename());
		String uploadDir = "user-photos/";
		Path uploadPath = Paths.get(uploadDir);
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
         
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }    
        //FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		return new ResponseEntity<Image>(imageService.saImage(img), HttpStatus.CREATED);
	}
}
