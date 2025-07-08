function MemoItem({ children, onClick, isSelected }) {
  return (
    <div
      className={'MemoItem' + (isSelected ? ' selected' : '')}
      onClick={onClick}
    >
      {children}
    </div>
  );
}

export default MemoItem;
