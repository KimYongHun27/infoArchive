package com.meta12.infoArchive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpecialController {

    @GetMapping("/special")
    public String specialMain() {
        return "special/main";
    }

    @GetMapping("/special/tools")
    public String specialTools() {
        return "special/tools";
    }

    @GetMapping("/special/logs")
    public String specialLogs() {
        return "special/logs";
    }

    @GetMapping("/special/report")
    public String specialReport() {
        return "special/report";
    }
}