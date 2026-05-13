package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.service.SpecialLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SpecialController {

    private final SpecialLogService specialLogService;

    @GetMapping("/special")
    public String specialMain(Model model) {
        model.addAttribute("totalLogCount", specialLogService.getTotalLogCount());
        model.addAttribute("successLogCount", specialLogService.getSuccessLogCount());
        model.addAttribute("failLogCount", specialLogService.getFailLogCount());

        return "special/main";
    }

    @GetMapping("/special/tools")
    public String specialTools() {
        return "special/tools";
    }

    @GetMapping("/special/logs")
    public String specialLogs(Model model) {
        model.addAttribute("logs", specialLogService.getRecentLogs());
        return "special/logs";
    }

    @GetMapping("/special/report")
    public String specialReport(Model model) {
        model.addAttribute("totalLogCount", specialLogService.getTotalLogCount());
        model.addAttribute("successLogCount", specialLogService.getSuccessLogCount());
        model.addAttribute("failLogCount", specialLogService.getFailLogCount());
        model.addAttribute("successRate", specialLogService.getSuccessRate());
        model.addAttribute("recentLogs", specialLogService.getRecent5Logs());

        return "special/report";
    }
}