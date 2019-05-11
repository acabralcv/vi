package com.library.helpers;

import java.util.List;

public class UtilsPagination {

    public static String Pagging(Object model, List<Integer> pageNumber){


        return "<nav aria-label=\"Page navigation\">" +
                "<ul class=\"pagination\">" +
                "<li class=\"page-item\">" +
                "<a class=\"page-link active\" href=\"/workflow/process/?size=3&amp;page=1\">1</a>" +
                "</li>" +
                "<li class=\"page-item\">" +
                "<a class=\"page-link\" href=\"/workflow/process/?size=3&amp;page=2\">2</a>" +
                "</li>" +
                "</ul>" +
                "</nav>$$";
    }

}


