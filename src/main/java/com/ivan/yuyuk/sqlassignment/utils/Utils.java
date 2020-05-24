package com.ivan.yuyuk.sqlassignment.utils;

import com.google.gson.Gson;
import com.ivan.yuyuk.sqlassignment.model.AnswerFromUserForm;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.function.BiFunction;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Utils {
    private static final Gson gson = new Gson();

    public static String convertToJson(AnswerFromUserForm answer) {
        return gson.toJson(answer);
    }

    public static AnswerFromUserForm getAnswerFromJson(String task, Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        for (Cookie temp : cookies) {
            if (temp.getName().equals(task)) {
                try {
                    String a = URLDecoder.decode(temp.getValue(), "UTF-8");
                    return gson.fromJson(a, AnswerFromUserForm.class);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
