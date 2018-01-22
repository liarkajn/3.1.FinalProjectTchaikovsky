package com.likeit.web.domain;

import java.io.Serializable;

public class Vote implements Serializable {

    private int id;
    private User author;
    private Answer answer;
    private int mark;

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

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public int hashCode() {
        return 107 * id + author.hashCode() + answer.hashCode() + mark;
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
        Vote vote = (Vote) obj;
        if (!super.equals(vote)) {
            return false;
        }
        return id == vote.id && author.equals(vote.author) &&
                answer.equals(vote.answer) && mark == vote.mark;
    }

    @Override
    public String toString() {
        return "vote id : " + id +
                "\nauthor : " + author.toString() +
                "\nanswer : " + answer.toString() +
                "\nmark : " + mark;
    }

}
