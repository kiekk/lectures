import MemoList from './MemoList';
import SideBarHeader from './SideBarHeader';
import SideBarFooter from './SideBarFooter';

function SideBar({ memos, addMemo, selectedMemoIndex, setSelectedMemoIndex }) {
  return (
    <div className="SideBar">
      <SideBarHeader />
      <MemoList
        memos={memos}
        selectedMemoIndex={selectedMemoIndex}
        setSelectedMemoIndex={setSelectedMemoIndex}
      />
      <SideBarFooter onClick={addMemo} />
    </div>
  );
}

export default SideBar;
