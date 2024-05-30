import './App.css';
// import WriteList from "./board/WriteList";
import WriteBoard from "./board/WriteBoard";
// import UpdateBoard from "./board/UpdateBoard";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Main from "./Main";
import WriteList from "./board/WriteList";
import UpdateBoard from "./board/UpdateBoard";
// import Header from "./layout/Header";

const App = () => {
    return (

        <BrowserRouter>
            <Routes>
                <Route path='/' element={<Main />} />
                <Route path='/board/writeList/:page' element={<WriteList />} />
                {/*/!*<Route path='/api/business' element={<Business />} />*!/*/}
                {/*/!*<Route path='/api/map' element={<Map />} />*!/*/}
                <Route path='/board/writeBoard' element={<WriteBoard />} />
                <Route path='/board/updateBoard/:seq' element={<UpdateBoard />} />
            </Routes>
        </BrowserRouter>
    );
};

export default App;
