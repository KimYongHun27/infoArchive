package com.meta12.infoArchive.service;

import com.meta12.infoArchive.dto.AnswerDto;
import com.meta12.infoArchive.entity.Answer;
import com.meta12.infoArchive.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer view(Long id){
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        Answer answer = null;
        if (optionalAnswer.isPresent()){
            answer = optionalAnswer.get();
        }
        return answer;
    }

    public Answer chugaProc(AnswerDto answerDto){
        Answer answer = new Answer();
        answer.setContent(answerDto.getContent());
        answer.setAuthor(answerDto.getAuthor());
        answerRepository.save(answer);
        return answer;
    }
    public Answer sujungProc(AnswerDto answerDto){
        Answer answer = new Answer();
        answer.setId(answerDto.getId());
        answer.setContent(answerDto.getContent());
        answer.setAuthor(answerDto.getAuthor());
        answerRepository.save(answer);
        return answer;
    }

    public Answer sakjeProc(AnswerDto answerDto){
        Answer answer = new Answer();
        answer.setId(answerDto.getId());
        answer.setContent(answerDto.getContent());
        answer.setAuthor(answerDto.getAuthor());
        answerRepository.delete(answer);
        return answer;
    }

}
