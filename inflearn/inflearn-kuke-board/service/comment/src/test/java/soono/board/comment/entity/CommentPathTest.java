package soono.board.comment.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommentPathTest {

    @ParameterizedTest
    @MethodSource("createChildCommentPathTestParameters")
    void createChildCommentPathTest(CommentPath commentPath, String descendantsTopPath, String expected) {
        CommentPath childCommentPath = commentPath.createChildCommentPath(descendantsTopPath);
        assertThat(childCommentPath.getPath()).isEqualTo(expected);
    }

    static Stream<Arguments> createChildCommentPathTestParameters() {
        return Stream.of(
                Arguments.of(CommentPath.create(""), null, "00000"),
                Arguments.of(CommentPath.create("00000"), null, "0000000000"),
                Arguments.of(CommentPath.create(""), "00000", "00001"),
                Arguments.of(CommentPath.create("0000z"), "0000zabcdzzzzzzzzzzz", "0000zabce0")
        );
    }

    @Test
    @DisplayName("Max Depth면 예외가 발생한다.")
    void createChildCommentPathIfMaxDepthTest() {
        assertThatThrownBy(() -> CommentPath.create("zzzzz".repeat(5)).createChildCommentPath(null))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("Chunk가 overflow되면 예외가 발생한다.")
    void createChildCommentPathIfChunkOverflowTest() {
        // given
        CommentPath commentPath = CommentPath.create("");

        // when, then
        assertThatThrownBy(() -> commentPath.createChildCommentPath("zzzzz"))
                .isInstanceOf(IllegalStateException.class);
    }

}
