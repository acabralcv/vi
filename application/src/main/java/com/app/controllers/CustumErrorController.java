package com.app.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

//https://www.baeldung.com/spring-boot-custom-error-page
@Controller
public class CustumErrorController implements ErrorController {


    @Override
    public String getErrorPath() {
        return "/error";
    }
//
//    @RequestMapping("/error")
//    @ResponseBody
//    public String handleError(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
//        return String.format("<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>"
//                        + "<div>Exception Message: <b>%s</b></div><body></html>",
//                statusCode, exception == null ? "N/A": exception.getMessage());
//    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, ModelMap model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        Integer statusCode =  500;
        String messsage = "Erro ao processar a solicitação. ";

        if (status != null) {
             statusCode = Integer.valueOf(status.toString());

            if(statusCode == 404) {
                messsage = "página não encontrada. ";
            }
            else if(statusCode == 403 || statusCode == 401) {
                messsage = "Voçe não tem permissão suficiente para acessar este conteudo. ";
            }
        }

        model.addAttribute("statusCode",  statusCode);
        model.addAttribute("messsage",  (messsage += " <br /><br /><h4>Por favor contacte o administrador do sistema.</h4>") );
        return "error";
    }
}