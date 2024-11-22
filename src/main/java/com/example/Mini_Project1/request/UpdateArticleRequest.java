package com.example.Mini_Project1.request;

import java.util.List;

import org.hibernate.validator.constraints.UUID;

import com.example.Mini_Project1.validator.CategorizeConstraint;
import com.example.Mini_Project1.validator.LinkConstraint;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateArticleRequest {
	@NotNull
	@UUID
	private String articleId;
	
	@NotNull
	@Size(min = 10, max = 200)
	private String title;
	
	@NotNull
	@CategorizeConstraint
	private String categorize;
	
	@NotNull
	@Size(min = 300)
	private String content;
	
    @Valid
    private List<@LinkConstraint String> referenceLink;
}
