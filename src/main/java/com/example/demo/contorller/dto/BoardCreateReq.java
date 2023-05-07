package com.example.demo.contorller.dto;

import com.example.demo.domain.board.service.dto.BoardCreatorCommand;
import lombok.Getter;

@Getter
public class BoardCreateReq {
    private final String title;
    private final String contents;

    public BoardCreateReq(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public BoardCreatorCommand encodingServiceCommand() {
        return new BoardCreatorCommand(this.title, this.contents);
    }
}
