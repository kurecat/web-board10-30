package com.human.web_board.service.impl;

import com.human.web_board.dao.MemberDao;
import com.human.web_board.dto.MemberRes;
import com.human.web_board.dto.MemberSignupReq;
import com.human.web_board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service   // Spring Container에 Bean 등록
@RequiredArgsConstructor  // 생성자를 통한 의존성을 주입을 위해 생성자 생성
public class MemberServiceImpl implements MemberService {
    private final MemberDao memberDao;  // 생성자를 통한 의존성 주입
    @Override
    @Transactional  // 원자성을 가짐, 실패 시 자동 롤백
    public Long signup(MemberSignupReq req) {
        // 이미 존재하는 이메일에 대한 예외처리
        if (memberDao.findByEmail(req.getEmail()) != null) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }
        return memberDao.save(req);
    }

    @Override
    public MemberRes getByEmail(String email) {
        return memberDao.findByEmail(email);
    }

    @Override
    public MemberRes getById(Long id) {
        return memberDao.findById(id);
    }

    @Override
    public List<MemberRes> list() {
        return memberDao.findAll();
    }
}
