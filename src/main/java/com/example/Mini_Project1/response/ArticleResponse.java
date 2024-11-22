package com.example.Mini_Project1.response;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {
	private String id;
	private String title;
	private String content;
	private String categorize;
	private List<String> referenceLink;
	private String created;
	private String updated;
	private UserResponse author;
}
