package com.its.memberboard.controller;

import com.its.memberboard.DTO.CommentDTO;
import com.its.memberboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/save")
    public ResponseEntity commentSave(@RequestBody CommentDTO commentDTO) {
        commentService.save(commentDTO);
        System.out.println("commentDTO = " + commentDTO);
        List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
        System.out.println("commentDTOList = " + commentDTOList);
        return new ResponseEntity(commentDTOList, HttpStatus.OK);

    }
}
