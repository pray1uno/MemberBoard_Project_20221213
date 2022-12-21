package com.its.memberboard.DTO;

import com.its.memberboard.entity.BoardEntity;
import com.its.memberboard.entity.BoardFileEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class BoardDTO {
    private Long id;
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private int boardHits;
    private String fileAttached;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    private List<MultipartFile> boardFile;
    private List<String> originalFileName;
    private List<String> storedFileName;

    public BoardDTO() {

    }

    public BoardDTO(Long id, String boardTitle, String boardWriter, String boardContents, int boardHits, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardContents = boardContents;
        this.boardHits = boardHits;
        this.boardCreatedTime = createdTime;
        this.boardUpdatedTime = updatedTime;
    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());

        if (boardEntity.getFileAttached().equals("Y")) {
            boardDTO.setFileAttached(boardEntity.getFileAttached());
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();

            for (BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntityList()) {
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }

            boardDTO.setOriginalFileName(originalFileNameList);
            boardDTO.setStoredFileName(storedFileNameList);

        } else {
            boardDTO.setFileAttached(boardEntity.getFileAttached());
        }

        return boardDTO;
    }
}
