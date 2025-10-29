package com.human.web_board.controller;

import com.human.web_board.dto.PostCreateReq;
import com.human.web_board.dto.PostRes;
import com.human.web_board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/posts")
public class PostRestController {
    private final PostService postService;

    @PostMapping  // 게시글 등록
    public ResponseEntity<Long> create(@RequestBody PostCreateReq req) {
        return ResponseEntity.ok(postService.write(req));
    }

    @GetMapping   // 게시글 전체 조회
    public ResponseEntity<List<PostRes>> list() {
        return ResponseEntity.ok(postService.list());
    }

    @GetMapping("/{id}")  // 게시글 상세 조회 (ID)
    public ResponseEntity<PostRes> detail(@PathVariable Long id) {
        return ResponseEntity.ok(postService.get(id));
    }
}
