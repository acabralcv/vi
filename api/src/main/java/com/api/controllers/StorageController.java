package com.api.controllers;

import java.io.*;
import java.util.UUID;

import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.helpers.UtilsDate;
import com.library.models.*;
import com.library.repository.DocumentRepository;
import com.library.repository.ImageRepository;
import com.library.repository.UserRepository;
import com.library.service.EventsLogService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
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

    @Autowired
    private UserRepository userRepository;

    /**
     * save an image
     * @param file
     * @param oImage
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "api/storage/exchange-image", method = RequestMethod.POST)
    public ResponseEntity exchangeSingleImage( MultipartFile  file, Image oImage)  throws IOException {

        try {

            if (file == null)
                throw  new Exception("Nenhum imagem foi enviada.");

            oImage.setId(new Helper().getUUID());
            oImage.setImageType(com.google.common.io.Files.getFileExtension(file.getOriginalFilename()));
            oImage.setName(oImage.getId() + "." + oImage.getImageType());
            oImage.setStatus(Helper.STATUS_ACTIVE);
            oImage.setDateCreated(UtilsDate.getDateTime());


            // Define metaData
            DBObject metaData = new BasicDBObject();
            metaData.put("document_type", "OTHER");
            metaData.put("description", oImage.getDescription());
            metaData.put("file_type", "image"); //Ex: image, document, video
            metaData.put("user_id", oImage.getUserId()); //Ex: image, document, video

            InputStream iamgeStream = file.getInputStream();
            ObjectId storeFile = gridOperations.store(iamgeStream, oImage.getName(), file.getContentType(), metaData);

            if (storeFile == null)
                throw new Exception("Não foi possivel guardar a imagem.");

            //let's update the image storageId
            oImage.setStorageId(storeFile.toString());

            //and then, save the metadata image
            Image objImage = imageRepository.save(oImage);

            return ResponseEntity.ok().body(new BaseResponse(1, "ok", objImage));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    /**
     * save an document
     * @param file
     * @param documentPosted
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "api/storage/exchange-document", method = RequestMethod.POST)
    public ResponseEntity exchangeSingleFile( MultipartFile  file, Document documentPosted)  throws IOException {

        try {

            if (file == null)
                throw new Exception("Nenhum documento foi enviado.");

            Document oDocument = new Document();
            String extension = com.google.common.io.Files.getFileExtension(file.getOriginalFilename());

            oDocument.setId(new Helper().getUUID());
            oDocument.setImageType(extension);
            oDocument.setName(oDocument.getId() + "." + oDocument.getImageType());
            oDocument.setDescription(documentPosted.getDescription());
            oDocument.setStatus(Helper.STATUS_ACTIVE);

            // Define metaData
            DBObject metaData = new BasicDBObject();
            metaData.put("document_type", "OTHER");
            metaData.put("description", oDocument.getDescription());
            metaData.put("file_type", "document"); //Ex: image, document, video

            InputStream iamgeStream = file.getInputStream();
            ObjectId storeFile = gridOperations.store(iamgeStream, oDocument.getName(), file.getContentType(), metaData);

            if (storeFile == null)
                throw new Exception("Não foi possivel guardar o documento.");

            //let's update the image storageId
            oDocument.setStorageId(storeFile.toString());

            //and then, save the metadata image
            Document objDocument = documentRepository.save(oDocument);

            return ResponseEntity.ok().body(new BaseResponse(1, "ok", objDocument));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }


    /**
     * returns user images
     * @param userId
     * @param pageable
     * @return
     */
    @RequestMapping(value = "api/storage/user-images", method = RequestMethod.GET)
    public ResponseEntity getImages(@RequestParam(name = "userId") UUID userId, Pageable pageable){
        try {

            Page<Image> images = imageRepository.findByUserId(userId, pageable);

            return ResponseEntity.ok().body(new BaseResponse( 1,"ok", images));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    /**
     * get an image as and returns it as image MediaType
     * should be called like "/api/storage/images-details?id=storageId" from client
     * @param id
     * @return
     */
    @RequestMapping(value = "api/storage/images-details", method = RequestMethod.GET)
    public ResponseEntity getImageDetails(@RequestParam(name = "id") String id){

        try {

            if (id == null || id.equalsIgnoreCase(""))
                throw new Exception("ID da imagem não pode ser 'null'.");

            GridFSFile fileObj = gridOperations.find(new Query(Criteria.where("_id").is(id))).first();
            GridFsResource resource = gridOperations.getResource(fileObj);

            if(fileObj == null)
                throw new Exception("Imagem não encontrada!.");

            return ResponseEntity.ok()
                    .contentLength(fileObj.getLength())
                    .contentType(MediaType.parseMediaType(resource.getContentType()))
                    .body(gridOperations.getResource(fileObj));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    /**
     * get an image as and returns it as document MediaType
     * should be called like "/api/storage/documents-details?id=storageId" from client
     * @param id
     * @return
     */
    @RequestMapping(value = "api/storage/documents-details", method = RequestMethod.GET)
    public ResponseEntity getDocumentDetails(@RequestParam(name = "id") String id){
        try {

            if (id == null || id.equalsIgnoreCase(""))
                throw new Exception("ID do documento não pode ser 'null'.");

            GridFSFile fileObj = gridOperations.find(new Query(Criteria.where("_id").is(id))).first();
            GridFsResource resource = gridOperations.getResource(fileObj);

            if(fileObj == null)
                throw new Exception("Documento não encontrada!.");

            return ResponseEntity.ok()
                    .contentLength(fileObj.getLength())
                    .contentType(MediaType.parseMediaType(resource.getContentType()))
                    .body(gridOperations.getResource(fileObj));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0, e.getMessage(), null));
        }
    }

    @RequestMapping(value = "api/storage/documents", method = RequestMethod.GET)
    public ResponseEntity getDocuments(Pageable pageable) {

        try {

            Page<Document> documents = documentRepository.findByStatus(Helper.STATUS_ACTIVE,
                    PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("dateCreated")
                            .descending()
                            .and(Sort.by("name").ascending())));

            return ResponseEntity.ok().body(new BaseResponse(1,"ok", documents));

        }catch (Exception e){
            new EventsLogService(userRepository).AddEventologs(null,"Excption in class '" + this.getClass().getName()
                    + "' method " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()",e.getMessage(),null, null);
            return ResponseEntity.ok().body(new BaseResponse(0,"ok", null));
        }
    }

}
