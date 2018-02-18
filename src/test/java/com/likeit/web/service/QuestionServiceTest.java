package com.likeit.web.service;

import com.likeit.web.dao.QuestionDAO;
import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Question;
import com.likeit.web.domain.User;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.question.AuthorException;
import com.likeit.web.service.exception.question.ContentValidationException;
import com.likeit.web.service.exception.question.NoSuchQuestionException;
import com.likeit.web.service.exception.question.TopicValidationException;
import com.likeit.web.service.exception.user.BannedException;
import com.likeit.web.service.exception.user.NoSuchUserException;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import static org.mockito.Matchers.anyInt;


public class QuestionServiceTest {

    private QuestionService questionService = ServiceFactory.getInstance().getQuestionService();

    private String validTopic;
    private String validContent;
    private int topicLength = 50;
    private int contentLength = 100;

    public QuestionServiceTest() {
        validTopic = createString(topicLength);
        validContent = createString(contentLength);
    }

    private String createString(int length) {
        String result = "";
        for (int i = 0; i <= length; i++) {
            result += "a";
        }
        return result;
    }

    @Test(expected = TopicValidationException.class)
    public void creationTopicValidationTest() throws ServiceException {

        UserService userService = ServiceFactory.getInstance().getUserService();
        UserService mockUserService = Mockito.mock(UserService.class);
        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", mockUserService);

        int authorId = 1234;
        User author = new User();
        author.setId(authorId);
        author.setBanned(false);

        Mockito.when(mockUserService.findUser(anyInt())).thenReturn(author);
        String shortTopic = "short Topic";
        questionService.createQuestion(shortTopic, validContent, authorId);

        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", userService);
    }

    @Test(expected = ContentValidationException.class)
    public void creationContentValidationTest() throws ServiceException {

        UserService userService = ServiceFactory.getInstance().getUserService();
        UserService mockUserService = Mockito.mock(UserService.class);
        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", mockUserService);

        int authorId = 1234;
        User author = new User();
        author.setId(authorId);
        author.setBanned(false);

        Mockito.when(mockUserService.findUser(anyInt())).thenReturn(author);
        String shortContent = "short content";
        questionService.createQuestion(validTopic, shortContent, authorId);

        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", userService);
    }

    @Test(expected = BannedException.class)
    public void creationByBannedUserTest() throws ServiceException {

        UserService userService = ServiceFactory.getInstance().getUserService();
        UserService mockUserService = Mockito.mock(UserService.class);
        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", mockUserService);

        int authorId = 1234;
        User author = new User();
        author.setId(authorId);
        author.setBanned(true);

        Mockito.when(mockUserService.findUser(anyInt())).thenReturn(author);
        questionService.createQuestion(validTopic, validContent, authorId);

        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", userService);
    }

    @Test(expected = NoSuchUserException.class)
    public void creationByNonexistentUserTest() throws ServiceException {

        UserService userService = ServiceFactory.getInstance().getUserService();
        UserService mockUserService = Mockito.mock(UserService.class);
        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", mockUserService);

        int authorId = 1234;

        Mockito.when(mockUserService.findUser(anyInt())).thenReturn(null);
        questionService.createQuestion(validTopic, validContent, authorId);

        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", userService);
    }

    @Test(expected = NoSuchQuestionException.class)
    public void findQuestionTest() throws ServiceException {

        QuestionDAO questionDAO = Mockito.mock(QuestionDAO.class);
        Whitebox.setInternalState(questionService, "questionDAO", questionDAO);

        int authorId = 1234;

        Mockito.when(questionService.findQuestion(anyInt())).thenReturn(null);
        questionService.createQuestion(validTopic, validContent, authorId);

    }

    @Test(expected = TopicValidationException.class)
    public void editTopicValidationTest() throws ServiceException, DAOException {

        UserService userService = ServiceFactory.getInstance().getUserService();
        AnswerService answerService = ServiceFactory.getInstance().getAnswerService();
        UserService mockUserService = Mockito.mock(UserService.class);
        AnswerService mockAnswerService = Mockito.mock(AnswerService.class);
        QuestionDAO questionDAO = Mockito.mock(QuestionDAO.class);
        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", mockUserService);
        Whitebox.setInternalState(ServiceFactory.getInstance(), "answerService", mockAnswerService);
        Whitebox.setInternalState(questionService, "questionDAO", questionDAO);

        int questionId = 1234;
        int authorId = 1234;
        User author = new User();
        author.setId(authorId);
        author.setBanned(false);

        Question question = new Question();
        question.setAuthor(author);

        Mockito.when(mockUserService.findUser(anyInt())).thenReturn(author);
        Mockito.when(questionDAO.readQuestion(anyInt())).thenReturn(question);
        String shortTopic = "short Topic";
        questionService.editQuestion(authorId, questionId, shortTopic, validContent);

        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", userService);
        Whitebox.setInternalState(ServiceFactory.getInstance(), "answerService", answerService);
    }

