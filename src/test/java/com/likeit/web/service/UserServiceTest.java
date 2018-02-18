package com.likeit.web.service;

import com.likeit.web.dao.UserDAO;
import com.likeit.web.dao.exception.DAOException;
import com.likeit.web.domain.Gender;
import com.likeit.web.domain.Role;
import com.likeit.web.domain.User;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.user.*;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

public class UserServiceTest {

    private UserService userService = ServiceFactory.getInstance().getUserService();
    private String validLogin = "login";
    private String validEmail = "test@gmail.com";
    private String validPassword = "1Password";
    private Gender validGender = Gender.MALE;

    @Test(expected = LoginValidationException.class)
    public void registrationLoginTest() throws ServiceException {
        String shortLogin = "log";
        String invalidLogin = "lo)*4gin";
        String longLogin = "looooooooooooooooooooooooooooooooooooooooogin";
        String withoutLatin = "123155443";
        userService.registration(shortLogin, validEmail, validPassword, validPassword, validGender);
        userService.registration(invalidLogin, validEmail, validPassword, validPassword, validGender);
        userService.registration(longLogin, validEmail, validPassword, validPassword, validGender);
        userService.registration(withoutLatin, validEmail, validPassword, validPassword, validGender);
    }

    @Test(expected = PasswordValidationException.class)
    public void registrationPasswordTest() throws ServiceException {
        String shortPassword = "1Pa";
        String withoutBigLetter = "1password";
        String withoutNumber = "Password";
        String withoutSmallLetter = "1PASSWORD";
        userService.registration(validLogin, validEmail, shortPassword, shortPassword, validGender);
        userService.registration(validLogin, validEmail, withoutBigLetter, withoutBigLetter, validGender);
        userService.registration(validLogin, validEmail, withoutNumber, withoutNumber, validGender);
        userService.registration(validLogin, validEmail, withoutSmallLetter, withoutSmallLetter, validGender);
    }

    @Test(expected = PasswordMatchException.class)
    public void registrationRepeatedPasswordTest() throws ServiceException {
        String anotherPassword = "11Password";
        userService.registration(validLogin, validEmail, validPassword, anotherPassword, validGender);
    }

    @Test(expected = EmailValidationException.class)
    public void registrationEmailTest() throws ServiceException {
        String firstInvalidEmail = "egmail.com";
        String secondInvalidEmail = "e@gmail";
        String thirdInvalidEmail = "egmail";
        userService.registration(validLogin, firstInvalidEmail, validPassword, validPassword, validGender);
        userService.registration(validLogin, secondInvalidEmail, validPassword, validPassword, validGender);
        userService.registration(validLogin, thirdInvalidEmail, validPassword, validPassword, validGender);
    }

