package ai.remi.comm.web.auth;

import ai.remi.comm.util.auth.AuthInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Startdis
 * @email startdis@dianjiu.cc
 * @desc AuthHandler
 */
public interface AuthHandler {
    AuthInfo getAuthInfo(HttpServletRequest request, HttpServletResponse response);
}
