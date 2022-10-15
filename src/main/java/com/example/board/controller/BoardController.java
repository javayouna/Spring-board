package com.example.board.controller;

import com.example.board.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.board.service.BoardService;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    //게시판 글 작성
    @GetMapping("/board/write") // http://localhost:8080/board/write
    public String boardWriteFrom(){
        return "boardwrite";
    }
    //게시판 글 전송
    @PostMapping("/board/writepro") // http://localhost:8080/board/write
    public String boardWritePro(Board board){
        boardService.write(board);
        return "";
    }
    //게시글 리스트
    @GetMapping("/board/list")
    public String boardList(Model model){
        model.addAttribute("list",boardService.boardList());
        return "boardlist";

    }
    //게시글 상세 페이지
    @GetMapping("/board/view")
    public String boardView(Model model,Integer id){
        model.addAttribute("board",boardService.boardView(id));
        return "boardview";
    }


}