    @Test(expected = LoginExistingException.class)
    public void loginExistingTest() throws ServiceException, DAOException {
        String searchLogin = "login";
        User user = new User();
        user.setLogin(searchLogin);
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUserByLogin(anyString())).thenReturn(user);
        userService.registration(searchLogin, validEmail, validPassword, validPassword, validGender);
    }

    @Test(expected = EmailExistingException.class)
    public void emailExistingTest() throws ServiceException, DAOException {
        String searchEmail = "email@gmail.com";
        User user = new User();
        user.setEmail(searchEmail);
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUserByLogin(anyString())).thenReturn(null);
        Mockito.when(userDAO.readUserByEmail(anyString())).thenReturn(user);
        userService.registration(validLogin, searchEmail, validPassword, validPassword, validGender);
    }

    @Test
    public void registrationTest() throws ServiceException, DAOException {
        User user = new User();
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUserByLogin(anyString())).thenReturn(null);
        Mockito.when(userDAO.readUserByEmail(anyString())).thenReturn(null);
        Mockito.when(userDAO.readUser(anyString(), anyString())).thenReturn(user);
        User result = userService.registration(validLogin, validEmail, validPassword, validPassword, validGender);
        assert (user == result);
    }

    @Test(expected = LoginValidationException.class)
    public void authorizationLoginTest() throws ServiceException {
        String shortLogin = "log";
        String invalidLogin = "lo)*4gin";
        String longLogin = "looooooooooooooooooooooooooooooooooooooooogin";
        String withoutLatin = "123155443";
        userService.authorization(shortLogin, validPassword);
        userService.authorization(invalidLogin, validPassword);
        userService.authorization(longLogin, validPassword);
        userService.authorization(withoutLatin, validPassword);
    }

    @Test(expected = PasswordValidationException.class)
    public void authorizationPasswordTest() throws ServiceException {
        String shortPassword = "1Pa";
        String withoutBigLetter = "1password";
        String withoutNumber = "Password";
        String withoutSmallLetter = "1PASSWORD";
        userService.authorization(validLogin, shortPassword);
        userService.authorization(validLogin, withoutBigLetter);
        userService.authorization(validLogin, withoutNumber);
        userService.authorization(validLogin, withoutSmallLetter);
    }


    @Test(expected = BannedException.class)
    public void authorizationBanUserTest() throws ServiceException, DAOException {
        User user = new User();
        user.setBanned(true);
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUser(anyString(), anyString())).thenReturn(user);
        userService.authorization(validLogin, validPassword);
    }

    @Test(expected = NoSuchUserException.class)
    public void authorizationNonexistentUserTest() throws ServiceException, DAOException {
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUser(anyString(), anyString())).thenReturn(null);
        userService.authorization(validLogin, validPassword);
    }

    @Test
    public void authorizationTest() throws ServiceException, DAOException {
        User user = new User();
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUser(anyString(), anyString())).thenReturn(user);
        User result = userService.authorization(validLogin, validPassword);
        assert (user == result);
    }

    @Test
    public void findUserTest() throws ServiceException, DAOException {
        User user = new User();
        int id = 1234;
        user.setId(1234);
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUser(anyInt())).thenReturn(user);
        User result = userService.findUser(id);
        assert (user == result);
    }

    @Test(expected = NoSuchUserException.class)
    public void findNonexistentUserTest() throws ServiceException, DAOException {
        int id = 1234;
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUser(anyInt())).thenReturn(null);
        userService.findUser(id);
    }

    @Test(expected = NoSuchUserException.class)
    public void editNonexistentUserTest() throws ServiceException, DAOException {
        int id = 1234;
        User target = new User();
        target.setId(id);
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUser(anyInt())).thenReturn(null);
        userService.editUser(id, target);
    }

    @Test(expected = BannedException.class)
    public void editBannedUserTest() throws ServiceException, DAOException {
        int id = 1234;
        User target = new User();
        target.setBanned(true);
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUser(anyInt())).thenReturn(target);
        userService.editUser(id, target);
    }

    @Test(expected = ProfileOwnerException.class)
    public void editUserByNotOwnerTest() throws ServiceException, DAOException {
        int id = 1234;
        User user = new User();
        user.setId(id);
        int profileId = 4321;
        User target = new User();
        target.setId(profileId);
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUser(id)).thenReturn(user);
        Mockito.when(userDAO.readUser(profileId)).thenReturn(target);
        userService.editUser(id, target);
    }

    @Test(expected = LoginValidationException.class)
    public void authorizationAdminLoginTest() throws ServiceException {
        String shortLogin = "log";
        String invalidLogin = "lo)*4gin";
        String longLogin = "looooooooooooooooooooooooooooooooooooooooogin";
        String withoutLatin = "123155443";
        userService.adminAuthorization(shortLogin, validPassword);
        userService.adminAuthorization(invalidLogin, validPassword);
        userService.adminAuthorization(longLogin, validPassword);
        userService.adminAuthorization(withoutLatin, validPassword);
    }

    @Test(expected = PasswordValidationException.class)
    public void authorizationAdminPasswordTest() throws ServiceException {
        String shortPassword = "1Pa";
        String withoutBigLetter = "1password";
        String withoutNumber = "Password";
        String withoutSmallLetter = "1PASSWORD";
        userService.adminAuthorization(validLogin, shortPassword);
        userService.adminAuthorization(validLogin, withoutBigLetter);
        userService.adminAuthorization(validLogin, withoutNumber);
        userService.adminAuthorization(validLogin, withoutSmallLetter);
    }


    @Test(expected = UserNotAdminException.class)
    public void authorizationNotAdminUserTest() throws ServiceException, DAOException {
        User user = new User();
        user.setRole(Role.USER);
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUser(anyString(), anyString())).thenReturn(user);
        userService.adminAuthorization(validLogin, validPassword);
    }

    @Test(expected = NoSuchUserException.class)
    public void authorizationAdminNonexistentUserTest() throws ServiceException, DAOException {
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUser(anyString(), anyString())).thenReturn(null);
        userService.adminAuthorization(validLogin, validPassword);
    }

    @Test
    public void authorizationAdminTest() throws ServiceException, DAOException {
        User user = new User();
        user.setRole(Role.ADMIN);
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUser(anyString(), anyString())).thenReturn(user);
        User result = userService.authorization(validLogin, validPassword);
        assert (user == result);
    }

    @Test(expected = NoSuchUserException.class)
    public void editAdminNonexistentUserTest() throws ServiceException, DAOException {
        int id = 1234;
        User target = new User();
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUser(anyInt())).thenReturn(null);
        userService.adminEditUser(id, target);
    }

    @Test(expected = UserNotAdminException.class)
    public void editUserByNotAdminTest() throws ServiceException, DAOException {
        int id = 1234;
        User notAdmin = new User();
        notAdmin.setId(id);
        notAdmin.setRole(Role.USER);
        int targetId = 4321;
        User target = new User();
        target.setId(targetId);
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUser(targetId)).thenReturn(target);
        Mockito.when(userDAO.readUser(id)).thenReturn(notAdmin);
        userService.adminEditUser(id, target);
    }

    @Test(expected = NoSuchUserException.class)
    public void banNonexistentUserTest() throws ServiceException, DAOException {
        int id = 1234;
        int targetId = 4321;
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUser(anyInt())).thenReturn(null);
        userService.banUser(id, targetId, true);
    }

    @Test(expected = UserNotAdminException.class)
    public void banUserByNotAdminTest() throws ServiceException, DAOException {
        int id = 1234;
        User notAdmin = new User();
        notAdmin.setId(id);
        notAdmin.setRole(Role.USER);
        int targetId = 4321;
        User target = new User();
        target.setId(targetId);
        UserDAO userDAO = Mockito.mock(UserDAO.class);
        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Mockito.when(userDAO.readUser(targetId)).thenReturn(target);
        Mockito.when(userDAO.readUser(id)).thenReturn(notAdmin);
        userService.banUser(id, targetId, true);
    }

}
