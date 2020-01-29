package com.sidj.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value="/file")
public class FileController {
	
	private final String UPLOAD_FOLDER="./uploads/";
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String uploadfile(@RequestParam("file") MultipartFile uploadingfile)
	{
		
		if (uploadingfile.isEmpty()) {
            return new String("please select a file!");
        }
		
		

            try {

            byte[] bytes = uploadingfile.getBytes();
            Path path = Paths.get("C:/Users/A172CTDI/Desktop/comparsion/" + uploadingfile.getOriginalFilename());
            Files.write(path, bytes);
            System.out.println(uploadingfile.getOriginalFilename());
            System.out.println("files uploaded");
            }
            catch(IOException e)
            {
            	System.out.println(e.getMessage());
            }

        
		return "filesuploaded..";
	}
	
	@RequestMapping(value="/uploadMultiple",method=RequestMethod.POST)
	public String uploadMultipleFiles(@RequestParam("file") MultipartFile[] uploadingfiles)
	{
		
		System.out.println("In multi file upload");
		String uploadedFileName = Arrays.stream(uploadingfiles).map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));
		System.out.println("file name are "+uploadedFileName);
		
		if (StringUtils.isEmpty(uploadedFileName)) {
            return new String("please select a file!");
        }
		


		try 
		{
			System.out.println("in try");
			saveUploadedFiles(Arrays.asList(uploadingfiles));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
		return "filesuploaded..";
	}
	
	private void saveUploadedFiles(List<MultipartFile> files) throws IOException {
		System.out.println("in saveupload file");
		int i=10;

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }
            i = i+1;
            byte[] bytes = file.getBytes();
            System.out.println("bytes "+bytes);
            String name = file.getOriginalFilename();
            
            System.out.println("FIel.getname "+file.getName());
            String extension = "";

            int j = name.lastIndexOf('.');
            if (j > 0) {
                extension = name.substring(j);
            }
            
            Path path = Paths.get("../../uploaded/"+"user-"+i+extension);
            System.out.println("Here at 86 folder "+path);
            Files.write(path, bytes);

        }

    }

}
