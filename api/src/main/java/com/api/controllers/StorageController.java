package com.api.controllers;

import com.library.helpers.FileUpload;
import com.library.helpers.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

@RestController
public class StorageController {

    public static final String uploadingDir = System.getProperty("user.dir") + "/src/main/resources/static/";


    @RequestMapping(value = "api/storage/exchange-files", method = RequestMethod.POST)
    public ArrayList<FileUpload> UploadFiles(ModelMap modelMap, MultipartFile[] files) throws IOException {

        ArrayList<FileUpload> filiList = new ArrayList<>();
        for(MultipartFile uploadedFile : files) {

            String FileID = new Helper().genToken(18);
            FileUpload oFileUpload = new FileUpload();
            oFileUpload.setId(FileID);
            oFileUpload.setBasePath("uploads/examples/");
            oFileUpload.setFileExtension(com.google.common.io.Files.getFileExtension(uploadedFile.getOriginalFilename()));
            oFileUpload.setFileName(FileID + "." + oFileUpload.getFileExtension());
            oFileUpload.setMimeType(uploadedFile.getContentType());
            oFileUpload.setFileSize(uploadedFile.getBytes().length);

            String fullFilePath = oFileUpload.getBasePath() + oFileUpload.getFileName();

            File file = new File(uploadingDir + fullFilePath);

            System.out.println(uploadedFile);
            System.out.println(file);

            filiList.add(oFileUpload);

            uploadedFile.transferTo(file);
        }

        if(files.length == 0)
            System.out.println("No files");

        return filiList;
    }



    @RequestMapping(value = "files/exchange-file", method = RequestMethod.POST)
    public FileUpload UploadFileToDiretory(ModelMap modelMap, MultipartFile  file) throws IOException {

        FileUpload oFileUpload = new FileUpload();

        if(file != null) {

            String FileID = new Helper().genToken(18);
            oFileUpload.setId(FileID);
            oFileUpload.setBasePath("uploads/examples/");
            oFileUpload.setFileExtension(com.google.common.io.Files.getFileExtension(file.getOriginalFilename()));
            oFileUpload.setFileName(FileID + "." + oFileUpload.getFileExtension());
            oFileUpload.setMimeType(file.getContentType());
            oFileUpload.setFileSize(file.getBytes().length);

            String fullFilePath = oFileUpload.getBasePath() + oFileUpload.getFileName();

            File oFile = new File(uploadingDir + fullFilePath);

            System.out.println(file);
            System.out.println(file);

            file.transferTo(oFile);
        }else
            System.out.println("No files");

        return oFileUpload;
    }

}
