package main.java.com.likeit.web.service.impl;

import main.java.com.likeit.web.dao.DAOFactory;
import main.java.com.likeit.web.dao.QuestionDAO;
import main.java.com.likeit.web.domain.Question;
import main.java.com.likeit.web.service.QuestionService;

import java.util.LinkedList;
import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    @Override
    public void createQuestion(String topic, String content, String authorLogin) {
        DAOFactory daoFactory = DAOFactory.getInstance();
        QuestionDAO questionDAO = daoFactory.getQuestionDAO();
        try {
            questionDAO.saveQuestion(topic, content, authorLogin);
        } catch (Exception e) {

        }
    }

    @Override
    public List<Question> getAll() {
        List<Question> questions = new LinkedList<>();
        DAOFactory daoFactory = DAOFactory.getInstance();
        QuestionDAO questionDAO = daoFactory.getQuestionDAO();
        try {
            questions = questionDAO.getQuestions();
        } catch (Exception e) {

        }
        return questions;
    }
}
