package com.example.Mini_Project1.controller;

import org.hibernate.validator.constraints.UUID; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Mini_Project1.request.CreateArticleRequest;
import com.example.Mini_Project1.request.UpdateArticleRequest;
import com.example.Mini_Project1.service.ArticleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/article")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@PostMapping("/createArticle")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_admin') || hasAuthority('SCOPE_ROLE_user') || hasAuthority('SCOPE_ROLE_staff')")
	public ResponseEntity createArticle(@Valid @RequestBody CreateArticleRequest createArticleRequest,
			HttpServletRequest httpServletRequest) {
		return articleService.createArticle(createArticleRequest, httpServletRequest);
	}

	@GetMapping("/getUnapprovedList")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_admin') || hasAuthority('SCOPE_ROLE_staff')")
	public ResponseEntity getUnapprovedList() {
		return articleService.getUnapprovedList();
	}

	@PutMapping("/approve")
	@PreAuthorize("hasAuthority('SCOPE_ROLE_admin') || hasAuthority('SCOPE_ROLE_staff')")
	public ResponseEntity approve(@NotNull @UUID @RequestParam(name = "articleId") String articleId) {
		return articleService.approve(articleId);
	}

	@GetMapping("/get")
	public ResponseEntity get(@RequestParam @Min(0) int page) {
		return articleService.get(page);
	}

	@DeleteMapping("/delete")
	@PreAuthorize("@articleService.checkUserId(#httpServletRequest, #articleId) || hasAuthority('SCOPE_ROLE_admin')")
	public ResponseEntity delete(@RequestParam @UUID String articleId, HttpServletRequest httpServletRequest) {
		return articleService.delete(articleId);
	}

	@PutMapping("/editArticle")
	@PreAuthorize("@articleService.checkUserId(#httpServletRequest, #updateArticleRequest.articleId)")
	public ResponseEntity editArticle(@Valid @RequestBody UpdateArticleRequest updateArticleRequest,
			HttpServletRequest httpServletRequest) {
		return articleService.editArticle(updateArticleRequest);
	}

}
