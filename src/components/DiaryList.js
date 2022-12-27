import {useState} from "react";
import MyButton from "./MyButton";
import {useNavigate} from "react-router-dom";

const filterOptionList = [
    {value: 'all', name: '전부 다'},
    {value: 'good', name: '좋은 감정만'},
    {value: 'bad', name: '안좋은 감정만'},
]

const sortOptionList = [
    {value: 'latest', name: '최신 순'},
    {value: 'oldest', name: '오래된 순'},
]

const ControlMenu = ({value, onChange, optionList}) => {
    return <select value={value} onChange={(e) => onChange(e.target.value)}>
        {optionList.map((it, idx) => <option key={idx} value={it.value}>{it.name}</option>)}
    </select>
}

const DiaryList = ({diaryList}) => {
    const navigate = useNavigate()
    const [sortType, setSortType] = useState('latest')
    const [filter, setFilter] = useState('all')

    const getProcessDiaryList = () => {
        const filterCallBack = (item) => {
            if (filter === 'good') {
                return parseInt(item.emotion) <= 3
            } else {
                return parseInt(item.emotion) > 3
            }
        }
        const compare = (a, b) => {
            if (sortType === 'latest') {
                return parseInt(b.date) - parseInt(a.date)
            } else {
                return parseInt(a.date) - parseInt(b.date)
            }
        }
        const copyList = JSON.parse(JSON.stringify(diaryList))
        const filterdList = filter === 'all' ? copyList : copyList.filter(filterCallBack)
        return filterdList.sort(compare)
    }

    return <div>
        <ControlMenu
            value={sortType}
            onChange={setSortType}
            optionList={sortOptionList}
        />
        <ControlMenu
            value={filter}
            onChange={setFilter}
            optionList={filterOptionList}
        />
        <MyButton
            type={'positive'}
            text={'새 일기 등록'}
            onClick={() => navigate('/new')}
        />
        {getProcessDiaryList().map((it) => (
            <div key={it.id}>{it.content} {it.emotion}</div>
        ))}
    </div>
}

DiaryList.defaultProps = {
    diaryList: [],
}

export default DiaryList

