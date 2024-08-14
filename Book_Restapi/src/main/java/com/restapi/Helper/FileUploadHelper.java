package com.restapi.Helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {

	// public final String UPLOAD_DIR = "D:\\Kashyap Folder\\Springboot\\Book_Restapi\\src\\main\\resources\\static\\image";
	
	//dynamic path
	public final String UPLOAD_DIR = new ClassPathResource("static/image/").getFile().getAbsolutePath();

	public FileUploadHelper() throws IOException {

	}

	public boolean uploadFile(MultipartFile multipartFile) {
		boolean f = false;

		try {
			// read
//			InputStream inputStream = multipartFile.getInputStream();
//			byte Data[] = new byte[inputStream.available()];
//			inputStream.read(Data);
//			
//			//write
//			FileOutputStream fos = new FileOutputStream(UPLOAD_DIR + "\\" + multipartFile.getOriginalFilename());
//			fos.write(Data);
//			fos.flush();
//			fos.close();
//			f=true;

			Files.copy(multipartFile.getInputStream(),
					Paths.get(UPLOAD_DIR + File.separator + multipartFile.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);

			f = true;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return f;

	}

}
