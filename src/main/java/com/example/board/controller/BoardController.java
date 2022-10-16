package com.example.board.controller;

import com.example.board.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.board.service.BoardService;
import org.springframework.web.multipart.MultipartFile;


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
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception{
        boardService.write(board,file);

        model.addAttribute("message","글 작성이 완료되었습니다.");
        //model.addAttribute("message","글 작성이 실패하였습니다.");
        model.addAttribute("searchUrl","/board/list");

        return "message";
    }
    //게시글 리스트
    @GetMapping("/board/list")
    public String boardList(Model model, @PageableDefault(page=0, size=10, sort ="id", direction = Sort.Direction.DESC) Pageable pageable) {
       Page<Board> list = boardService.boardList(pageable);

       int nowPage=list.getPageable().getPageNumber()+1;
       int startPage =Math.max(nowPage -4,1);
       int endPage=Math.min(nowPage+5,list.getTotalPages());
       int lastendPage=Math.min(nowPage,list.getTotalPages());
        model.addAttribute("list",list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("lastendPage",lastendPage);
        return "boardlist";

    }
    //게시글 상세 페이지
    @GetMapping("/board/view")
    public String boardView(Model model,Integer id){
        model.addAttribute("board",boardService.boardView(id));
        return "boardview";
    }

    //게시글 삭제 http://localhost:8080/board/delete?id=1
    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }
    //게시글 수정
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id,Model model){
    model.addAttribute("board", boardService.boardView(id));
        return "boardmodify";
    }

    //게시글 업데이트
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id,Board board, Model model,MultipartFile file) throws Exception{
        Board boardTemp=boardService.boardView(id); //기존 글 보여주기
        boardTemp.setTitle(board.getTitle()); //제목
        boardTemp.setContent(board.getContent()); //내용
        boardService.write(boardTemp,file);

        model.addAttribute("message","글 수정이 완료되었습니다.");
        //model.addAttribute("message","글 작성이 실패하였습니다.");
        model.addAttribute("searchUrl","/board/list");
        return "message";
    }

}
