### 댓글 테이블 설계

#### 요구사항

- 계층형 대댓글
- 하위 댓글이 모두 삭제되어야 상위 댓글을 삭제할 수 있다.
  - 하위 댓글이 없으면 댓글을 즉시 삭제된다.
  - 하위 댓글이 있으면 댓글은 삭제 표시만 되며 "삭제된 댓글입니다."와 같이 노출된다.

#### 댓글 Depth

- 계층형의 depth에 따라서 설계 방식으 달리할 수 있다.
  - 최대 2 depth (Adjacency list: 인접 리스트)
  - 무한 depth (Path Enumeration: 경로 열거)

#### [댓글 최대 2 depth](2depth/README.md)

#### [댓글 무한 depth](infinite/README.md)

#### 참고 자료
- 계층형 게시글, 댓글의 경우 이전에 만들어본 Repository가 있으니 설계 부분에서 같이 참고하면 좋을 것 같다.
  - 설계는 강의와 달리 depth 와 관련된 정보들 (group, depth)등의 정보를 컬럼으로 관리한다.
    - groupNo: 그룹 번호
    - groupOrd: 그룹 내 순서
    - depth: 깊이
    - parentBno: 부모 게시글 번호
  - [계층형 게시판, 댓글 Repository](https://github.com/kiekk/study-springboot-2/blob/master/src/main/java/com/example/domain/BoardVO.java)