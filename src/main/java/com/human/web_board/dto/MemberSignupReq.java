package com.human.web_board.dto;

import lombok.*;

import java.time.LocalDateTime;

// 회원 가입 요청
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class MemberSignupReq {
    private String email;   // 이메일
    private String pwd;     // 비밀번호
    private String name;    // 이름
}
