package com.example.demo.domain.morphemecount.service;

import com.example.demo.domain.board.domain.BoardRepository;
import com.example.demo.domain.morphemecount.domain.MorphemeCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MorphemeCountPercentUpdaterImpl implements MorphemeCountPercentUpdater {
    private final BoardRepository boardRepository;
    private final MorphemeCountRepository morphemeCountRepository;

    @Override
    public void update() {
        int totalBoardCount = (int) boardRepository.count();

        morphemeCountRepository.findAll().forEach(morphemeCount ->
                morphemeCount.calculatePercent(totalBoardCount));
    }
}
