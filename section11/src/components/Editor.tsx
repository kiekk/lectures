import React, { useState } from 'react'
import { useTodoDispatch } from '../App'

interface Props {
}

export default function Editor(props: Props) {

  const dispatch = useTodoDispatch()

  const [text, setText] = useState('')

  const onChangeInput = (event: React.ChangeEvent<HTMLInputElement>) => {
    setText(event.target.value)
  }

  const onClickButton = () => {
    dispatch.onClickAdd(text)
    setText('')
  }

  return (
    <div>
      <input type='text' value={text} onChange={onChangeInput} />
      <button onClick={onClickButton}>추가</button>
    </div>
  )
}