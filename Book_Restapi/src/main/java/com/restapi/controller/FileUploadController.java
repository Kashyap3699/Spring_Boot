package com.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.restapi.Helper.FileUploadHelper;

@RestController
public class FileUploadController {

	@Autowired
	private FileUploadHelper fileUploadHelper;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
//		System.out.println("File Original Name = " +file.getOriginalFilename());
//		System.out.println("File Size = " + file.getSize());
//		System.out.println("Content Type = " + file.getContentType());

		// Validation
		try {

			if (multipartFile.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Request must contain file");
			}

			if (!multipartFile.getContentType().equals("image/jpeg")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Only jpeg file allowed");
			}
			
			// file upload code
			boolean upFile = fileUploadHelper.uploadFile(multipartFile);
			if(upFile) 
			{
				return ResponseEntity.ok("File uploaded Successfully");
			}
			
		} catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Something went wrong");
	}

}
