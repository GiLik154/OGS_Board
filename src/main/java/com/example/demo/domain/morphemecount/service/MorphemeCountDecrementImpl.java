package com.example.demo.domain.morphemecount.service;

import com.example.demo.domain.board.domain.BoardRepository;
import com.example.demo.domain.morphemecount.domain.MorphemeCount;
import com.example.demo.domain.morphemecount.domain.MorphemeCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class MorphemeCountDecrementImpl implements MorphemeCountDecrement {
    private final BoardRepository boardRepository;
    private final MorphemeCountRepository morphemeCountRepository;

    @Override
    public void decrease(List<MorphemeCount> morphemeCounts) {
        long totalBoardCount = boardRepository.count(); // 캐스팅 없이 long을 사용할 수 있도록

        morphemeCounts.forEach(morphemeCount ->
                morphemeCount.decreaseCount(totalBoardCount));
    }
}