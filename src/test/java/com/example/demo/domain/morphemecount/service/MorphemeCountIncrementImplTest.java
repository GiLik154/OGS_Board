package com.example.demo.domain.morphemecount.service;

import com.example.demo.domain.board.domain.Board;
import com.example.demo.domain.board.domain.BoardRepository;
import com.example.demo.domain.morphemecount.domain.MorphemeCount;
import com.example.demo.domain.morphemecount.domain.MorphemeCountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MorphemeCountIncrementImplTest {
    private final MorphemeCountIncrement morphemeCountIncrement;
    private final MorphemeCountRepository morphemeCountRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public MorphemeCountIncrementImplTest(MorphemeCountIncrement morphemeCountIncrement, MorphemeCountRepository morphemeCountRepository, BoardRepository boardRepository) {
        this.morphemeCountIncrement = morphemeCountIncrement;
        this.morphemeCountRepository = morphemeCountRepository;
        this.boardRepository = boardRepository;
    }

    @Test
    void 형태소_사용횟수_증가_정상작동() {
        Board board = new Board("testTitle", "testContents", Collections.emptyList());
        boardRepository.save(board);

        MorphemeCount morphemeCount = new MorphemeCount("글로벌널리지글로벌널리지");
        morphemeCountRepository.save(morphemeCount);

        List<String> morphemeCounts = new ArrayList<>();
        morphemeCounts.add(morphemeCount.getWord());

        morphemeCountIncrement.increase(morphemeCounts);

        MorphemeCount testResult = morphemeCountRepository.findById("글로벌널리지글로벌널리지").get();

        assertEquals(1, testResult.getCount());
        assertEquals(100, testResult.getPercent());
    }

    @Test
    void 형태소_사용한적_없음() {
        Board board = new Board("testTitle", "testContents", Collections.emptyList());
        boardRepository.save(board);

        List<String> morphemeCounts = new ArrayList<>();
        morphemeCounts.add("글로벌널리지글로벌널리지");

        morphemeCountIncrement.increase(morphemeCounts);

        MorphemeCount testResult = morphemeCountRepository.findById("글로벌널리지글로벌널리지").get();

        assertNotNull(testResult.getWord());
        assertEquals(1, testResult.getCount());
        assertEquals(100, testResult.getPercent());
    }

    @Test
    void 형태소_사용한적_5회반복() {
        Board board = new Board("testTitle", "testContents", Collections.emptyList());
        boardRepository.save(board);

        List<String> morphemeCounts = new ArrayList<>();
        morphemeCounts.add("글로벌널리지글로벌널리지");

        morphemeCountIncrement.increase(morphemeCounts);
        morphemeCountIncrement.increase(morphemeCounts);
        morphemeCountIncrement.increase(morphemeCounts);
        morphemeCountIncrement.increase(morphemeCounts);
        morphemeCountIncrement.increase(morphemeCounts);

        MorphemeCount testResult = morphemeCountRepository.findById("글로벌널리지글로벌널리지").get();

        assertNotNull(testResult.getWord());
        assertEquals(5, testResult.getCount());
        assertEquals(100, testResult.getPercent());
    }

    @Test
    void 형태소_사용한적_게시글_점유율_2개() {
        Board board = new Board("testTitle", "testContents", Collections.emptyList());
        boardRepository.save(board);

        Board board2 = new Board("testTitle", "testContents", Collections.emptyList());
        boardRepository.save(board2);

        List<String> morphemeCounts = new ArrayList<>();
        morphemeCounts.add("글로벌널리지글로벌널리지");

        morphemeCountIncrement.increase(morphemeCounts);

        MorphemeCount testResult = morphemeCountRepository.findById("글로벌널리지글로벌널리지").get();

        assertNotNull(testResult.getWord());
        assertEquals(1, testResult.getCount());
        assertEquals(50, testResult.getPercent());
    }
}