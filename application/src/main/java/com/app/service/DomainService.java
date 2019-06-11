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

public class DomainService {

    public static final String DOMAIN_SEXO = "DOMAIN_SEXO";
    public static final String DOMAIN_ESTADO_CIVIL = "DOMAIN_ESTADO_CIVIL";
    public static final String DOCUMENT_TYPE = "DOCUMENT_TYPE";


    /**
     * Serci to get domains of a kind of domain
     * see example of use in :: ...\views\recluso\_form.html
     * @param domain
     * @return
     */
    public  static ArrayList<Domain> getDomains(String domain){

        ArrayList<Domain> domainList = new ArrayList<>();

        ArrayList<Params> params = new Params()
                .Add(new Params("domainType", domain))
                .Add(new Params("statue", String.valueOf(Helper.STATUS_ACTIVE)))
                .Get();

        BaseResponse objResponse = (new ServiceProxy()).getJsonData("api/domains", params);

        JSONObject dataResponse = (JSONObject) objResponse.getData();

        if(dataResponse != null && dataResponse.get("content") != null)
            domainList =  (ArrayList<Domain>) dataResponse.get("content");

        return domainList;
    }

}

