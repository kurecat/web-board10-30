package com.human.web_board.service.impl;

import com.human.web_board.dao.MemberDao;
import com.human.web_board.dao.PostDao;
import com.human.web_board.dto.MemberRes;
import com.human.web_board.dto.PostCreateReq;
import com.human.web_board.dto.PostRes;
import com.human.web_board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostDao postDao;
    private final MemberDao memberDao;
    @Override
    @Transactional
    public Long write(PostCreateReq req) {
        if (memberDao.findById(req.getMemberId()) == null) {
            throw new IllegalArgumentException("존재하지 않는 회원 입니다.");
        }
        return postDao.save(req);
    }

    @Override
    public List<PostRes> list() {
        List<PostRes> list = null;
        try {
            list = postDao.findAll();
        } catch (DataAccessException e){
            log.error("게시글 조회 중 DB 예외 발생: {}", e.getMessage());
        }
        return postDao.findAll();
    }

    @Override
    public PostRes get(Long id) {
        PostRes postRes = postDao.findById(id);
        if (postRes == null) throw new IllegalArgumentException("존재하지 않는 게시글 입니다.");
        return postRes;
    }

    @Override
    public boolean edit(PostCreateReq req, Long id) {
        PostRes postRes = postDao.findById(id);
        if (postRes == null) throw new IllegalArgumentException("존재하지 않는 게시글 입니다.");
        return postDao.update(id, req.getTitle(), req.getContent());
    }

    @Override
    public boolean delete(Long id) {
        PostRes postRes = postDao.findById(id);
        if (postRes == null) throw new IllegalArgumentException("존재하지 않는 게시글 입니다.");
        return postDao.delete(id);
    }
}
