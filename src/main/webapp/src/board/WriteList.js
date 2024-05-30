import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import axios from 'axios';

const WriteList = () => {
    const boardDeleteURL = 'http://localhost:8080/board/deleteBoard';
    const { page } = useParams();
    //const navigate = useNavigate();
    const [boardDTOList, setBoardDTOList] = useState([]);

    const getBoardList = async () => {
        try {
            const res = await axios.post('http://localhost:8080/board/getWriteList', null, {
                params: {
                    page: page
                }
            });
            setBoardDTOList(res.data.content);
        } catch (err) {
            console.log("에러발생" + err);
        }
    }

    useEffect(() => {
        getBoardList();
    }, [page]);

    useEffect(() => {
        boardDTOList.forEach((item) => {
            const contentRef = document.getElementById(`content-${item.seq}`);
            if (contentRef) {
                const parser = new DOMParser();
                const doc = parser.parseFromString(item.content, 'text/html');
                const oembedTags = doc.querySelectorAll('oembed');
                oembedTags.forEach(oembedTag => {
                    const url = oembedTag.getAttribute('url');
                    if (url.includes('youtube.com')) {
                        const urlObj = new URL(url);
                        const videoId = urlObj.searchParams.get('v');
                        const embedUrl = `https://www.youtube.com/embed/${videoId}`;
                        const iframe = document.createElement('iframe');
                        iframe.src = embedUrl;
                        iframe.title = "video";
                        iframe.allowFullscreen = true;
                        iframe.width = "560";
                        iframe.height = "315";
                        oembedTag.replaceWith(iframe);
                    }
                })
                contentRef.innerHTML = doc.body.innerHTML;
            }
        });
    }, [boardDTOList]);

    const onDeleteBoard = (seq) => {
        axios.post(boardDeleteURL, null, {
            params: {
                seq: seq // 삭제할 글 번호
            }
        })
            .then(res => {
                console.log(res);
                alert('삭제 완료!');
                getBoardList(); // 리스트를 다시 불러와서 삭제된 상태를 반영
            })
            .catch(err => {
                console.log(err);
                alert('에러!!!');
            });
    };

    return (
        <div>
            <h1>글 목록입니다.</h1>
            <table border='1'>
                <thead>
                <tr>
                    <th>글번호</th>
                    <th>이름</th>
                    <th>제목</th>
                    <th>내용</th>
                    <th>버튼</th>
                </tr>
                </thead>
                <tbody>
                {boardDTOList.map((item) => (
                    <tr key={item.seq}>
                        <td>{item.seq}</td>
                        <td>{item.name}</td>
                        <td>{item.title}</td>
                        <td id={`content-${item.seq}`}></td>
                        <td><Link to={`/board/updateBoard/${item.seq}`}><button type='button'>글수정</button></Link></td>
                        <td><button type='button' onClick={() => onDeleteBoard(item.seq)}>글삭제</button></td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default WriteList;
