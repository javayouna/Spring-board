package com.example.board.service;

import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;


    //글작성
    public void write(Board board, MultipartFile file) throws  Exception{
        //저장경로
         String projectPath=System.getProperty("user.dir")
                 +"\\src\\main\\resources\\static\\files";
        //랜덤함수
         UUID uuid=UUID.randomUUID();
         //파일이름
        String fileName=uuid+"_"+file.getOriginalFilename();
         File saveFile = new File(projectPath,fileName);
         file.transferTo(saveFile);
         board.setFilename(fileName);
         board.setFilepath("/files/"+fileName);
         boardRepository.save(board);

    }
    //리스트
    public Page<Board> boardList(Pageable pageable){
      return boardRepository.findAll(pageable);
    }
    //상세페이지
    public Board boardView(Integer id){
        return boardRepository.findById(id).get();
    }

    //특정게시물 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }


}
