package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.InstructorCourseCreateDto;
import com.meta12.infoArchive.entity.Product;
import com.meta12.infoArchive.entity.ProductStatus;
import com.meta12.infoArchive.entity.ProductType;
import com.meta12.infoArchive.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstructorCourseService {

    private final ProductRepository productRepository;

    @Transactional
    public void createCourse(Authentication authentication, InstructorCourseCreateDto dto) {

        Product product = new Product();

        product.setProductName(dto.getTitle());
        product.setCategory(dto.getCategory());
        product.setVideoUrl(convertYoutubeUrlToEmbed(dto.getVideoUrl()));
        product.setPrice(dto.getPrice());
        product.setDiscountRate(dto.getDiscountRate());
        product.setThumbnailUrl(dto.getThumbnailUrl());
        product.setDescription(dto.getDescription());

        product.setInstructorName(authentication.getName());

        product.setProductType(ProductType.COURSE);
        product.setStatus(ProductStatus.WAITING);

        productRepository.save(product);
    }

    @Transactional
    public void updateCourse(Long id, InstructorCourseCreateDto dto, MultipartFile thumbnailFile) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 없습니다. id=" + id));

        product.setProductName(dto.getTitle());
        product.setCategory(dto.getCategory());
        product.setVideoUrl(convertYoutubeUrlToEmbed(dto.getVideoUrl()));
        product.setPrice(dto.getPrice());
        product.setDiscountRate(dto.getDiscountRate());
        product.setDescription(dto.getDescription());

        // 썸네일 파일이 새로 올라온 경우만 교체
        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            String thumbnailUrl = saveThumbnailFile(thumbnailFile);
            product.setThumbnailUrl(thumbnailUrl);
        }

        // 승인/반려된 강의를 수정하면 다시 검토중으로 변경
        if (product.getStatus() == ProductStatus.REJECTED
                || product.getStatus() == ProductStatus.APPROVED) {
            product.setStatus(ProductStatus.WAITING);
            product.setRejectReason(null);
            product.setReviewedAt(null);
        }

        productRepository.save(product);
    }

    private String saveThumbnailFile(MultipartFile file) {

        try {
            String uploadDir = System.getProperty("user.dir")
                    + "/src/main/resources/static/uploads/course/";

            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String savedFileName = UUID.randomUUID() + extension;

            File saveFile = new File(uploadDir + savedFileName);

            file.transferTo(saveFile);

            return "/uploads/course/" + savedFileName;

        } catch (IOException e) {
            throw new RuntimeException("썸네일 이미지 저장 중 오류가 발생했습니다.", e);
        }
    }

    private String convertYoutubeUrlToEmbed(String url) {

        if (url == null || url.trim().isEmpty()) {
            return null;
        }

        String trimmedUrl = url.trim();

        // 이미 embed 주소면 그대로 저장
        if (trimmedUrl.contains("youtube.com/embed/")) {
            return trimmedUrl;
        }

        // 일반 유튜브 주소: https://www.youtube.com/watch?v=영상ID
        if (trimmedUrl.contains("youtube.com/watch?v=")) {
            String videoId = trimmedUrl.substring(trimmedUrl.indexOf("v=") + 2);

            // 뒤에 &list= 같은 파라미터 제거
            int ampIndex = videoId.indexOf("&");
            if (ampIndex != -1) {
                videoId = videoId.substring(0, ampIndex);
            }

            return "https://www.youtube.com/embed/" + videoId;
        }

        // 짧은 주소: https://youtu.be/영상ID
        if (trimmedUrl.contains("youtu.be/")) {
            String videoId = trimmedUrl.substring(trimmedUrl.lastIndexOf("/") + 1);

            int questionIndex = videoId.indexOf("?");
            if (questionIndex != -1) {
                videoId = videoId.substring(0, questionIndex);
            }

            return "https://www.youtube.com/embed/" + videoId;
        }

        // 유튜브가 아닌 주소는 일단 그대로 저장
        return trimmedUrl;
    }
}