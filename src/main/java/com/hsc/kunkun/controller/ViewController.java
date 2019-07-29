package com.hsc.kunkun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: hsc
 * @Description:
 * @Date: 2019/7/26 14:03
 */
@Controller
public class ViewController {
    @RequestMapping("/")
    public String index(){return "wxtb";}
    @RequestMapping("/home")
    public String home(){return "wxtb";}
}
