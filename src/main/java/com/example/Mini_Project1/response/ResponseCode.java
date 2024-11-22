package com.example.Mini_Project1.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResponseCode {
    SignUpSuccessfully(1000, "Sign up successfully!"),
    LoginSuccessfully(1000, "Login successfully!"),
    CheckEmail(1000, "Send email successfully! Check your email to get token"),
    UpdatePassword(1000, "Update password successfully!"),
    CreateStaff(1000, "Create staff successfully!"),
    DeleteStaff(1000, "Delete staff successfully!"),
    CreateArticle(1000, "Create article successfully!"),
    Approve(1000, "Approve article successfully!"),
    DeletePost(1000, "Delete article successfully!"),
    EditArticle(1000, "Edit article successfully!");

    private int code;
    private String message;

    public Map<String, Object> responseCodeToJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        return map;
    }
}
