package com.app.service;

import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Pais;
import com.library.models.Profile;
import org.json.simple.JSONObject;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

public class PaisService {

    public ArrayList<Pais> findAll(){

        BaseResponse oBaseResponse = (new ServiceProxy())
                .getJsonData("api/paises", (new ServiceProxy()).encodePageableParams(PageRequest.of(0,50)));

        JSONObject dataResponse = (JSONObject) oBaseResponse.getData();

        return dataResponse != null ? (ArrayList<Pais>) dataResponse.get("content") : new ArrayList<>();
    }
}
