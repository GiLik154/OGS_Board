package com.example.demo.domain.board.service;

import com.example.demo.domain.board.domain.Board;
import com.example.demo.domain.board.domain.BoardRepository;
import com.example.demo.domain.board.domain.RecommendBoard;
import com.example.demo.domain.board.domain.RecommendBoardRepository;
import com.example.demo.domain.board.domain.aplication.ContentsAnalyzer;
import com.example.demo.domain.board.service.dto.BoardCreatorCommand;
import com.example.demo.domain.board.service.dto.BoardUpdaterCommand;
import com.example.demo.domain.morphemecount.domain.MorphemeCount;
import com.example.demo.domain.morphemecount.domain.MorphemeCountRepository;
import com.example.demo.domain.morphemecount.service.MorphemeCountDecrement;
import com.example.demo.domain.morphemecount.service.MorphemeCountIncrement;
import com.example.demo.domain.morphemecount.service.MorphemeCountPercentUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService implements BoardCreator, BoardDeleter, BoardRecommender, BoardUpdater {
    private final ContentsAnalyzer contentsAnalyzer;
    private final MorphemeCountIncrement morphemeCountIncrement;
    private final MorphemeCountDecrement morphemeCountDecrement;
    private final MorphemeCountPercentUpdater morphemeCountPercentUpdater;
    private final BoardRepository boardRepository;
    private final RecommendBoardRepository recommendBoardRepository;
    private final MorphemeCountRepository morphemeCountRepository;

    private static final int RECOMMEND_TERMS_PERCENT = 60;

    @Override
    public void create(BoardCreatorCommand boardCreatorCommand) {
        List<String> morphemes = contentsAnalyzer.analyze(boardCreatorCommand.getContents());
        List<MorphemeCount> morphemeCounts = morphemeCountIncrement.increase(morphemes);

        Board board = new Board(boardCreatorCommand.getTitle(), boardCreatorCommand.getContents(), morphemeCounts);
        boardRepository.save(board);

        recommend(board, morphemes);

        morphemeCountPercentUpdater.update();
    }

    @Override
    public void delete(Long boardId) {
        boardRepository.findById(boardId).ifPresent(board -> {
            morphemeCountDecrement.decrease(board.getMorphemeCount());

            recommendBoardRepository.deleteAllByBoardId(boardId);
            boardRepository.delete(board);

            morphemeCountPercentUpdater.update();
        });
    }

    @Override
    public void recommend(Board board, List<String> morphemeList) {
        List<String> recommendWord = morphemeCountRepository.findWordByWordInAndPercentLessThan(morphemeList, RECOMMEND_TERMS_PERCENT);

        boardRepository.findRecommendBoardWithCount(recommendWord).forEach(morphemeMatchCounter -> {
            Board relatedBoard = morphemeMatchCounter.getBoard();
            long count = morphemeMatchCounter.getCount();

            registerBoardRecommendations(board, relatedBoard, count);
        });
    }

    private void registerBoardRecommendations(Board board, Board relatedBoard, Long count) {
        Long boardId = board.getId();

        if (!boardId.equals(relatedBoard.getId())) {
            RecommendBoard boardToRelated = new RecommendBoard(board, relatedBoard, count);
            RecommendBoard relatedToBoard = new RecommendBoard(relatedBoard, board, count);

            List<RecommendBoard> recommendBoards = List.of(boardToRelated, relatedToBoard);

            recommendBoardRepository.saveAll(recommendBoards);
        }
    }


    @Override
    public void update(Long boardId, BoardUpdaterCommand boardUpdaterCommand) {
        List<String> morphemes = contentsAnalyzer.analyze(boardUpdaterCommand.getContents());
        List<MorphemeCount> morphemeCountList = morphemeCountIncrement.increase(morphemes);

        boardRepository.findById(boardId).ifPresent(board -> {
                    morphemeCountDecrement.decrease(board.getMorphemeCount());
                    board.update(boardUpdaterCommand.getTitle(), boardUpdaterCommand.getContents(), morphemeCountList);
                    recommend(board, morphemes);
                }
        );
    }
}
