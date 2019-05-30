package com.app.service;


import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Stream;

import com.app.helpers.Params;
import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Domain;
import com.library.models.Profile;
import com.library.models.User;
import org.json.simple.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

public class StorageService {


    public  static ArrayList<Domain> getDomains(String domain){

        ServiceProxy oServiceProxy = new ServiceProxy();

        BaseResponse oBaseResponse = oServiceProxy
                .buildParams("api/users/details", new Params().Add(new Params("domain", domain)).Get())
                .getTarget()
                .get(BaseResponse.class);
        oServiceProxy.close();

        ArrayList<Domain> domainList = (ArrayList<Domain>) oBaseResponse.getData();

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

