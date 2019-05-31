package com.app.service;


import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Stream;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.helpers.Helper;
import com.library.models.Domain;
import com.library.models.Profile;
import com.library.models.User;
import org.json.simple.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

public class StorageService {

    public static final String DOMAIN_SEXO = "DOMAIN_SEXO";
    public static final String DOMAIN_ESTADO_CIVIL = "DOMAIN_ESTADO_CIVIL";
    public static final String DOCUMENT_TYPE = "DOCUMENT_TYPE";

    public  static ArrayList<Domain> getDomains(String domain){

        ArrayList<Domain> domainList = new ArrayList<>();

        ArrayList<Params> params = new Params()
                .Add(new Params("domainType", domain))
                .Add(new Params("statue", String.valueOf(Helper.STATUS_ACTIVE)))
                .Get();

        BaseResponse objResponse = (new ServiceProxy()).getJsonData("api/domains", params);

        JSONObject dataResponse = (JSONObject) objResponse.getData();

        if(dataResponse.get("content") != null)
            domainList =  (ArrayList<Domain>) dataResponse.get("content");

        return domainList;
    }

    public  static HashMap<UUID, String> getMapedDomain(String domain){

        HashMap<UUID, String> mapDomains = new HashMap<>();

        for(Domain oDomain: getDomains(domain)){
            mapDomains.put(oDomain.getId(), oDomain.getName());
        }
        return mapDomains;
    }
}

