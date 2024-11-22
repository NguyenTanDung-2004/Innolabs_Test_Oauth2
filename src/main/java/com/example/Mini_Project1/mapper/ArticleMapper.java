package com.example.Mini_Project1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.Mini_Project1.entity.Article;
import com.example.Mini_Project1.request.CreateArticleRequest;
import com.example.Mini_Project1.request.UpdateArticleRequest;
import com.example.Mini_Project1.response.ArticleResponse;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
	Article createRequestToArticle(CreateArticleRequest createArticleRequest);
	
	@Mapping(target = "created", ignore = true)
	@Mapping(target = "updated", ignore = true)
	ArticleResponse articleToArticleResponse(Article article);
	

}
