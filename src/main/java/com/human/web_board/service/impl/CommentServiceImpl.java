package com.human.web_board.service.impl;

import com.human.web_board.dao.CommentDao;
import com.human.web_board.dao.MemberDao;
import com.human.web_board.dao.PostDao;
import com.human.web_board.dto.CommentCreateReq;
import com.human.web_board.dto.CommentRes;
import com.human.web_board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;
    private final PostDao postDao;
    private final MemberDao memberDao;
    @Override
    @Transactional
    public Long write(CommentCreateReq req) {
        if(memberDao.findById(req.getMemberId()) == null) {
            throw new IllegalArgumentException("존재 하지 않은 회원 입니다.");
        }
        if(postDao.findById(req.getPostId()) == null) {
            throw new IllegalArgumentException("존재하지 않는 게시글 입니다.");
        }
        return commentDao.save(req);
    }

    @Override
    public List<CommentRes> list(Long postId) {
        if (postDao.findById(postId) == null) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다. ID: " + postId);
        }

        List<CommentRes> list;
        try{
            list = commentDao.findByPostId(postId);
        } catch (DataAccessException e){
            log.error("댓글 목록 조회 중 DB 예외 발생: {}", e.getMessage());
            throw new IllegalArgumentException("댓글 목록을 조회 할 수 없습니다.");
        }

        return list;
    }


    @Override
    public boolean update(CommentCreateReq req, Long id) {
        CommentRes commentRes = commentDao.findById(id);

        if (commentRes == null) {
            throw new IllegalArgumentException("존재하지 않는 댓글입니다. ID: " + id);
        }

        return commentDao.update(req, id);
    }

    @Override
    public boolean delete(Long id) {
        CommentRes commentRes = commentDao.findById(id);
        if (commentRes == null) {
            throw new IllegalArgumentException("존재하지 않는 댓글 입니다. ID: " + id);
        }
        return commentDao.delete(id);
    }
}