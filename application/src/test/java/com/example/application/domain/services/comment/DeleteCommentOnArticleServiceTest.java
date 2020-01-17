package com.example.application.domain.services.comment;

import com.example.application.domain.exceptions.CommentNotFoundException;
import com.example.application.domain.model.Comment;
import com.example.application.domain.model.CommentId;
import com.example.application.domain.ports.in.DeleteCommentOnArticleUseCase;
import com.example.application.domain.ports.in.GetArticleQuery;
import com.example.application.domain.ports.out.DeleteCommentPort;
import com.example.application.domain.ports.out.LoadCommentPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteCommentOnArticleServiceTest {

    @InjectMocks
    private DeleteCommentOnArticleService sut;

    @Mock
    private LoadCommentPort loadCommentPort;
    @Mock private GetArticleQuery getArticleQuery;
    @Mock private DeleteCommentPort deleteCommentPort;


    @DisplayName("Comment MUST exist in order to be deleted")
    @Test
    void can_only_delete_comment_if_it_exists(){
        // Arrange
        UUID id = UUID.randomUUID();
        var commentId = CommentId.builder().id(id).build();
        var input = DeleteCommentOnArticleUseCase.DeleteCommentCommand.builder().commentId(commentId).build();

        Mockito.when(this.loadCommentPort.getComment(commentId)).thenReturn(Optional.empty());
        // Act & Assert

        Assertions.assertThrows(CommentNotFoundException.class, () -> sut.delete(input));
    }

    @DisplayName("Deletes a comment for the specified article")
    @Test
    void deletes_a_comment_for_the_specified_article(){
        // Arrange
        var uuid = UUID.randomUUID();
        var id = CommentId.builder().id(uuid).build();
        var comment = Comment.builder().id(id).build();
        var input = DeleteCommentOnArticleUseCase.DeleteCommentCommand.builder().commentId(id).build();
        Mockito.when(this.loadCommentPort.getComment(id)).thenReturn(Optional.of(comment));
        // Act
        Comment actual = sut.delete(input);
        // Assert
        Mockito.verify(this.deleteCommentPort).delete(id);
        Assertions.assertEquals(id, actual.getId());
    }
}