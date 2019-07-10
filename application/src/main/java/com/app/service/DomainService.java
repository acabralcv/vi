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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

public class DomainService {

    @Autowired
    private final Environment env;

    public static final String DOMAIN_SEXO = "DOMAIN_SEXO";
    public static final String DOMAIN_ESTADO_CIVIL = "DOMAIN_ESTADO_CIVIL";
    public static final String DOCUMENT_TYPE = "DOCUMENT_TYPE";

    public DomainService(Environment _env){
        this.env = _env;
    }


    /**
     * Serci to get domains of a kind of domain
     * see example of use in :: ...\views\recluso\_form.html
     * @param domain
     * @return
     */
    public  static ArrayList<Domain> getDomains(ServiceProxy oServiceProxy, String domain){

        ArrayList<Domain> domainList = new ArrayList<>();

        ArrayList<Params> params = new Params()
                .Add(new Params("domainType", domain))
                .Add(new Params("statue", String.valueOf(Helper.STATUS_ACTIVE)))
                .Get();

        BaseResponse objResponse = oServiceProxy.getJsonData("api/domains", params);
        oServiceProxy.close();

        JSONObject dataResponse = (JSONObject) objResponse.getData();

        if(dataResponse != null && dataResponse.get("content") != null)
            domainList =  (ArrayList<Domain>) dataResponse.get("content");

        return domainList;
    }

}

