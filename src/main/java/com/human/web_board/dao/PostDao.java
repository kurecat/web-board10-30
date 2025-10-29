package com.human.web_board.dao;

import com.human.web_board.dto.PostCreateReq;
import com.human.web_board.dto.PostRes;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.intellij.lang.annotations.Language;

@Repository
@RequiredArgsConstructor
public class PostDao {
    private final JdbcTemplate jdbc;

    // 게시글 등록
    public Long save(PostCreateReq p) {
        @Language("SQL")
        String sql = "INSERT INTO post(id, member_id, title, content) VALUES (swr_post.NEXTVAL, ?, ?, ?)";
        jdbc.update(sql, p.getMemberId(), p.getTitle(), p.getContent());
        return jdbc.queryForObject("SELECT seq_post.CURRVAL FROM dual", Long.class);
    }

    // 게시글 목록 보기
    public List<PostRes> findAll() {
        @Language("SQL")
        String sql = """
                SELECT p.id, p.member_id, m.email, p.title, p.content, p.created_at
                FROM post p JOIN member m ON p.member_id = m.id
                ORDER BY p.id DESC
                """;
        return jdbc.query(sql, new PostResPowMapper());
    }

    // id로 게시글 가져 오기
    public PostRes findById(Long id) {
        @Language("SQL")
        String sql = """
                SELECT p.id, p.member_id, m.email, p.title, p.content, p.created_at
                FROM post p JOIN member m ON p.member_id = m.id
                WHERE p.id =?
                """;
        List<PostRes> list = jdbc.query(sql, new PostResPowMapper(), id);
        return list.isEmpty() ? null : list.get(0);
    }

    // 게시글 수정
    public boolean update(PostCreateReq p, Long id) {
        @Language("SQL")
        String sql = "UPDATE post SET title=?, content=? WHERE id=?";
        return jdbc.update(sql, p.getTitle(), p.getContent(), id) > 0;
    }

    // 게시글 삭제
    public boolean delete(Long id) {
        @Language("SQL")
        String sql = "DELETE FROM post WHERE id=?";
        return jdbc.update(sql, id) > 0;
    }

    // mapper 메서드 생성
    static class PostResPowMapper implements RowMapper<PostRes> {
        @Override
        public PostRes mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new PostRes(
                    rs.getLong("id"),
                    rs.getLong("member_id"),
                    rs.getString("email"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        }
    }
}