    @Test(expected = ContentValidationException.class)
    public void editContentValidationTest() throws ServiceException, DAOException {

        UserService userService = ServiceFactory.getInstance().getUserService();
        AnswerService answerService = ServiceFactory.getInstance().getAnswerService();
        UserService mockUserService = Mockito.mock(UserService.class);
        AnswerService mockAnswerService = Mockito.mock(AnswerService.class);
        QuestionDAO questionDAO = Mockito.mock(QuestionDAO.class);
        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", mockUserService);
        Whitebox.setInternalState(ServiceFactory.getInstance(), "answerService", mockAnswerService);
        Whitebox.setInternalState(questionService, "questionDAO", questionDAO);

        int questionId = 1234;
        int authorId = 1234;
        User author = new User();
        author.setId(authorId);
        author.setBanned(false);

        Question question = new Question();
        question.setAuthor(author);

        Mockito.when(mockUserService.findUser(anyInt())).thenReturn(author);
        Mockito.when(questionDAO.readQuestion(anyInt())).thenReturn(question);
        String shortContent = "short content";
        questionService.editQuestion(authorId, questionId, validTopic, shortContent);

        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", userService);
        Whitebox.setInternalState(ServiceFactory.getInstance(), "answerService", answerService);
    }

    @Test(expected = BannedException.class)
    public void editByBannedUserTest() throws ServiceException {

        UserService userService = ServiceFactory.getInstance().getUserService();
        UserService mockUserService = Mockito.mock(UserService.class);
        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", mockUserService);

        int authorId = 1234;
        int questionId = 1234;
        User author = new User();
        author.setId(authorId);
        author.setBanned(true);

        Mockito.when(mockUserService.findUser(anyInt())).thenReturn(author);
        questionService.editQuestion(authorId, questionId, validTopic, validContent);

        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", userService);
    }

    @Test(expected = NoSuchUserException.class)
    public void editByNonexistentUserTest() throws ServiceException {

        UserService userService = ServiceFactory.getInstance().getUserService();
        UserService mockUserService = Mockito.mock(UserService.class);
        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", mockUserService);

        int authorId = 1234;
        int questionId = 1234;

        Mockito.when(mockUserService.findUser(anyInt())).thenReturn(null);
        questionService.editQuestion(authorId, questionId, validTopic, validContent);

        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", userService);
    }

    @Test(expected = AuthorException.class)
    public void editQuestionByNotAuthorTest() throws ServiceException, DAOException {

        UserService userService = ServiceFactory.getInstance().getUserService();
        AnswerService answerService = ServiceFactory.getInstance().getAnswerService();
        UserService mockUserService = Mockito.mock(UserService.class);
        AnswerService mockAnswerService = Mockito.mock(AnswerService.class);
        QuestionDAO questionDAO = Mockito.mock(QuestionDAO.class);
        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", mockUserService);
        Whitebox.setInternalState(ServiceFactory.getInstance(), "answerService", mockAnswerService);
        Whitebox.setInternalState(questionService, "questionDAO", questionDAO);

        int questionId = 1234;
        int authorId = 1234;
        User author = new User();
        author.setId(authorId);
        author.setBanned(false);

        Question question = new Question();
        question.setAuthor(author);

        int userId = 4321;
        User user = new User();
        user.setId(userId);
        user.setBanned(false);

        Mockito.when(mockUserService.findUser(authorId)).thenReturn(author);
        Mockito.when(mockUserService.findUser(userId)).thenReturn(user);
        Mockito.when(questionDAO.readQuestion(anyInt())).thenReturn(question);
        questionService.editQuestion(userId, questionId, validTopic, validContent);

        Whitebox.setInternalState(ServiceFactory.getInstance(), "userService", userService);
        Whitebox.setInternalState(ServiceFactory.getInstance(), "answerService", answerService);
    }

}
