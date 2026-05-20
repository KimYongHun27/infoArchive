package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.AnswerDto;
import com.meta12.infoArchive.dto.CommunityDto;
import com.meta12.infoArchive.entity.Answer;
import com.meta12.infoArchive.entity.Community;
import com.meta12.infoArchive.entity.User;
import com.meta12.infoArchive.repository.AnswerRepository;
import com.meta12.infoArchive.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final CommunityRepository communityRepository;
    private final UserService userService;

    public void save(Community community, User user, String content){
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCommunity(community);
        answer.setUser(user);
        answerRepository.save(answer);
    }

    public Community detail(Long id){
        Optional<Community> oc = communityRepository.findById(id);
        Community community = null;
        if (oc.isPresent()) {
            community = oc.get();
        }
        return community;
    }

    public void deleteById(Long id){
        if (answerRepository.existsById(id)) {
            answerRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id);
        }
    }
    @Transactional
    public void saveReply(Community community, User user, String content, Long parentId) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCommunity(community);
        answer.setUser(user);

        // 부모 댓글이 있는지 확인하고 찾아와서 연결합니다.
        Answer parent = answerRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다. id=" + parentId));

        answer.setParent(parent); // 부모 댓글 지정!
        answerRepository.save(answer);
    }
}

