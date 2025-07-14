import './App.css';
import MemoContainer from './components/MemoContainer';
import SideBar from './components/SideBar';
import { useCallback, useState } from 'react';
import { setItem, getItem } from './lib/storage';
import debounce from 'lodash.debounce';

// debounce 함수를 사용하여 마지막 입력 후 5초가 지날 때 setItem을 한 번만 실행하도록 설정
let debouncedSetItem = debounce(setItem, 5000);

function App() {
  const [memos, setMemos] = useState(getItem('memo') || []);

  const [selectMemoIndex, setSelectMemoIndex] = useState(0);

  const setMemo = useCallback(
    (newMemo) => {
      // setMemos(newMemos);
      setMemos((memos) => {
        // 불변성 유지를 위해 새로운 배열을 생성
        const newMemos = [...memos];
        newMemos[selectMemoIndex] = newMemo;
        debouncedSetItem('memo', newMemos);
        return newMemos;
      });
    },
    [selectMemoIndex],
  );

  const addMemo = useCallback(() => {
    setMemos((memos) => {
      const now = new Date().getTime();
      let newMemos = [
        ...memos,
        { title: 'Untitled', content: '', createdAt: now, updatedAt: now },
      ];
      debouncedSetItem('memo', newMemos);
      return newMemos;
    });
    setSelectMemoIndex(memos.length);
  }, [memos]);

  const deleteMemo = useCallback(
    (index) => {
      setMemos((memos) => {
        const newMemos = [...memos];
        newMemos.splice(index, 1);
        debouncedSetItem('memo', newMemos);
        return newMemos;
      });

      if (index === selectMemoIndex) {
        setSelectMemoIndex(0);
      }
    },
    [selectMemoIndex],
  );

  return (
    <div className="App">
      <SideBar
        memos={memos}
        addMemo={addMemo}
        deleteMemo={deleteMemo}
        selectedMemoIndex={selectMemoIndex}
        setSelectedMemoIndex={setSelectMemoIndex}
      />
      <MemoContainer memo={memos[selectMemoIndex]} setMemo={setMemo} />
    </div>
  );
}

export default App;
