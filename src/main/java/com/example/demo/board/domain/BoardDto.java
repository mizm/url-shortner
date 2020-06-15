package com.example.demo.board.domain;

import java.util.List;

public class BoardDto {
    // 함수에 get이 있어야 불러옴
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private int id;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    private String title;

    private List<Reply> replies;

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

//    private int replyCount() {
//
//    }

    public int getCount() {
        return replies == null ? 0 : replies.size();
    }
}
