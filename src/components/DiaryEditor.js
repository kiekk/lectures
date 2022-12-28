import {useState} from "react";
import MyHeader from "./MyHeader";
import MyButton from "./MyButton";
import {useNavigate} from "react-router-dom";

const getStringDate = (date) => {
  return date.toISOString().slice(0, 10)
}

const DiaryEditor = () => {
  const navigate = useNavigate()
  const [date, setDate] = useState(getStringDate(new Date()))

  return <div>
    <MyHeader
      headText={'새 일기쓰기'}
      leftChild={<MyButton text={'< 뒤로가기'} onClick={() => navigate(-1)}/>}
    />
    <section>
      <h4>오늘은 언제인가요?</h4>
      <div className='input-box'>
        <input
          type='date'
          value={date}
          onChange={(e) => setDate(e.target.value)}
        />
      </div>
    </section>
  </div>
}

export default DiaryEditor