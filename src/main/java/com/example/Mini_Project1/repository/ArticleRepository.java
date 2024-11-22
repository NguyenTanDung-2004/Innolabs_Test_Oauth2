package com.example.Mini_Project1.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Mini_Project1.entity.Article;



public interface ArticleRepository extends JpaRepository<Article, String> {
	@Query("select a from Article a join fetch a.user where a.statusApproved = 0")
	public List<Article> getUnapprovedList();
	
	@Query("select a from Article a left join fetch a.user u where a.statusApproved = 0")
	public List<Article> getApprovedList();
	
	@Query("select a from Article a left join fetch a.user u where a.statusApproved = 1")
	List<Article> getArticleFollowingPage(Pageable pageable);

	
	@Query("select a from Article a left join fetch a.user where a.id = :articleId")
	public Article getArticle(String articleId);
}
