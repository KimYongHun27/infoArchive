package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.CommunityDto;
import com.meta12.infoArchive.entity.Community;
import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.CommunityRepository;
import com.meta12.infoArchive.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final UserService userService;

    public Page<Community> list(int page, String category, String kw) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10);
        if (category == null || category.isEmpty() || category.equals("all")) {
            if (kw == null || kw.isEmpty()) return communityRepository.findAll(pageable);
            return communityRepository.findByTitleContaining(kw, pageable);
        } else {
            if (kw == null || kw.isEmpty()) return communityRepository.findByCategory(category, pageable);
            return communityRepository.findByCategoryAndTitleContaining(category, kw, pageable);
        }
    }
    public Community editProc(Authentication authentication, CommunityDto communityDto){
        Community community = new Community();

        User user = userService.getLoginUser(authentication);

        community.setId(communityDto.getId());
        community.setTitle(communityDto.getTitle());
        community.setContent(communityDto.getContent());
        community.setCategory(communityDto.getCategory());
        community.setUser(user);
        communityRepository.save(community);
        return community;
    }
    public Community detail(Long id){
        Optional<Community> oc = communityRepository.findById(id);
        Community community = null;
        if (oc.isPresent()) {
            community = oc.get();
        }
        return community;
    }
}