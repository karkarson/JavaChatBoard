package com.blogspot.noteoneverything.chatboard.service;

import java.util.List;
import java.util.Date;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;
import com.blogspot.noteoneverything.chatboard.service.BoardService;
import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;
import com.blogspot.noteoneverything.chatboard.dao.BoardRepository;
import com.blogspot.noteoneverything.chatboard.dao.BoardResponseRepository;
import org.springframework.data.domain.Pageable;

@Service
public class BoardServiceImpl implements BoardService{
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardResponseRepository boardResponseRepository;

    @Transactional
    @Override
    public boolean createBoard(Board board) {
        Date now = new Date();
        try{
            board.setCreated(now);
            board.setUpdated(now);
            this.boardRepository.save(board);
            return true;
        }catch(DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBoard(Board board) {
        try{
            this.boardRepository.deleteBoardById(board.getId());
            return true;
        }catch(DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBoard(long id) {
        try{
            this.boardRepository.deleteBoardById(id);
            return true;
        }catch(DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    @Override
    public boolean createBoardResponse(BoardResponse boardResponse) {
        Date now = new Date();
        try{
            boardResponse.setCreated(now);
            boardResponse.setUpdated(now);
            this.boardResponseRepository.save(boardResponse);
            return true;
        }catch(DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBoardResponse(BoardResponse boardResponse) {
        try{
            this.boardResponseRepository.deleteBoardResponseById(boardResponse.getId());
            return true;
        }catch(DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBoardResponse(long id) {
        try{
            this.boardResponseRepository.deleteBoardResponseById(id);
            return true;
        }catch(DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Board> findBoardsByUser(User user){
        return boardRepository.findBoardsByUser(user);
    };

    @Override
    public List<Board> findBoardsByUser(User user, Pageable pageable){
        return boardRepository.findBoardsByUser(user, pageable);
    };

    @Override
    public List<BoardResponse> findBoardResponsesByBoard(Board board){
        return boardResponseRepository.findBoardResponsesByBoard(board);
    };

    @Override
    public List<BoardResponse> findBoardResponsesByBoard(Board board, Pageable pageable){
        return boardResponseRepository.findBoardResponsesByBoard(board, pageable);
    };

    @Override
    public List<BoardResponse> findBoardResponsesByUser(User user){
        return boardResponseRepository.findBoardResponsesByUser(user);
    };

    @Override
    public List<BoardResponse> findBoardResponsesByUser(User user, Pageable pageable){
        return boardResponseRepository.findBoardResponsesByUser(user, pageable);
    };

    @Override
    public List<BoardResponse> findBoardResponsesByBoardAndUser(Board board, User user){
        return boardResponseRepository.findBoardResponsesByBoardAndUser(board, user);
    };

    @Override
    public List<BoardResponse> findBoardResponsesByBoardAndUser(Board board, User user, Pageable pageable){
        return boardResponseRepository.findBoardResponsesByBoardAndUser(board, user, pageable);
    };
}