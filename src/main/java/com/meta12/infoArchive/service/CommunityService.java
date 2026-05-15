package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.Review;
import com.meta12.infoArchive.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final ReviewRepository reviewRepository;;

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
}
