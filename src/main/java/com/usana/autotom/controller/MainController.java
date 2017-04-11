package com.usana.autotom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kerrysouthworth on 1/11/17.
 */

@Controller
public class MainController
{
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String homepage(){
        return "index";
    }
}
