package com.example.demo.domain.morphemecount.service;

import com.example.demo.domain.board.domain.BoardRepository;
import com.example.demo.domain.morphemecount.domain.MorphemeCount;
import com.example.demo.domain.morphemecount.domain.MorphemeCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MorphemeCountIncrementImpl implements MorphemeCountIncrement {
    private final MorphemeCountRegister morphemeCountRegister;
    private final BoardRepository boardRepository;
    private final MorphemeCountRepository morphemeCountRepository;

    @Override
    public List<MorphemeCount> increase(List<String> morphemeCounts) {
        return morphemeCounts.stream()
                .map(this::getIncreaseCount)
                .collect(Collectors.toList());
    }

    private MorphemeCount getIncreaseCount(String morpheme) {
        long totalBoardCount = boardRepository.count();

        MorphemeCount morphemeCount = morphemeCountRepository.findById(morpheme)
                .orElseGet(() -> morphemeCountRegister.register(morpheme));

        morphemeCount.increaseCount(totalBoardCount);

        return morphemeCount;
    }
}