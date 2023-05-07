package com.example.demo.contorller;

import com.example.demo.contorller.dto.BoardUpdaterReq;
import com.example.demo.domain.board.domain.BoardRepository;
import com.example.demo.domain.board.service.BoardUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardUpdateController {
    private final BoardUpdater boardUpdater;
    private final BoardRepository boardRepository;

    @GetMapping("/update/{boardId}")
    public String get(@PathVariable("boardId") Long boardId, Model model) {
        boardRepository.findById(boardId).ifPresent(board ->
                model.addAttribute("board", board));
        return "thymeleaf/update";
    }

    @PostMapping("/update/{boardId}")
    public String put(@PathVariable("boardId") Long boardId, BoardUpdaterReq boardUpdaterReq) {
        boardUpdater.update(boardId, boardUpdaterReq.encodingServiceCommand());

        return "redirect:/board/read/" + boardId;
    }
}