import React, { useEffect, useState, useRef } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
import {CKEditor} from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import '@ckeditor/ckeditor5-build-classic/build/translations/ko';

const UpdateBoard = () => {
    const { seq } = useParams();
    const navigate = useNavigate();
    const editorRef = useRef('');
    const titleRef = useRef('');
    const boardGetURL = 'http://localhost:8080/board/getUpdateBoard';
    const boardSubmitURL = 'http://localhost:8080/board/updateBoard';

    const [board, setBoard] = useState({
        title: '',
        content: '',
        id: '아이디',
        name: '이름',
        //seq:'',board_time='',board_lastTime='' //밑에서 추가.
    });
    const [imageNamesBefore, setImageNamesBefore] = useState([]); // 수정 전 이미지들

    useEffect(() => {
        axios.post(boardGetURL, null, { params: { seq: seq } })
            .then(res => {
                console.log(res);
                setBoard(prevData => ({
                    ...prevData,
                    title: res.data.boardDTO.title,
                    content: res.data.boardDTO.content,
                    board_time:res.data.boardDTO.board_time,
                    seq: res.data.boardDTO.seq
                }));
                setImageNamesBefore(res.data.imageList);
            })
            .catch(err => console.log(err));
    }, [seq]);

// 이미지 이름 목록 상태가 변경될 때마다 로그로 출력
    useEffect(() => {
        console.log('update-image', imageNamesBefore);
    }, [imageNamesBefore]);
    useEffect(() => {
        console.log('update-board', board);
    }, [board]);

    const onTitle =(e) => {
        const { value } = e.target;
        setBoard((prevData) => ({
            ...prevData,
            title: value
        }));
        //console.log('title: ',board)
    }
    const onContent = (editor) => {
        const data = editor.getData();
        setBoard(prevData=>({
            ...prevData,
            content: data
        }))
        console.log('content: ',board)
    }

    const onReset = () => {
        setBoard(prevData=>({
            title:''
            ,content:''
            ,id: '아이디'
            ,name: '이름'
        }))
        editorRef.current.setData('');
        titleRef.current.value = '';
        //console.log('Reset', board)
    }

    const onSubmit = () => {
        axios.post(boardSubmitURL, { board, imageNamesBefore }, {
            headers: { 'Content-Type': 'application/json' }
        })
            .then(res => {
                console.log(res);
                alert('수정 완료!');
                navigate('/');
            })
            .catch(err => {
                console.log(err);
                alert('에러!!!');
            });
        console.log('Submit: ', board);
    }

    return (
        <div>
            <h2>CKEditor5를 리액트에서 사용해보겠습니다!</h2>


            <div className='CKEditor'>
                <input onChange={onTitle} ref={titleRef} value={board.title} type='text' name='ckeditorName' placeholder='제목'/>
                <CKEditor
                    editor={ClassicEditor}
                    data={board.content}
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
            </div>
        </div>
    );
};

export default UpdateBoard;
