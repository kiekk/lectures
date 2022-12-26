import React, {useReducer, useRef} from "react";
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";

import Home from "./pages/Home";
import Edit from "./pages/Edit";
import New from "./pages/New";
import Diary from "./pages/Diary";

const reducer = (state, action) => {
    let newState = []
    switch (action.type) {
        case 'INIT': {
            return action.data
        }
        case 'CREATE' : {
            newState = [action.data, ...state]
            break
        }
        case 'REMOVE': {
            newState = state.filter((it) => it.id !== action.targetId)
            break
        }
        case 'EDIT': {
            newState = state.map((it) => it.id === action.data.id ? {...action.data} : it)
            break
        }
        default :
            return state
    }
    return newState
}

export const DiaryStateContext = React.createContext()

function App() {
    const [data, dispatch] = useReducer(reducer, [])
    const dataId = useRef(0)

    // Create
    const onCreate = (date, content, emotion) => {
        dispatch({
            type: 'CREATE',
            data: {
                id: dataId.current,
                date: new Date(date).getTime(),
                content,
                emotion
            }
        })
        dataId.current += 1
    }

    // Remove
    const onRemove = (targetId) => {
        dispatch({
            type: 'REMOVE',
            targetId
        })
    }

    // Edit
    const onEdit = (targetId, date, content, emotion) => {
        dispatch({
            type: 'EDIT',
            data: {
                id: targetId,
                date: new Date(date).getTime(),
                content,
                emotion
            }
        })
    }

    return (
        <DiaryStateContext.Provider value={data}>
            <BrowserRouter>
                <div className="App">
                    <Routes>
                        <Route path='/' element={<Home/>}></Route>
                        <Route path='/edit' element={<Edit/>}></Route>
                        <Route path='/new' element={<New/>}></Route>
                        <Route path='/diary' element={<Diary/>}></Route>
                    </Routes>
                </div>
            </BrowserRouter>
        </DiaryStateContext.Provider>
    );
}

export default App;
