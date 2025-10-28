package com.human.web_board.dto;

import lombok.*;

import java.time.LocalDateTime;

// 게시글 응답
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class PostRes {
    private Long id;
    private Long memberId;
    private String memberEmail;  // 회원 테이블에 있는 정보를 조인해서 가져 옴
    private String title;
    private String content;
    private LocalDateTime createAt;
}
