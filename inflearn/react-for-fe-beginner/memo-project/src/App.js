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

  return (
    <div className="App">
      <SideBar memos={memos} />
      <MemoContainer />
    </div>
  );
}

export default App;
