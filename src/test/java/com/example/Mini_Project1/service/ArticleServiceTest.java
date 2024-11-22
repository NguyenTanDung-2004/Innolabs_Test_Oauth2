//package com.example.Mini_Project1.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.Mockito;
//import org.springframework.http.ResponseEntity;
//
//import com.example.Mini_Project1.entity.Article;
//import com.example.Mini_Project1.exception.UserException;
//import com.example.Mini_Project1.repository.ArticleRepository;
//import com.example.Mini_Project1.request.UpdateArticleRequest;
//import com.example.Mini_Project1.response.ResponseCode;
//
//import org.junit.jupiter.api.Assertions;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@ExtendWith(MockitoExtension.class)
//public class ArticleServiceTest {
//
//    @InjectMocks
//    private ArticleService articleService;
//
//    @Mock
//    private ArticleRepository  articleRepository;
//
//    @Test
//    void testEditArticle_ArticleNotFound() {
//        // Arrange
//        UpdateArticleRequest request = new UpdateArticleRequest();
//        UUID articleId1 = UUID.randomUUID(); // Create a UUID for the article ID
//        String articleId = articleId1.toString();
//        request.setArticleId(articleId);
//
//        Mockito.when(articleRepository.findById(articleId))
//                .thenReturn(Optional.empty());
//
//        // Act & Assert
//        Assertions.assertThrows(UserException.class, () -> {
//            articleService.editArticle(request);
//        });
//
//        Mockito.verify(articleRepository, Mockito.times(1)).findById(articleId);
//    }
//
//    @Test
//    void testEditArticle_ArticleDeleted() {
//        // Arrange
//        Article deletedArticle = new Article();
//        deletedArticle.setFlagDelete(1);
//
//        UUID articleId = UUID.randomUUID(); // Create a UUID for the article ID
//        UpdateArticleRequest request = new UpdateArticleRequest();
//        request.setArticleId(articleId.toString());
//
//        Mockito.when(articleRepository.findById(articleId.toString()))
//                .thenReturn(Optional.of(deletedArticle));
//
//        // Act & Assert
//        Assertions.assertThrows(UserException.class, () -> {
//            articleService.editArticle(request);
//        });
//
//        Mockito.verify(articleRepository, Mockito.times(1)).findById(articleId.toString());
//    }
//
//    @Test
//    void testEditArticle_Success() {
//        // Arrange
//        UUID articleId = UUID.randomUUID(); // Tạo một UUID cho article ID
//
//        // Tạo mock Article
//        Article article = new Article();
//        article.setId(articleId.toString());
//        article.setFlagDelete(0); // Đảm bảo bài viết chưa bị xóa
//        article.setTitle("Old Title"); // Tiêu đề ban đầu
//        article.setContent("Old Content");
//        article.setCategorize("Old Categorize");
//        article.setReferenceLink(List.of("https://old-link.com"));
//
//        // Tạo request để chỉnh sửa bài viết
//        UpdateArticleRequest request = new UpdateArticleRequest();
//        request.setArticleId(articleId.toString());
//        request.setTitle("New Title"); // Dữ liệu mới
//        request.setContent("New Content");
//        request.setCategorize("New Categorize");
//        request.setReferenceLink(List.of("https://example.com"));
//
//        // Giả lập repository trả về bài viết mock khi tìm kiếm theo ID
//        Mockito.when(articleRepository.findById(articleId.toString()))
//               .thenReturn(Optional.of(article));
//
//        // Act
//        ResponseEntity<?> response = articleService.editArticle(request);
//
//        // Assert: Kiểm tra response
//        Assertions.assertEquals(ResponseEntity.ok().body(ResponseCode.EditArticle.responseCodeToJson()), response);
//
//        // Kiểm tra repository đã được gọi đúng số lần
//        Mockito.verify(articleRepository, Mockito.times(1)).findById(articleId.toString());
//        Mockito.verify(articleRepository, Mockito.times(1)).save(article);
//
//        // Assert: Kiểm tra bài viết đã được cập nhật đúng
//        Assertions.assertEquals("New Title", article.getTitle());
//        Assertions.assertEquals("New Content", article.getContent());
//        Assertions.assertEquals("New Categorize", article.getCategorize());
//        Assertions.assertEquals(List.of("https://example.com"), article.getReferenceLink());
//    }
//
//}
//
