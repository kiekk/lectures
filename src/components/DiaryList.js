import {useState} from "react";

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

    const [sortType, setSortType] = useState('latest')

    const getProcessDiaryList = () => {
        const compare = (a, b) => {
            if (sortType === 'latest') {
                return parseInt(b.date) - parseInt(a.date)
            } else {
                return parseInt(a.date) - parseInt(b.date)
            }
        }
        const copyList = JSON.parse(JSON.stringify(diaryList))
        return copyList.sort(compare)
    }

    return <div>
        <ControlMenu
            value={sortType}
            onChange={setSortType}
            optionList={sortOptionList}
        />
        {getProcessDiaryList().map((it) => (
            <div key={it.id}>{it.content}</div>
        ))}
    </div>
}

DiaryList.defaultProps = {
    diaryList: [],
}

export default DiaryList

