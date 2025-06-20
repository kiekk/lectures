package soono.board.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soono.board.comment.service.CommentServiceV2;
import soono.board.comment.service.request.CommentCreateRequestV2;
import soono.board.comment.service.response.CommentPageResponseV2;
import soono.board.comment.service.response.CommentResponseV2;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentV2Controller {

    private final CommentServiceV2 commentService;

    @GetMapping("/v2/comments/articles/{articleId}/count")
    public Long count(@PathVariable("articleId") Long articleId) {
        return commentService.count(articleId);
    }

    @GetMapping("/v2/comments")
    public CommentPageResponseV2 readAll(
            @RequestParam("articleId") Long articleId,
            @RequestParam(value = "page") Long page,
            @RequestParam(value = "pageSize") Long pageSize) {
        return commentService.readAll(articleId, page, pageSize);
    }

    @GetMapping("/v2/comments/infinite-scroll")
    public List<CommentResponseV2> readAll(
            @RequestParam("articleId") Long articleId,
            @RequestParam(value = "lastParentCommentId", required = false) String lastPath,
            @RequestParam(value = "pageSize") Long pageSize) {
        return commentService.readAllInfiniteScroll(articleId, lastPath, pageSize);
    }

    @GetMapping("/v2/comments/{commentId}")
    public CommentResponseV2 read(@PathVariable("commentId") Long commentId) {
        return commentService.read(commentId);
    }

    @PostMapping("/v2/comments")
    public CommentResponseV2 create(@RequestBody CommentCreateRequestV2 request) {
        return commentService.create(request);
    }

    @DeleteMapping("/v2/comments/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
    }
}
