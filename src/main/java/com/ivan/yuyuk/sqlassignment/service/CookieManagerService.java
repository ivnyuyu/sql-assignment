package com.ivan.yuyuk.sqlassignment.service;

import com.google.gson.Gson;
import com.ivan.yuyuk.sqlassignment.dto.AnswerFromUserForm;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Service
public class CookieManagerService {

    private static final Gson GSON_PARSER = new Gson();
    private static final String PATTERN_NAME_COOKIE = "TASK_PROGRESS#";

    public static String convertToJson(AnswerFromUserForm answer) {
        return GSON_PARSER.toJson(answer);
    }

    public AnswerFromUserForm getUserQueryFromCookie(Long id, Cookie[] cookies) {
        try {
            for (Cookie temp : cookies) {
                if (temp.getName().equals(PATTERN_NAME_COOKIE.concat(id.toString()))) {
                    return GSON_PARSER.fromJson(URLDecoder.decode(temp.getValue(), "UTF-8"), AnswerFromUserForm.class);
                }
            }
        } catch (Exception e) {
            new AnswerFromUserForm();
        }
        return new AnswerFromUserForm();
    }

    public void addUserQueryToCookie(AnswerFromUserForm answer, HttpServletResponse response) {
        Cookie cookie = null;
        try {
            cookie = new Cookie(PATTERN_NAME_COOKIE.concat(answer.getId().toString()), URLEncoder.encode(CookieManagerService.convertToJson(answer), "UTF-8"));
        } catch (UnsupportedEncodingException ignored) {
        }
        response.addCookie(cookie);
    }
}
