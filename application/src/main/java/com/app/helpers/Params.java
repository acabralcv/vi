package com.app.helpers;


import java.util.ArrayList;

public class Params {

    String name;
    String value;
    ArrayList<Params> params;

    public  Params(){
        params = new ArrayList<>();
    }

    public  Params(String name, String value){
        this.name = name;
        this.value = value;
    }

    public Params Add(Params oParams){
        this.params.add(oParams);
        return this;
    }

    public ArrayList<Params> Get(){
        return this.params;
    }

    //new Params().Add().Add().Get();
}
