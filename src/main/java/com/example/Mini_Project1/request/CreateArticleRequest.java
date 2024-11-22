package com.example.Mini_Project1.request;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.example.Mini_Project1.deserializer.LocalDateDeserializer;
import com.example.Mini_Project1.validator.CategorizeConstraint;
import com.example.Mini_Project1.validator.LinkConstraint;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleRequest {
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
