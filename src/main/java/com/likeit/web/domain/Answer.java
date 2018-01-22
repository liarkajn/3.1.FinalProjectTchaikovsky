package com.likeit.web.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Answer implements Serializable {

    private int id;
    private User author;
    private Question question;
    private String content;
    private LocalDateTime publishDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public int hashCode() {
        return 107 * id + author.hashCode() + question.hashCode() +
                content.hashCode() + publishDate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Answer answer = (Answer) obj;
        if (!super.equals(obj)) {
            return false;
        }
        return id == answer.id && author.equals(answer.author)
                && question.equals(answer.question) && content.equals(answer.content)
                && publishDate.equals(answer.publishDate);
    }

    @Override
    public String toString() {
        return "id : " + id +
                "\nauthor : " + author +
                "\nquestion : " + question +
                "\ncontent : " + content +
                "\npublish date : " + publishDate;
    }

}
