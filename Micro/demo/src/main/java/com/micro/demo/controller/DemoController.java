package com.micro.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/gfg")  // This method must match the request being made
    public String getDemoData() {
        return "Hello from demo!";
    }

    @PostMapping("/gfg")  // This method is for POST requests
    public String postDemoData() {
        return "Posted to demo!";
    }
}