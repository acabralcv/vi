package com.library.helpers;

public class ActionColumn {

    public static  String getParams(String params){

        if( params == null || !params.contains(",") )
            return "1";

        String urlParam = "2";
        String [] arrOfStr = params.split(",");

        for(String a:arrOfStr)
            urlParam += "/" + a;

        return urlParam.length() > 0 ? urlParam : params;
    }
}
