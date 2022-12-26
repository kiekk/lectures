import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";

import Home from "./pages/Home";
import Edit from "./pages/Edit";
import New from "./pages/New";
import Diary from "./pages/Diary";
import RouterTest from "./components/RouterTest";

function App() {
    return (
        <BrowserRouter>
            <div className="App">
                <h2>App.js</h2>


                <Routes>
                    <Route path='/' element={<Home/>}></Route>
                    <Route path='/edit' element={<Edit/>}></Route>
                    <Route path='/new' element={<New/>}></Route>
                    <Route path='/diary' element={<Diary/>}></Route>
                </Routes>

                <RouterTest/>
            </div>
        </BrowserRouter>
    );
}

export default App;
