package com.example.demo.domain.board.domain.aplication;

import java.util.List;

public interface ContentsAnalyzer {
    /**
     * 주석추가
     * @param contents
     * @return
     */
    List<String> analyze(String contents);
}