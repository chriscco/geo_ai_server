package com.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {

    @GetMapping(value = "/ai")
    public String getIndex() {
        return "index";
    }

}
