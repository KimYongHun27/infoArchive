package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.entity.SpecialLog;
import com.meta12.infoArchive.service.SpecialLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/special")
public class SpecialApiController {

    private final SpecialLogService specialLogService;

    @PostMapping("/log")
    public SpecialLog createLog(
            @RequestParam String actionName,
            @RequestParam String status,
            @RequestParam String message,
            Authentication authentication
    ) {
        String executedBy = authentication.getName();

        return specialLogService.saveLog(
                actionName,
                status,
                message,
                executedBy
        );
    }

    @DeleteMapping("/log/{id}")
    public String deleteLog(@PathVariable Long id) {
        specialLogService.deleteLog(id);
        return "OK";
    }

    @DeleteMapping("/logs")
    public String deleteAllLogs() {
        specialLogService.deleteAllLogs();
        return "OK";
    }
}