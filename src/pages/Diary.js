import {useNavigate, useParams} from "react-router-dom";
import {DiaryStateContext} from "../App";
import {useContext, useEffect, useState} from "react";
import MyHeader from "../components/MyHeader";
import {getStringDate} from "../util/date";
import MyButton from "../components/MyButton";

const Diary = () => {
  const navigate = useNavigate()
  const {id} = useParams()
  const diaryList = useContext(DiaryStateContext)
  const [data, setData] = useState()

  useEffect(() => {
    if (diaryList.length >= 1) {
      const targetDiary = diaryList.find((it) => parseInt(it.id) === parseInt(id))

      if (targetDiary) {
        setData(targetDiary)
      } else {
        alert('없는 일기입니다.')
        navigate('/', {replace: true})
      }
    }
  }, [id, diaryList, navigate])

  if (!data) {
    return <div className='DiaryPage'>로딩중입니다...</div>
  } else {
    return (
      <div className='DiaryPage'>
        <MyHeader
          headText={`${getStringDate(new Date(data.date))} 기록`}
          leftChild={<MyButton text={'< 뒤로가기'} onClick={() => navigate(-1)}/>}
          rightChild={<MyButton text={'수정하기'} onClick={() => navigate(`/edit/${data.id}`)}/>}
        />
      </div>
    )
  }
}

export default Diary;