package com.example.Mini_Project1.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Mini_Project1.entity.Article;
import com.example.Mini_Project1.entity.User;
import com.example.Mini_Project1.exception.ExceptionCode;
import com.example.Mini_Project1.exception.UserException;
import com.example.Mini_Project1.mapper.ArticleMapper;
import com.example.Mini_Project1.mapper.UserMapper;
import com.example.Mini_Project1.repository.ArticleRepository;
import com.example.Mini_Project1.repository.UserRepository;
import com.example.Mini_Project1.request.CreateArticleRequest;
import com.example.Mini_Project1.request.UpdateArticleRequest;
import com.example.Mini_Project1.response.ArticleResponse;
import com.example.Mini_Project1.response.ResponseCode;
import com.example.Mini_Project1.response.UserResponse;
import com.example.Mini_Project1.utils.JwtTokenUtils;

import jakarta.servlet.http.HttpServletRequest;
@Service
public class ArticleService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@Autowired
	private ArticleMapper articleMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Value("${page.limit}")
    private int limit;

	public ResponseEntity<?> createArticle(CreateArticleRequest createArticleRequest,
			HttpServletRequest httpServletRequest) {
// get token
		String token = JwtTokenUtils.getBearerTokenFromRequest(httpServletRequest);
// create user id
		String userId = jwtTokenUtils.verifyToken(token);
// get user
		Optional<User> optional = userRepository.findById(userId);
		if (optional.isEmpty()) {
			throw new UserException(ExceptionCode.IdDoesNotExist);
		} else {
			// convert
			Article article = articleMapper.createRequestToArticle(createArticleRequest);
			// set user and date
			article.setCreatedDate(new Date());
			if (optional.get().getRole().equals("user") == true) {
				article.setUser(optional.get());
			} else {
				article.setStatusApproved(1);
			}
			// save
			this.articleRepository.save(article);
			// return
			return ResponseEntity.ok().body(ResponseCode.CreateArticle.responseCodeToJson());
		}
	}

	public ResponseEntity getUnapprovedList() {
// get list article
		List<Article> UnapprovedList = this.articleRepository.getUnapprovedList();
// iterate
		List<ArticleResponse> list = new ArrayList<ArticleResponse>();
		for (int i = 0; i < UnapprovedList.size(); i++) {
			if (UnapprovedList.get(i).getFlagDelete() == 1) {
				continue;
			}
			// convert
			ArticleResponse articleResponse = this.articleMapper.articleToArticleResponse(UnapprovedList.get(i));
			articleResponse.setCreated(UnapprovedList.get(i).getCreatedDate().toGMTString());
			if (UnapprovedList.get(i).getUpdatedDate() != null) {
				articleResponse.setUpdated(UnapprovedList.get(i).getUpdatedDate().toGMTString());
			}
			// set author
			Optional<User> optional = userRepository.findById(UnapprovedList.get(i).getUser().getId());
			UserResponse userResponse;
			if (optional.isEmpty() == false) {
				userResponse = userMapper.UserToResponseUser(optional.get());
			} else {
				userResponse = UserResponse.builder().name("Admin").build();
			}
			articleResponse.setAuthor(userResponse);
			// add
			list.add(articleResponse);
		}
// return
		return ResponseEntity.ok().body(list);
	}

	public ResponseEntity approve(String articleId) {
// check article
		Optional<Article> optional = this.articleRepository.findById(articleId);
		if (optional.isEmpty()) {
			throw new UserException(ExceptionCode.ArticleId);
		} else {
			// set
			optional.get().setStatusApproved(1);
			// update
			articleRepository.save(optional.get());
			// return
			return ResponseEntity.ok().body(ResponseCode.Approve.responseCodeToJson());
		}
	}

	public ResponseEntity get(int page) {
// calculate skip
		int skip = page * limit;
// create pageble
		Pageable pageable = PageRequest.of(page, limit);
// get list
		List<Article> list = this.articleRepository.getArticleFollowingPage(pageable);
// iterate
		List<ArticleResponse> list1 = new ArrayList<ArticleResponse>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getFlagDelete() == 1) {
				continue;
			}
			// convert
			ArticleResponse articleResponse = this.articleMapper.articleToArticleResponse(list.get(i));
			articleResponse.setCreated(list.get(i).getCreatedDate().toGMTString());
			if (list.get(i).getUpdatedDate() != null) {
				articleResponse.setUpdated(list.get(i).getUpdatedDate().toGMTString());
			}
			// set author
			if (list.get(i).getUser() == null) {
				articleResponse.setAuthor(UserResponse.builder().name("Admin").email("admin@gmail.com").build());
				list1.add(articleResponse);
				continue;
			}
			Optional<User> optional = userRepository.findById(list.get(i).getUser().getId());
			UserResponse userResponse;
			if (optional.isEmpty() == false) {
				userResponse = userMapper.UserToResponseUser(optional.get());
			} else {
				userResponse = UserResponse.builder().email("admin@gmail.com").name("Admin").build();
			}
			articleResponse.setAuthor(userResponse);
			// add
			list1.add(articleResponse);
		}
// return
		return ResponseEntity.ok().body(list1);
	}

	public ResponseEntity delete(String articleId) {
// delete
		Article article = articleRepository.findById(articleId).get();
		article.setFlagDelete(1);
		this.articleRepository.save(article);
// return 
		return ResponseEntity.ok().body(ResponseCode.DeletePost.responseCodeToJson());
	}

	public ResponseEntity editArticle(UpdateArticleRequest updateArticleRequest) {
// check article
		Optional<Article> optional = this.articleRepository.findById(updateArticleRequest.getArticleId());
		if (optional.isEmpty()) {
			throw new UserException(ExceptionCode.ArticleId);
		} else {
			// convert
			Article article = articleRepository.getArticle(updateArticleRequest.getArticleId());
			if (article.getFlagDelete() == 1) {
				throw new UserException(ExceptionCode.ArticleId);
			}
			article.setTitle(updateArticleRequest.getTitle());
			article.setContent(updateArticleRequest.getContent());
			article.setCategorize(updateArticleRequest.getCategorize());
			article.setReferenceLink(updateArticleRequest.getReferenceLink());
			article.setUpdatedDate(new Date());
			if (article.getUser() != null) {
				article.setStatusApproved(0);
			}
			// save
			this.articleRepository.save(article);
			// return
			return ResponseEntity.ok().body(ResponseCode.EditArticle.responseCodeToJson());
		}
	}
	
	public boolean checkUserId(HttpServletRequest httpServletRequest, String articleId) {
		// get token 
		String token = JwtTokenUtils.getBearerTokenFromRequest(httpServletRequest);
		// get userId
		String userId = jwtTokenUtils.verifyToken(token);
		// check article
		Article article = this.articleRepository.getArticle(articleId);
		if (article == null) {
			throw new UserException(ExceptionCode.ArticleId);
		}
		else {
			if (article.getUser() == null && userRepository.findById(userId).get().getRole().equals("admin")) {
				return true;
			}
			return article.getUser().getId().equals(userId);
		}
	}
}
