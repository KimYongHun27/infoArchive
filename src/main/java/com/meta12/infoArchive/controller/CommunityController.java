package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.CommunityDto;
import com.meta12.infoArchive.entity.Community;
import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.AnswerService;
import com.meta12.infoArchive.service.CommunityService;
import com.meta12.infoArchive.service.ReviewService;
import com.meta12.infoArchive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
    private final UserService userService;
    private final AnswerService answerService;

    @GetMapping("/community")
      public String community(
              Model model,
              @RequestParam(value="page", defaultValue="0") int page,
              @RequestParam(value="category", defaultValue="all") String category,
              @RequestParam(value="kw", defaultValue="") String kw
    )
    {
        Page<Community> paging = communityService.list(page,category,kw);
        model.addAttribute("paging",paging);
        model.addAttribute("category", category);
        model.addAttribute("kw", kw);
        return "community/community";
    }

    @GetMapping("/community/edit")
    public String edit()
    {
        return "community/edit";
    }

    @GetMapping("/community/detail/{id}")
    public String detail(
            Model model,
            @PathVariable("id") Long id,
            Authentication authentication
    ) {
        Community community = communityService.detail(id);
        model.addAttribute("community", community);
        if (authentication != null && authentication.isAuthenticated()) {
            User loginUser = userService.getLoginUser(authentication);
            if (loginUser != null) {
                model.addAttribute("loginUserId", loginUser.getId());
            }
        }
        return "community/detail";
    }

    @PostMapping("/community/editProc")
    public String editProc(
            Authentication authentication,
            CommunityDto communityDto
    )
    {
        communityService.editProc(authentication,communityDto);
        return "redirect:/community";
    }

    @PostMapping("/community/deleteProc/{id}")
    public String deleteProc(
            @PathVariable ("id") Long id,
            Authentication authentication
    )
    {
        Community community = communityService.detail(id);

        User loginUser = userService.getLoginUser(authentication);

        if (community == null || !community.getUser().getId().equals(loginUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
        }
        communityService.deleteById(id);

        return "redirect:/community";
    }
    @PostMapping("/community/answer/create/{id}")
    public String Answer(
            @PathVariable("id") Long id,
            @RequestParam("content") String content,
            Authentication auth
    ) {

        Community community = communityService.detail(id);
        User user = userService.getLoginUser(auth);

        // commentService를 만들어 저장 로직을 구현하세요.
        answerService.save(community, user, content);

        return String.format("redirect:/community/detail/%s", id);
    }

}

