package com.example.demo.contorller;

import com.example.demo.domain.board.service.BoardDeleter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardDeleteController {
    private final BoardDeleter boardDeleter;

    @PostMapping("/delete/{boardId}")
    public String get(@PathVariable("boardId") Long boardId) {
        boardDeleter.delete(boardId);
        return "redirect:/board/list";
    }
}
