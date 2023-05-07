package com.example.demo.contorller;

import com.example.demo.contorller.dto.BoardCreateReq;
import com.example.demo.domain.board.service.BoardCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardCreateController {
    private final BoardCreator boardCreator;

    @GetMapping("/create")
    public String get() {
        return "thymeleaf/create";
    }

    @PostMapping("/create")
    public String post(BoardCreateReq boardCreateReq) {
        boardCreator.create(boardCreateReq.encodingServiceCommand());
        return "redirect:/board/list";
    }
}
