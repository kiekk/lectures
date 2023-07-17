import React, { useState } from 'react'

interface Props {
  onClickAdd: (text: string) => void
}

export default function Editor(props: Props) {

  const [text, setText] = useState('')

  const onChangeInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    setText(event.target.value)
  }

  const onClickButton = () => {
    props.onClickAdd(text)
    setText('')
  }

  return (
    <div>
      <input type='text' value={text} onChange={onChangeInput} />
      <button onClick={onClickButton}>추가</button>
    </div>
  )
}