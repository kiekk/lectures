## 섹션 4. Chapter 03 컴포넌트

## 컴포넌트

### 컴포넌트란?
```markdown
리액트 컴포넌트는 React 앱의 UI를 구성하는 독립적이고 재사용 가능한 UI 블록입니다. 
컴포넌트는 함수 또는 클래스로 정의될 수 있으며, 각각은 특정 UI 요소를 렌더링하고 상태를 관리하는 역할을 합니다.

- 스스로 상태를 관리하는 캡슐화된 코드 조각
- 하나의 JSX를 반환하는 함수

React는 이러한 컴포넌트라는 단위로 화면이 구성됩니다.
```

### JSX와의 차이점
```markdown
- 컴포넌트는 기본적으로 함수이기 때문에 자신만의 고유한 로직이 존재
- 스스로의 상태 반영 가능
```

### 컴포넌트 생성 시 주의사항
```markdown
- 컴포넌트 이름은 무조건 PascalCase로 작성해야 함
- 의미단위로 쪼개어 파일을 분리해야 함
- 최상위 컴포넌트 이름은 일반적으로 App으로 작성
```

## Props

### Props란?
```markdown
- Props는 Properties의 약자로, 부모 컴포넌트에서 자식 컴포넌트로 내려주는 데이터를 의미
```

### Props의 활용 팁
```markdown
- 구조분해할당 구문 활용
- 특정 Props에 기본 값을 지정 가능
- Props는 읽기 전용
```

## State

### State란?
```markdown
- 컴포넌트 내부에서 사용되는 일종의 변수
- 컴포넌트 스스로 상태를 관리하게 만드는 존재
```

## 클래스형 컴포넌트 vs 함수형 컴포넌트

### 클래스형 컴포넌트
```markdown
- 클래식 문법으로 구현한 옛날 컴포넌트
```

### 함수형 컴포넌트
```markdown
- 구조가 클래스보다 단순한 함수를 사용하기로 결정하며 대세로 사용되는 중
- 코드 재활용성에 유리한 새로운 컴포넌트 제작 방식
```

### Hooks의 등장 이유
```markdown
- 클래스형 컴포넌트의 단점을 보완하기 위해 함수형 컴포넌트에 지급된 개념
```
