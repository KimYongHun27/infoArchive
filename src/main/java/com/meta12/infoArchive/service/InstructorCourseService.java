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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstructorCourseService {

    private final ProductRepository productRepository;

    // 내 강의 목록
    public List<Product> getMyCourses(Authentication authentication) {

        String instructorName = authentication.getName();

        return productRepository.findByInstructorNameAndStatusNotOrderByCreatedAtDesc(
                instructorName,
                ProductStatus.DELETED
        );
    }

    // 내 강의 단건 조회
    public Product getMyCourse(Authentication authentication, Long id) {

        String instructorName = authentication.getName();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의가 없습니다. id=" + id));

        if (product.getStatus() == ProductStatus.DELETED) {
            throw new IllegalArgumentException("삭제된 강의입니다.");
        }

        if (product.getInstructorName() == null
                || !product.getInstructorName().equals(instructorName)) {
            throw new IllegalArgumentException("본인이 등록한 강의만 접근할 수 있습니다.");
        }

        return product;
    }

    // 강의 등록
    @Transactional
    public void createCourse(
            Authentication authentication,
            InstructorCourseCreateDto dto,
            MultipartFile thumbnailFile
    ) {
        Product product = new Product();

        product.setProductName(dto.getTitle());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setDiscountRate(dto.getDiscountRate());
        product.setDescription(dto.getDescription());
        product.setVideoUrl(convertYoutubeUrlToEmbed(dto.getVideoUrl()));

        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            String thumbnailUrl = saveThumbnailFile(thumbnailFile);
            product.setThumbnailUrl(thumbnailUrl);
        }

        product.setInstructorName(authentication.getName());
        product.setProductType(ProductType.COURSE);
        product.setStatus(ProductStatus.WAITING);

        productRepository.save(product);
    }

    // 강의 수정
    @Transactional
    public void updateCourse(
            Authentication authentication,
            Long id,
            InstructorCourseCreateDto dto,
            MultipartFile thumbnailFile
    ) {
        Product product = getMyCourse(authentication, id);

        product.setProductName(dto.getTitle());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setDiscountRate(dto.getDiscountRate());
        product.setDescription(dto.getDescription());
        product.setVideoUrl(convertYoutubeUrlToEmbed(dto.getVideoUrl()));

        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            String thumbnailUrl = saveThumbnailFile(thumbnailFile);
            product.setThumbnailUrl(thumbnailUrl);
        }

        if (product.getStatus() == ProductStatus.REJECTED
                || product.getStatus() == ProductStatus.APPROVED) {
            product.setStatus(ProductStatus.WAITING);
            product.setRejectReason(null);
            product.setReviewedAt(null);
        }

        productRepository.save(product);
    }

    // 강의 삭제 - 실제 삭제 X, 상태만 DELETED
    @Transactional
    public void deleteCourse(Authentication authentication, Long id) {

        Product product = getMyCourse(authentication, id);

        product.setStatus(ProductStatus.DELETED);
        product.setRejectReason(null);
        product.setReviewedAt(null);

        productRepository.save(product);
    }

    private String saveThumbnailFile(MultipartFile file) {

        try {
            if (file == null || file.isEmpty()) {
                return null;
            }

            String contentType = file.getContentType();

            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
            }

            String originalFilename = file.getOriginalFilename();

            if (originalFilename == null || originalFilename.trim().isEmpty()) {
                throw new IllegalArgumentException("파일명이 올바르지 않습니다.");
            }

            String extension = "";

            int dotIndex = originalFilename.lastIndexOf(".");
            if (dotIndex != -1) {
                extension = originalFilename.substring(dotIndex).toLowerCase();
            }

            if (!extension.equals(".jpg")
                    && !extension.equals(".jpeg")
                    && !extension.equals(".png")
                    && !extension.equals(".gif")
                    && !extension.equals(".webp")
                    && !extension.equals(".bmp")) {
                throw new IllegalArgumentException("jpg, jpeg, png, gif, webp, bmp 파일만 업로드할 수 있습니다.");
            }

            String uploadDir = System.getProperty("user.dir")
                    + "/uploads/course/";

            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
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
        String videoId = null;

        if (trimmedUrl.contains("youtube.com/embed/")) {
            String embedUrl = trimmedUrl;

            if (embedUrl.contains("?")) {
                embedUrl = embedUrl.substring(0, embedUrl.indexOf("?"));
            }

            return embedUrl + "?controls=0&disablekb=1&rel=0&modestbranding=1";
        }

        if (trimmedUrl.contains("youtube.com/watch?v=")) {
            videoId = trimmedUrl.substring(trimmedUrl.indexOf("v=") + 2);

            int ampIndex = videoId.indexOf("&");
            if (ampIndex != -1) {
                videoId = videoId.substring(0, ampIndex);
            }
        }

        if (trimmedUrl.contains("youtu.be/")) {
            videoId = trimmedUrl.substring(trimmedUrl.lastIndexOf("/") + 1);

            int questionIndex = videoId.indexOf("?");
            if (questionIndex != -1) {
                videoId = videoId.substring(0, questionIndex);
            }
        }

        if (videoId != null && !videoId.isEmpty()) {
            return "https://www.youtube.com/embed/" + videoId
                    + "?controls=0&disablekb=1&rel=0&modestbranding=1";
        }

        return trimmedUrl;
    }
}