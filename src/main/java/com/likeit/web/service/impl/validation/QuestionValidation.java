package com.likeit.web.service.impl.validation;

import com.likeit.web.domain.Question;
import com.likeit.web.domain.User;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.question.AuthorException;
import com.likeit.web.service.exception.question.ContentValidationException;
import com.likeit.web.service.exception.question.NoSuchQuestionException;
import com.likeit.web.service.exception.question.TopicValidationException;

public class QuestionValidation {

    private final static int REQUIRED_TOPIC_LENGTH = 50;
    private final static int REQUIRED_CONTENT_LENGTH = 100;

    public boolean validateQuestion(Question question) throws ServiceException {
        if (question == null) {
            throw new NoSuchQuestionException("No such question");
        }
        return true;
    }

    public boolean validateQuestionCreation(String topic, String content) throws ServiceException {
        validateTopic(topic);
        validateContent(content);
        return true;
    }

    public boolean validateAuthor(Question question, User author) throws ServiceException {
        if (question.getAuthor().getId() != author.getId()) {
            throw new AuthorException("User with id: " + author.getId() + " trying edit not his question with id : " + question.getId());
        }
        return true;
    }

    private boolean validateTopic(String topic) throws ServiceException {
        topic = topic.trim();
        if (topic.isEmpty() || topic.length() < REQUIRED_TOPIC_LENGTH) {
            throw new TopicValidationException("Question's topic is too short");
        }
        return true;
    }

    private boolean validateContent(String content) throws ServiceException {
        content = content.trim();
        if (content.isEmpty() || content.length() < REQUIRED_CONTENT_LENGTH) {
            throw new ContentValidationException("Question content is too short");
        }
        return true;
    }

}
