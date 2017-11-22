package main.java.com.likeit.web.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Answer implements Serializable {

    private User author;
    private String content;
    private LocalDateTime publishDate;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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

    private boolean compareFields(Answer answer) {
        if (!author.equals(answer.author) || !content.equals(answer.content)
                || !publishDate.equals(answer.publishDate)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 107 * author.hashCode() + content.hashCode() +
                + publishDate.hashCode();
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
        if (!super.equals(answer)) {
            return false;
        }
        return compareFields(answer);
    }

    @Override
    public String toString() {
        return "author : " + author +
                "\ncontent : " + content +
                "\npublishDate : " + publishDate;
    }

}
