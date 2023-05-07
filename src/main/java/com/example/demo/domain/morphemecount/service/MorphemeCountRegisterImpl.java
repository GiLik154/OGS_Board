package com.example.demo.domain.morphemecount.service;

import com.example.demo.domain.morphemecount.domain.MorphemeCount;
import com.example.demo.domain.morphemecount.domain.MorphemeCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MorphemeCountRegisterImpl implements MorphemeCountRegister {
    private final MorphemeCountRepository morphemeCountRepository;

    @Override
    public MorphemeCount register(String word) { // 자체적으로 보호 or private method
        if (!morphemeCountRepository.existsById(word)) {
            MorphemeCount morphemeCount = new MorphemeCount(word);
            morphemeCountRepository.save(morphemeCount);
        }

        return morphemeCountRepository.findById(word).orElseThrow(IllegalStateException::new);
    }
}