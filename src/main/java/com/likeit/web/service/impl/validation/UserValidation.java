package com.likeit.web.service.impl.validation;

import com.likeit.web.domain.Role;
import com.likeit.web.domain.User;
import com.likeit.web.service.exception.ServiceException;
import com.likeit.web.service.exception.user.BannedException;
import com.likeit.web.service.exception.user.NoSuchUserException;
import com.likeit.web.service.exception.user.ProfileOwnerException;

public class UserValidation {

    private final Role administrator = Role.ADMIN;
    private final Role moderator = Role.MODERATOR;

    public void validate(User user) throws ServiceException {
        if (user == null) {
            throw new NoSuchUserException("No such user");
        }
    }

    public void ownerValidation(User user, User target) throws ServiceException {
        if (user.getId() != target.getId()) {
            throw new ProfileOwnerException("User with id + " + user.getId() + " trying update user with id: " + target.getId());
        }
    }

    public boolean isActive(User user) throws BannedException {
        if (user.isBanned()) {
            throw new BannedException("User have been banned");
        }
        return true;
    }

    public boolean isAdmin(User user) {
        return user.getRole().equals(administrator) || user.getRole().equals(moderator);
    }

}
