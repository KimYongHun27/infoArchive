package com.meta12.infoArchive.controller;

import com.meta12.infoArchive.dto.CommunityDto;
import com.meta12.infoArchive.entity.Community;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.service.AnswerService;
import com.meta12.infoArchive.service.CommunityService;
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

    @PostMapping("/community/SujungProc")
    public String SujungProc(
            Authentication authentication,
            CommunityDto communityDto
    )
    {
        communityService.SujungProc(authentication,communityDto);
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

        // 💡 1. Spring Security의 Authentication 객체에서 관리자(ADMIN) 권한이 있는지 확인합니다.
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().contains("ADMIN"));

        // 💡 2. 게시글 작성자 본인이 "아니면서" 동시에 관리자도 "아니면" 403 에러를 발생시킵니다.
        // (즉, 둘 중 하나라도 만족하면 if문을 무사히 통과하여 삭제가 진행됩니다.)
        if (community == null || (!community.getUser().getId().equals(loginUser.getId()) && !isAdmin)) {
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



    @PostMapping("/community/answer/delete/{id}")
    public String deleteAnswer(
            @PathVariable("id") Long id,
            @RequestParam("communityId") Long communityId,
            Authentication authentication
    ) {
        // 1. 로그인 여부 확인
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        // 2. 관리자(ADMIN) 권한 확인
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().contains("ADMIN"));

        if (!isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "댓글 삭제 권한이 없습니다.");
        }

        // 3. 댓글 삭제 진행 (AnswerService에 아래 메서드가 있어야 합니다)
        answerService.deleteById(id);

        // 4. 삭제 완료 후 원래 있던 게시글 상세 페이지로 돌아갑니다
        return String.format("redirect:/community/detail/%s", communityId);
    }

    //대댓글
    @PostMapping("/community/answer/reply/{communityId}/{parentId}")
    public String createReply(
            @PathVariable("communityId") Long communityId,
            @PathVariable("parentId") Long parentId,
            @RequestParam("content") String content,
            Authentication auth
    ) {
        if (auth == null || !auth.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        Community community = communityService.detail(communityId);
        User user = userService.getLoginUser(auth);
        // 💡 위에서 만든 대댓글 전용 서비스 메서드를 호출합니다.
        answerService.saveReply(community, user, content, parentId);

        // 처리가 끝나면 다시 보던 게시글 상세 페이지로 리다이렉트합니다.
        return String.format("redirect:/community/detail/%s", communityId);
    }
    @PostMapping("/community/answer/update/{id}")
    public String updateAnswer(
            @PathVariable("id") Long id,
            @RequestParam("communityId") Long communityId,
            @RequestParam("content") String content,
            Authentication authentication
    ) {
        // 1. 로그인 여부 확인
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        // 2. 서비스에서 댓글을 가져와 본인 확인 (또는 서비스 내부에서 검증)
        // 여기서는 간단히 수정 로직을 호출합니다. (AnswerService에 update 메서드 추가 필요)
        User loginUser = userService.getLoginUser(authentication);

        // 💡 아래에서 만들 서비스 메서드를 호출합니다.
        answerService.update(id, content, loginUser);

        // 3. 원래 있던 게시글 상세 페이지로 돌아갑니다
        return String.format("redirect:/community/detail/%s", communityId);
    }

}

