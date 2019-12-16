//package com.chatapp.tokens.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@Controller
//public class TokensController {
//
//    @GetMapping(value = "/{a}/{b}", produces = "text/plain")
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public String getToken(@PathVariable String a, @PathVariable String b) {
//        return String.format("%s and %s", a, b);
//    }
//
//}
