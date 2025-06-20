package soono.board.comment.service.request;

import lombok.Getter;

@Getter
public class CommentCreateRequestV2 {
    private Long articleId;
    private String content;
    private String parentPath; // v2는 parentCommentId 대신 parentPath를 전달 받아 상위 댓글을 식별한다.
    private Long writerId;
}
