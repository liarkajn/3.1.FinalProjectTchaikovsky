package main.java.com.likeit.web.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Question implements Serializable {

    private int id;
    private User author;
    private String topic;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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
        return 107 * author.hashCode() + topic.hashCode() +
                +content.hashCode() + publishDate.hashCode();
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
        Question question = (Question) obj;
        if (!super.equals(question)) {
            return false;
        }
        return author.equals(question.author) && topic.equals(question.topic)
                && content.equals(question.content) && publishDate.equals(question.publishDate);
    }

    @Override
    public String toString() {
        return "author : " + author +
                "\ntopic : " + topic +
                "\ncontent : " + content +
                "\npublishDate : " + publishDate;
    }

}
