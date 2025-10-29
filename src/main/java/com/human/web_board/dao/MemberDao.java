package com.human.web_board.dao;

import com.human.web_board.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.intellij.lang.annotations.Language;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// DB와 정보를 주고 받기 위한 SQL 작성 구간
@Repository     //  Spring Container 에 Bean 객체 등록, 싱글톤 객체가 됨
@RequiredArgsConstructor  // 생성자를 통한 의존성 주입을 하기 위해서 사용
@Slf4j  // log 메세지 출력을 지원하기 위한  lombok 기능
public class MemberDao {
    private final JdbcTemplate jdbc;  // JdbcTemplate을 의존성 주입 받음

    // 회원 가입
    public Long save(Member member) {
        @Language("SQL")
        String sql = "INSERT INTO member(id, email, pwd, name) VALUES (seq_member.NEXTVAL, ?, ?, ?)";
        jdbc.update(sql, member.getEmail(), member.getPwd(), member.getName());
        return jdbc.queryForObject("SELECT seq_member.CURRVAL FORM dual", Long.class);  // Long 타입의 id를 반환
    }

    // 이메일로 회원 조회
    public Member findByEmail(String email) {
        @Language("SQL")
        String sql = "SELECT * FROM member WHERE email=?";
        List<Member> list = jdbc.query(sql, new MemberRowMapper(), email);
        return list.isEmpty() ? null : list.get(0); // 조회 시 결과가 없는 경우 null을 넣기 위해서
    }

    // ID로 회원 조회
    public Member findById(Long id) {
        @Language("SQL")
        String sql = "SELECT * FROM member WHERE id=?";
        List<Member> list = jdbc.query(sql, new MemberRowMapper(), id);
        return list.isEmpty() ? null : list.get(0); // 조회 시 결과가 없는 경우 null을 넣기 위해서
    }

    // 전체 회원 조회
    public List<Member> findAll() {
        @Language("SQL")
        String sql = "SELECT * from member ORDER BY id DESC";
        return jdbc.query(sql, new MemberRowMapper());
    }

    // Mapper 메서드  DB -> Member
    static class MemberRowMapper implements RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Member(
              rs.getLong("id"),
              rs.getString("email"),
              rs.getString("pwd"),
              rs.getString("name"),
              rs.getTimestamp("reg_date").toLocalDateTime()
            );
        }
    }


}
