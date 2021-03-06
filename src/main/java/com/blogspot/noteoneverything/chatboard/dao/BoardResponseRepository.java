package com.blogspot.noteoneverything.chatboard.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;
import org.springframework.data.domain.Pageable;

@Repository
public interface BoardResponseRepository extends JpaRepository<BoardResponse,Long>{
    @Query("select br from BoardResponse br where br.id = id and br.is_deleted = 0")
    BoardResponse findBoardResponseByIdWithUser(@Param("id") long id);
    @Query("select br from BoardResponse br where br.user = :user and is_deleted = 0")
    List<BoardResponse> findBoardResponsesByUser(@Param("user") User user);
    @Query("select br from BoardResponse br where br.user = :user and is_deleted = 0")
    List<BoardResponse> findBoardResponsesByUser(@Param("user") User user, Pageable pageable);
    @Query("select br from BoardResponse br where br.board = :board and is_deleted = 0")
    List<BoardResponse> findBoardResponsesByBoard(@Param("board") Board board);
    @Query("select br from BoardResponse br where br.board = :board and is_deleted = 0")
    List<BoardResponse> findBoardResponsesByBoard(@Param("board") Board board, Pageable pageable);
    @Query("select br from BoardResponse br where br.board = :board and br.user = :user and is_deleted = 0")
    List<BoardResponse> findBoardResponsesByBoardAndUser(@Param("board") Board board, @Param("user") User user);
    @Query("select br from BoardResponse br where br.board = :board and br.user = :user and is_deleted = 0")
    List<BoardResponse> findBoardResponsesByBoardAndUser(@Param("board") Board board, @Param("user") User user, Pageable pageable);
    @Modifying(clearAutomatically = true)
    @Query("update BoardResponse br set is_deleted = 1 where br.id = :id")
    boolean deleteBoardResponseById(@Param("id") long id);
    @Query(value =   "SELECT * FROM "
                   +   "(select * from board_responses where board_id in ((select distinct br.board_id from board_responses br left join boards b on br.board_id = b.id where b.user_id = :user_id or br.user_id = :user_id))) b1 " 
                   + "left join "
                   +   "(select * from board_responses where board_id in ((select distinct br.board_id from board_responses br left join boards b on br.board_id = b.id where b.user_id = :user_id or br.user_id = :user_id))) b2 " 
                   + "on " 
                   +   "b1.board_id = b2.board_id and b1.created < b2.created " 
                   + "where " 
                   +   "b2.id is null;"
           ,nativeQuery = true)
    List<BoardResponse> getLatestResponsePerBoard(@Param("user_id") long user_id);
}