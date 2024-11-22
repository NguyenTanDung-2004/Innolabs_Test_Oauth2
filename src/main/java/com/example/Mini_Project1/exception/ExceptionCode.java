package com.example.Mini_Project1.exception;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionCode {
	LocalDateFormat(1001, "The localdate format is wrong", HttpStatus.BAD_REQUEST), 
	EmailAlreadyExists(1002, "This email already exists in the database", HttpStatus.CONFLICT), 
	EmailDoesNotExist(1003, "This email does not exist in the database", HttpStatus.NOT_FOUND), 
	WrongPassword(1004, "Your password is wrong", HttpStatus.UNAUTHORIZED), 
	CreateTokenFail(1005, "Create token fail", HttpStatus.INTERNAL_SERVER_ERROR), 
	VerifyTokenFail(1006, "Verify token fail", HttpStatus.UNAUTHORIZED), 
	SendEmailFail(1007, "Send email fail", HttpStatus.INTERNAL_SERVER_ERROR), 
	WrongToken(1008, "Your token is wrong", HttpStatus.UNAUTHORIZED),
	IdDoesNotExist(1009, "User id does not exist in the database", HttpStatus.NOT_FOUND), 
	NotStaff(1010, "This user is not staff", HttpStatus.FORBIDDEN), 
	Unauthorized(1011, "Unauthorized", HttpStatus.FORBIDDEN), 
	Unauthenticated(1012, "Unauthenticated", HttpStatus.UNAUTHORIZED), 
	MissToken(1013, "Missing token", HttpStatus.BAD_REQUEST), 
	ArticleId(1014, "Article id does not exist", HttpStatus.NOT_FOUND); 

    private int code;
    private String message;
    private HttpStatus status;

    public Map<String, Object> exceptionCodeToJson() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        return map;
    }
}
