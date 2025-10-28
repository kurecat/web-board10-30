package com.human.web_board.dto;

import lombok.*;

import java.time.LocalDateTime;

// 댓글 조회
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class CommentRes {
    private Long id;
    private Long postId;
    private Long memberId;
    private String memberEmail;
    private String content;
    private LocalDateTime createAt;
}
