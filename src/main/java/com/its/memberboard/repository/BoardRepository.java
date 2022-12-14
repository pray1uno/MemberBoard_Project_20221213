package com.its.memberboard.repository;

import com.its.memberboard.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits = b.boardHits+1 where b.id = :id")
    void updateHits(@Param("id") Long id);

    Page<BoardEntity> findByBoardWriterContainingOrderByIdDesc(String keyword, PageRequest id);

    Page<BoardEntity> findByBoardTitleContainingOrderByIdDesc(String keyword, PageRequest id);
}
