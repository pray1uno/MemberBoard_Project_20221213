package com.its.memberboard.entity;

import com.its.memberboard.DTO.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String CommentWriter;

    @Column(length = 500)
    private String CommentContents;

    @Column
    private LocalDateTime CommentCreatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static CommentEntity toCommentSaveEntity(BoardEntity entity, CommentDTO commentDTO) {
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setCommentCreatedDate(commentDTO.getCommentCreatedDate());
        commentEntity.setBoardEntity(entity);

        return commentEntity;
    }
}
