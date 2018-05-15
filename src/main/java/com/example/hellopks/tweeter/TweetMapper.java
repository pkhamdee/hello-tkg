package com.example.hellopks.tweeter;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public class TweetMapper {
    private final JdbcTemplate jdbcTemplate;

    public TweetMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insert(Tweet tweet) {
        return this.jdbcTemplate.update(
                "INSERT INTO tweets(uuid, text, username, created_at) VALUES(?,?,?,?)",
                tweet.getUuid().toString(), tweet.getText(), tweet.getUsername(),
                Timestamp.from(tweet.getCreatedAt()));
    }

    public long count() {
        return this.jdbcTemplate.queryForObject("SELECT count(*) FROM tweets",
                Long.class);
    }

    public List<Tweet> findAll() {
        return this.jdbcTemplate.query(
                "SELECT uuid, text, username, created_at FROM tweets ORDER BY created_at DESC LIMIT 50",
                (rs, i) -> new Tweet(UUID.fromString(rs.getString("uuid")),
                        rs.getString("text"), rs.getString("username"),
                        rs.getTimestamp("created_at").toInstant()));
    }
}