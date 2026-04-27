package com.meta12.infoArchive.service;

import com.meta12.infoArchive.entity.test;
import com.meta12.infoArchive.repository.testRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class testService {
    private final testRepository testRepository;

    public List<test> list(){
        return testRepository.findAll();
    }



}
