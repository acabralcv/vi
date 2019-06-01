package com.app.service;

import com.app.helpers.ServiceProxy;
import com.library.helpers.BaseResponse;
import com.library.models.Ilha;
import com.library.models.Pais;
import org.json.simple.JSONObject;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

public class IlhaService {

    public ArrayList<Ilha> findAll(){

        BaseResponse oBaseResponse = (new ServiceProxy())
                .getJsonData("api/ilhas", (new ServiceProxy()).encodePageableParams(PageRequest.of(0,50)));

        JSONObject dataResponse = (JSONObject) oBaseResponse.getData();

        return dataResponse != null ? (ArrayList<Ilha>) dataResponse.get("content") : new ArrayList<>();
    }
}
