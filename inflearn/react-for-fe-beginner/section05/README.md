## 섹션 5. Chapter 04 LifeCycle과 Hooks

## Hooks
React Hooks는 **함수형 컴포넌트(Function Component)** 에서 **상태 관리(state)** 나 라이프사이클 기능을 사용할 수 있도록 React 16.8에서 도입된 기능입니다.
이전에는 이런 기능이 클래스형 컴포넌트에서만 가능했습니다.

### Hooks란?
```markdown
- Hooks는 React 함수형 컴포넌트에서 상태나 리액트 생명주기 기능을 "hook(걸어)" 쓸 수 있게 해주는 함수입니다.
- useState, useEffect, useContext 등의 함수로 제공됩니다.
- 이름이 항상 use로 시작합니다 (useSomething).
```

### 기본(Built-in) Hooks 종류
| Hook                  | 설명                                                                           |
| --------------------- | ---------------------------------------------------------------------------- |
| `useState`            | 컴포넌트 내에서 상태(state)를 사용할 수 있게 해줍니다.                                           |
| `useEffect`           | 컴포넌트가 마운트, 업데이트, 언마운트될 때 특정 작업(부수 효과, side effect)을 수행합니다.                   |
| `useContext`          | 리액트 컨텍스트(Context)를 사용하여 전역 상태를 관리하거나 공유할 수 있게 합니다.                           |
| `useRef`              | DOM 요소나 어떤 값을 컴포넌트 생애주기 전체에서 기억할 수 있게 해줍니다. (값이 변경되어도 리렌더링되지 않음)             |
| `useMemo`             | 복잡한 계산 결과를 메모이제이션하여 성능 최적화에 사용됩니다.                                           |
| `useCallback`         | 콜백 함수를 메모이제이션하여 불필요한 함수 재생성을 막습니다.                                           |
| `useReducer`          | 상태 업데이트 로직이 복잡할 때 `reducer` 함수로 상태를 관리하는 방식입니다. (Redux의 reducer와 유사)         |
| `useLayoutEffect`     | `useEffect`와 유사하지만 DOM 업데이트 직후에 동기적으로 실행됩니다.                                 |
| `useImperativeHandle` | `ref`를 사용하는 부모 컴포넌트가 자식의 일부 함수나 값을 직접 제어할 수 있게 합니다. (보통 `forwardRef`와 함께 사용) |
| `useId` (React 18)    | 고유한 ID를 생성하는 데 사용됩니다. SSR/클라이언트 간 ID 불일치 문제 방지용입니다.                          |

### 📌 가장 기본적인 예시
useState 예시:
```jsx
import React, { useState } from 'react';

function Counter() {
  const [count, setCount] = useState(0); // 초기값 0

  return (
    <div>
      <p>카운트: {count}</p>
      <button onClick={() => setCount(count + 1)}>증가</button>
    </div>
  );
}
```

useEffect 예시:
```jsx
import React, { useEffect } from 'react';

function Timer() {
  useEffect(() => {
    console.log('컴포넌트가 마운트됨');
    return () => {
      console.log('컴포넌트가 언마운트됨');
    };
  }, []); // 빈 배열: 마운트/언마운트 시 1회만 실행
}
```

### ✅ Hooks 사용 규칙
```markdown
- 함수 컴포넌트의 최상위에서만 사용해야 합니다.
- 조건문이나 반복문, 함수 내부에서 사용하면 안 됩니다.
- React 함수형 컴포넌트 또는 Custom Hook 내에서만 사용해야 합니다.
- 일반 JavaScript 함수나 클래스에서는 사용할 수 없습니다.
```

## React 렌더링 과정

### React의 렌더링 과정
```markdown
Rerendering
- 컴포넌트의 상태 즉 State 또는 Props가 변경되면서 해당 컴포넌트를 다시 실행하여 화면을 다시 그리는 것을 의미
```

### 클래스형 컴포넌트의 라이프 사이클
```markdown
- 컴포넌트가 실행되는 것 = 컴포넌트의 마운트
- 컴포넌트가 지워지는 것 = 언마운트
  
- 마운트가 되었을 때 constructor 실행 
  → getDerivedStateFromProps 메소드 실행 
  → render 메소드 실행 
  → componentDidMount라는 메소드 실행
- 메소드에서 컴포넌트를 다시 rendering 할 것인지, 이번 상태 변화에 따른 -rerendering은 생략할 것인지를 결정
- 함수가 false를 반환하면 렌더링을 진행하지 않고 true를 반환하면 렌더링을 진행
- rendering이 완료되면 마무리로 componentDidUpdate를 실행
```

### 함수형 컴포넌트의 라이프 사이클
```markdown
마운트 
    → 바로 자기 자신을 실행 
    → 반환된 JSX 값을 DOM에 반영
    → useEffect라는 Hook을 실행
    → 클래스형 컴포넌트의 componentDidMount, componentDidUpdate,componentWillUnmount 메소드 = useEffect hook으로 통일
    → 데이트 할 때는 다시 함수를 실행 
    → 그 JSX를 DOM에 반영
    → useEffect hook을 실행
언마운트 시엔 useEffect 함수만 마지막으로 실행
```
