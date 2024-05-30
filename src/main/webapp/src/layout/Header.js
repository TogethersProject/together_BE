import React from 'react';
import {Link} from "react-router-dom";
import Main from "../Main";
import WriteList from "../board/WriteList";
import WriteBoard from "../board/WriteBoard";

const Header = () => {
    return (
        <div>
            <Link to={'/'} element={<Main/>}>메인</Link>
            <Link to={'/board/writeBoard'} element={<WriteBoard/>}>글쓰기</Link>
            <Link to={'/board/writeList/0'} element={<WriteList/>}>글목록</Link>
        </div>
    );
};

export default Header;