package com.example.demo.contorller;

import com.example.demo.domain.board.domain.Board;
import com.example.demo.domain.board.domain.BoardRepository;
import com.example.demo.domain.board.domain.RecommendBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardReadController {
    private final BoardRepository boardRepository;
    private final RecommendBoardRepository recommendBoardRepository;

    @GetMapping("/read/{boardId}")
    public String get(@PathVariable("boardId") Long boardId, @PageableDefault Pageable pageable, Model model) {
        Page<Board> recommendBoards = recommendBoardRepository.findByOriginBoardIdOrderByPriorityDesc(boardId, pageable);

        boardRepository.findById(boardId).ifPresent(board ->
                model.addAttribute("board", board));
        model.addAttribute("recommendBoards", recommendBoards);
        return "thymeleaf/read";
    }
}