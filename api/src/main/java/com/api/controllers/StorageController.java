package com.api.controllers;

import java.io.*;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.models.Document;
import com.library.models.Image;
import com.library.repository.DocumentRepository;
import com.library.repository.ImageRepository;
import com.library.service.EventsLogService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFSDBFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {


    @Autowired
    private GridFsOperations gridOperations;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @RequestMapping(value = "api/storage/exchange-image", method = RequestMethod.POST)
    public ResponseEntity exchangeSingleImage( MultipartFile  file, String description)  throws IOException {

        try {

            if (file == null)
                new Exception("Nenhum imagem foi enviada.");

            Image oImage = new Image();
            String extension = com.google.common.io.Files.getFileExtension(file.getOriginalFilename());

            oImage.setId(new Helper().getUUID());
            oImage.setImageType(extension);
            oImage.setName(oImage.getId() + "." + oImage.getImageType());
            oImage.setDescription(description);

            // Define metaData
            DBObject metaData = new BasicDBObject();
            metaData.put("document_type", "OTHER");
            metaData.put("description", oImage.getDescription());
            metaData.put("file_type", "image"); //Ex: image, document, video

            InputStream iamgeStream = file.getInputStream();
            ObjectId storeFile = gridOperations.store(iamgeStream, oImage.getName(), file.getContentType(), metaData);

            if (storeFile == null)
                new Exception("Não foi possivel guardar a imagem.");

            //let's update the image storageId
            oImage.setStorageId(storeFile.toString());

            //and then, save the metadata image
            Image objImage = imageRepository.save(oImage);

            return ResponseEntity.ok().body(new BaseResponse(1, "ok", objImage));

        }catch (Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    @RequestMapping(value = "api/storage/exchange-document", method = RequestMethod.POST)
    public ResponseEntity exchangeSingleFile( MultipartFile  file, String description)  throws IOException {

        try {

            if (file != null)
                new Exception("Nenhum documento foi enviado.");

            Document oDocument = new Document();
            String extension = com.google.common.io.Files.getFileExtension(file.getOriginalFilename());

            oDocument.setId(new Helper().getUUID());
            oDocument.setImageType(extension);
            oDocument.setName(oDocument.getId() + "." + oDocument.getImageType());
            oDocument.setDescription(description);

            // Define metaData
            DBObject metaData = new BasicDBObject();
            metaData.put("document_type", "OTHER");
            metaData.put("description", oDocument.getDescription());
            metaData.put("file_type", "document"); //Ex: image, document, video

            InputStream iamgeStream = file.getInputStream();
            ObjectId storeFile = gridOperations.store(iamgeStream, oDocument.getName(), file.getContentType(), metaData);

            if (storeFile == null)
                new Exception("Não foi possivel guardar o documento.");

            //let's update the image storageId
            oDocument.setStorageId(storeFile.toString());

            //and then, save the metadata image
            Document objDocument = documentRepository.save(oDocument);

            return ResponseEntity.ok().body(new BaseResponse(1, "ok", objDocument));

        }catch (Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }


    @RequestMapping(value = "api/storage/get-images-details", method = RequestMethod.GET)
    public ResponseEntity getImageDetails(@RequestParam(name = "id") String id){
        try {

            id = "5ce1d931c764832950800322";

            GridFSFile file = gridOperations.find(new Query(Criteria.where("_id").is(id))).first();

            return ResponseEntity.ok()
                .contentLength(file.getLength())
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .body(new BaseResponse(1, "ok", file.getMetadata().toJson()));

        }catch (Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

}
