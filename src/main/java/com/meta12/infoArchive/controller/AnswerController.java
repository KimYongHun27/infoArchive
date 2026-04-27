package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.AnswerDto;
import com.meta12.infoArchive.entity.Answer;
import com.meta12.infoArchive.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

//    @GetMapping("/ /list")
//    public String list(
//
//    ){
//
//        return "/list";
//    }
//    @GetMapping("/ /view")
//    public String view(){
//        return "/view";
//    }
//    @GetMapping("/ /chuga")
//    public String chuga(){
//        return "/chuga";
//    }
    @GetMapping("/ /sakje")
    public String sakje(
            @PathVariable("id") Long id,
            AnswerDto answerDto
    ){
        Answer answer = answerService.view(id);
        answerDto.setId(id);
        answerDto.setContent(answer.getContent());
        return "/sakje";
    }
    @GetMapping("/ /sujung")
    public String sujung(
            @PathVariable("id") Long id,
            AnswerDto answerDto
    ){
        Answer answer = answerService.view(id);
        answerDto.setId(id);
        answerDto.setContent(answer.getContent());
        return "/sujung";
    }



// Proc
    @PostMapping("/ /chugaProc")
    public String chugaProc(){
        return "redirect:/  /chuga";
    }

//    @PreAuthorize("isAuthenticated()")
    @PostMapping("/ /sujungProc/{id}")
    public String sujungProc(
            AnswerDto answerDto
    ){
        return "redirect:/  /view/" + answerDto.getId();
    }

//    @PreAuthorize("isAuthenticated()")
    @PostMapping("/  /sakjeProc/{id}")
    public String sakjeProc(){
        return "redirect:/  /view";
    }

}