package com.mtf.kibs.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/errors")
public class ErrorApiController implements ErrorController {

    @GetMapping
    public String handleError(HttpServletRequest request) {
        Optional<Object> maybeStatus =
                Optional.ofNullable(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        if(maybeStatus.isPresent()) {
            int statusCode = Integer.parseInt(maybeStatus.get().toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "/error/error404";
            }else if(statusCode == HttpStatus.PAYMENT_REQUIRED.value() || statusCode == HttpStatus.FORBIDDEN.value()){
                return "/error/error402";
            }else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return "/error/error500";
            }
        }
        return "/error/error404";
    }

}