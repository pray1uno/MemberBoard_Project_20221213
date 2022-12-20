package com.its.memberboard.service;

import com.its.memberboard.DTO.BoardDTO;
import com.its.memberboard.entity.BoardEntity;
import com.its.memberboard.entity.BoardFileEntity;
import com.its.memberboard.repository.BoardFileRepository;
import com.its.memberboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    public Long save(BoardDTO boardDTO) throws IOException {
        System.out.println("board File" + boardDTO.getBoardFile());
        System.out.println("boardFileSize" + boardDTO.getBoardFile().size());
        if (boardDTO.getBoardFile().get(0).isEmpty()) {
            System.out.println("걸리냐?");
            BoardEntity boardEntity = BoardEntity.toBoardSaveEntity(boardDTO);
            return boardRepository.save(boardEntity).getId();
        } else {
            System.out.println("왜 안되냐");
            BoardEntity boardEntity = BoardEntity.toBoardSaveFileEntity(boardDTO);
            Long saveId = boardRepository.save(boardEntity).getId();
            BoardEntity entity = boardRepository.findById(saveId).get();

            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                String originalFileName = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
                String savePath = "D:\\spring_boot_img\\" + storedFileName;
                boardFile.transferTo(new File(savePath));

                BoardFileEntity boardFileEntity = BoardFileEntity.toSaveBoardFileEntity(entity, originalFileName, storedFileName);
                boardFileRepository.save(boardFileEntity);
            }
            return saveId;
        }
    }

//    public List<BoardDTO> findAll() {
//        List<BoardEntity> entityList = boardRepository.findAll();
//        List<BoardDTO> boardDTOList = new ArrayList<>();
//
//        for (BoardEntity boardEntity : entityList) {
//            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
//        }
//        return boardDTOList;
//    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);

        if (optionalBoardEntity.isPresent()) {
            return BoardDTO.toBoardDTO(optionalBoardEntity.get());
        } else {
            return null;
        }
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public void update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toBoardUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() -1;
        final int pageLimit = 5;
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        Page<BoardDTO> boardDTO = boardEntities.map(
                board -> new BoardDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardContents(),
                        board.getBoardHits(),
                        board.getCreatedTime(),
                        board.getUpdatedTime()
                )
        );
        return boardDTO;
    }
}
