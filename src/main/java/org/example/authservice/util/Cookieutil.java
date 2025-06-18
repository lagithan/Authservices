package org.example.authservice.util;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Cookieutil {

    // Set secure HTTP-only cookie
    public void setSecureCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);          // Prevents JavaScript access (XSS protection)
        cookie.setSecure(true);            // HTTPS only
        cookie.setPath("/");               // Available for entire application
        cookie.setMaxAge(maxAgeInSeconds); // Expiration time in seconds
        response.addCookie(cookie);
    }

    // Set authentication cookies (access + refresh token)
    public void setAuthenticationCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        setSecureCookie(response, "access_token", accessToken, 60 * 60); // 1 hour
        setSecureCookie(response, "refresh_token", refreshToken, 90 * 24 * 60 * 60); // 90 days
    }

    // Get cookie value by name
    public String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // Clear authentication cookies (for logout)
    public void clearAuthenticationCookies(HttpServletResponse response) {
        clearCookie(response, "access_token");
        clearCookie(response, "refresh_token");
    }

    // Clear a specific cookie
    public void clearCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setPath("/");
        cookie.setMaxAge(0); // This will delete the cookie
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}