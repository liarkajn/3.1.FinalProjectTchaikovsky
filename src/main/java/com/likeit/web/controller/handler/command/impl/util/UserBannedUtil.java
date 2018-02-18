package com.likeit.web.controller.handler.command.impl.util;

import com.likeit.web.controller.handler.command.impl.exception.user.BannedException;
import com.likeit.web.domain.User;

public class UserBannedUtil {

    public static boolean isBanned(User user) throws BannedException {
        if (user.isBanned()) {
            throw new BannedException("User have been banned");
        }
        return false;
    }

}
