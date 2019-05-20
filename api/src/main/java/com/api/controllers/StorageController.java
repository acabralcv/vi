package com.api.controllers;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.models.Document;
import com.library.models.Domain;
import com.library.models.Image;
import com.library.models.Profile;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
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
    public ResponseEntity exchangeSingleImage( MultipartFile  file, Image oImage)  throws IOException {

        try {

            if (file == null)
                new Exception("Nenhum imagem foi enviada.");

            oImage.setId(new Helper().getUUID());
            oImage.setImageType(com.google.common.io.Files.getFileExtension(file.getOriginalFilename()));
            oImage.setName(oImage.getId() + "." + oImage.getImageType());


            // Define metaData
            DBObject metaData = new BasicDBObject();
            metaData.put("document_type", "OTHER");
            metaData.put("description", oImage.getDescription());
            metaData.put("file_type", "image"); //Ex: image, document, video
            metaData.put("user_id", oImage.getUserId()); //Ex: image, document, video

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

    @RequestMapping(value = "api/storage/images-details", method = RequestMethod.GET)
    public ResponseEntity getImageDetails(@RequestParam(name = "id") String id){
        try {
            GridFSFile fileObj = gridOperations.find(new Query(Criteria.where("_id").is(id))).first();

            if(fileObj != null)
                return ResponseEntity.ok()
                        .contentLength(fileObj.getLength())
                        .contentType(MediaType.valueOf("image/png"))
                        .body(gridOperations.getResource(fileObj));
            else
                return ResponseEntity.ok().body(new BaseResponse(0,"File not found!", null));

        }catch (Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    @RequestMapping(value = "api/storage/user-images", method = RequestMethod.GET)
    public ResponseEntity getImages(@RequestParam(name = "userId") UUID userId, Pageable pageable){
        try {

            Page<Image> images = imageRepository.findByUserId(userId, pageable);

            return ResponseEntity.ok().body(new BaseResponse(1,"ok", images));

        }catch (Exception e){
            new EventsLogService().AddEventologs(null,"Excption in " + this.getClass().getName(), e.getMessage(),null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    @RequestMapping(value = "api/storage/documents", method = RequestMethod.GET)
    public ResponseEntity getDocuments(ModelMap model, @PageableDefault(sort = {"name"}, size = 10, page = 0) Pageable pageable) {

        Page<Document> documents = documentRepository.findByStatus(Helper.STATUS_ACTIVE, pageable);

        return ResponseEntity.ok().body(new BaseResponse().getObjResponse(1, "ok", documents));
    }

}
