package com.its.memberboard.controller;

import com.its.memberboard.DTO.BoardDTO;
import com.its.memberboard.DTO.CommentDTO;
import com.its.memberboard.service.BoardService;
import com.its.memberboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;


    @GetMapping("/board/save")
    public String saveForm() {
        return "boardPages/board_save";
    }

    @PostMapping("/board/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        return "redirect:/board/list";
    }


    @GetMapping("/board/list")
    public String findAll(Model model,
                          @PageableDefault(page = 1) Pageable pageable) {
        Page<BoardDTO> boardDTOPage = boardService.paging(pageable);
        model.addAttribute("paging", boardDTOPage);

        int blockLimit = 5;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < boardDTOPage.getTotalPages()) ? startPage + blockLimit - 1 : boardDTOPage.getTotalPages();

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardPages/board_list";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id,
                           Model model) {
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        List<CommentDTO> commentDTOList = commentService.findAll(id);

        if (commentDTOList.size() > 0) {
            model.addAttribute("commentList", commentDTOList);
        } else {
            model.addAttribute("commentList", "none");
        }

        model.addAttribute("findById", boardDTO);
        System.out.println("boardDTO = " + boardDTO);
        System.out.println("commentDTOList = " + commentDTOList);
        return "boardPages/board_detail";
    }

    @DeleteMapping("/board/delete/{id}")
    public ResponseEntity deleteByAxios(@PathVariable Long id) {
        boardService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/board/update")
    public String updateForm(@RequestParam("id") Long id,
                             Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("update", boardDTO);
        return "boardPages/board_update";
    }

    @PostMapping("/board/update")
    public String update(@ModelAttribute BoardDTO boardDTO) {
        boardService.update(boardDTO);
        return "redirect:/board/list";

    }

}
