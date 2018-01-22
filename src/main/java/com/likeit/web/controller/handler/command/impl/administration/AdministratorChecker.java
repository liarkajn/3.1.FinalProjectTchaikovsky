package com.likeit.web.controller.handler.command.impl.administration;

import javax.servlet.http.HttpServletRequest;

public class AdministratorChecker {

    private final static String ADMIN_ID_ATTRIBUTE = "id";
    private final static String ROLE_ATTRIBUTE_NAME = "role";
    private final static String ADMIN_ROLE_ATTRIBUTE_VALUE = "admin";

    public static boolean isAdmin(HttpServletRequest request) {
        return request.getSession().getAttribute(ADMIN_ID_ATTRIBUTE) != null &&
                ADMIN_ROLE_ATTRIBUTE_VALUE.equals(request.getSession().getAttribute(ROLE_ATTRIBUTE_NAME));
    }

}
