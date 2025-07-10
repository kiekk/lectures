import './App.css';
import MemoContainer from './components/MemoContainer';
import SideBar from './components/SideBar';
import { useState } from 'react';

function App() {
  const [memos, setMemos] = useState([
    {
      title: 'Memo 1',
      content: 'This is memo 1',
      createdAt: 1751975318911, // new Date().getTime(),
      updatedAt: 1751975336373, // new Date().getTime(),
    },
    {
      title: 'Memo 2',
      content: 'This is memo 2',
      createdAt: 1751975361959, // new Date().getTime(),
      updatedAt: 1751975368155, // new Date().getTime(),
    },
  ]);

  const [selectMemoIndex, setSelectMemoIndex] = useState(0);

  const setMemo = (newMemo) => {
    // 불변성 유지를 위해 새로운 배열을 생성
    const newMemos = [...memos];
    newMemos[selectMemoIndex] = newMemo;
    setMemos([...newMemos]);
  };

  const addMemo = () => {
    const now = new Date().getTime();

    setMemos([
      ...memos,
      { title: 'Untitled', content: '', createdAt: now, updatedAt: now },
    ]);

    setSelectMemoIndex(memos.length);
  };

  return (
    <div className="App">
      <SideBar
        memos={memos}
        addMemo={addMemo}
        selectedMemoIndex={selectMemoIndex}
        setSelectedMemoIndex={setSelectMemoIndex}
      />
      <MemoContainer memo={memos[selectMemoIndex]} setMemo={setMemo} />
    </div>
  );
}

export default App;
