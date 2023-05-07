package com.example.demo.contorller;

import com.example.demo.domain.board.domain.Board;
import com.example.demo.domain.board.domain.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardListController {
    private final BoardRepository boardRepository;

    @GetMapping("/list")
    public String get(@PageableDefault Pageable pageable, Model model) {
        Page<Board> boards = boardRepository.findAll(pageable);

        model.addAttribute("boards", boards);
        return "thymeleaf/list";
    }
}
