import React, { useState, useEffect, useRef } from 'react';
import axios from "axios";
import { useNavigate } from "react-router-dom";

import { CKEditor } from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import '@ckeditor/ckeditor5-build-classic/build/translations/ko';
//import { Image, ImageResizeEditing, ImageResizeHandles } from '@ckeditor/ckeditor5-image';

const WriteBoard = () => {
    const navigate = useNavigate();
    const editorRef = useRef('');
    const titleRef = useRef('');
    const boardSubmitURL = 'http://localhost:8080/board/writeBoard'
    const [board, setBoard] = useState({
        title: '',
        content: '',
        id: '아이디',
        name: '이름'
    });

    useEffect(() => {
        console.log('Reset', board);
    }, [board]);

    const onTitle = (e) => {
        const {value} = e.target;
        setBoard((prevData) => ({
            ...prevData,
            title: value
        }));
        //console.log('title: ',board)
    };

    const onContent = (editor) => {
        const data = editor.getData();
        setBoard(prevData => ({
            ...prevData,
            content: data
        }));
        console.log('content: ', board);
    };

    const onReset = () => {
        setBoard(prevData => ({
            title: '',
            content: '',
            id: '아이디',
            name: '이름'
        }));
        editorRef.current.setData('');
        titleRef.current.value = '';
        //console.log('Reset', board)
    };

    const onSubmit = () => {
        axios.post(boardSubmitURL, null, {
            params: board
        })
            .then(res => {
                console.log(res);
                alert('등록 완료!');
                navigate('/');
            })
            .catch(err => {
                console.log(err);
                alert('에러!!!')
            });
        console.log('Submit: ', board);
    };

    return (
        <div>
            <h2>
                <a href='https://ckeditor.com/docs/ckeditor5/latest/installation/integrations/react/react.html'>
                    CKEditor5</a>를 리액트에서 사용해보겠습니다!
            </h2>
            <p>
                CKEditor는 나온 지 오래되었으며 UI가 깔끔하여 사용하는 개발자가 많습니다.<br/>
            </p>

            <div className='CKEditor'>
                <input onChange={onTitle} ref={titleRef} type='text' name='ckeditorName' placeholder='제목'/>
                <CKEditor
                    editor={ClassicEditor}
                    config={{
                        placeholder: "내용입력해",
                        language: 'ko',
                        ckfinder: {
                            // 이미지 업로드 API 엔드포인트
                            uploadUrl: 'http://localhost:8080/board/writeImage'
                        }

                    }
                    }
                    onReady={editor => {
                        editorRef.current = editor;
                        console.log('Editor is ready to use!', editor);
                    }}
                    onChange={(event, editor) => {
                        console.log('Change', event);
                        onContent(editor);
                    }}

                />

                <button type='button' onClick={onSubmit}>제출하기</button>
                <button type='button' onClick={onReset}>초기화</button>
            </div>

            <h4>CKEditor를 들여오는 방법!</h4>
            <ol>
                <li>
                    우선, 터미널에서 라이브러리를 설치합니다. 오늘도 npm은 안되네요. yarn으로 갑시다.
                    <br/>yarn add @ckeditor/ckeditor5-react @ckeditor/ckeditor5-build-classic
                    <br/>npm install --save @ckeditor/ckeditor5-react @ckeditor/ckeditor5-build-classic
                    <br/>yarn add buffer
                    <br/>npm install buffer
                </li>
                <li>
                    공식 참고문서를 활용하여 코드를 적어줍니다.
                    import는 잊지말고 해줍니다. 위에서 설치한 CKEditor와 ClassicEditor입니다.
                    plugins 설치는
                </li>
                <li>
                    해당 웹에디터는 내용을 꾸미기 위한 것으로 제목은 input 태그로 따로 입력해 줍니다.
                    content 저장 시 html 내용을 포함하여 DB에 저장 됩니다.
                </li>
                <li>
                    내용을 출력할 땐
                    <br/><b>dangerouslySetInnerHTML = {'{{'}__html: 출력할데이터{'}}'}</b>
                    <br/>을 속성으로 넣어주어야 합니다.
                    이는 리액트에서 제공하지 않는 HTML을 강제로 사용하게 합니다. 이때문에 여러 문제가 발생할 가능성이 존재합니다.
                    <br/>p태그를 자동으로 사용하기 때문에 p태그의 마진을 없애주면 좋습니다.
                </li>
            </ol>
        </div>
    );
};
export default WriteBoard;
