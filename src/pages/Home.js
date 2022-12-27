import {useState} from "react";
import MyHeader from "../components/MyHeader";

const Home = () => {

    const [curDate, setCurDate] = useState(new Date())
    const headText = `${curDate.getFullYear()}년 ${curDate.getMonth() + 1}월`
    return (
        <div>
            <MyHeader headText={headText}/>
        </div>
    )
}

export default Home;