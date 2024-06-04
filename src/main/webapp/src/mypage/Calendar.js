import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";

import './myCalendar.css';
import FullCalendar from '@fullcalendar/react';
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import dayGridPlugin from '@fullcalendar/daygrid';

import WriteCalendar from './WriteCalendar';
import axios from "axios";
import UpdateDeleteCalendar from "./UpdateDeleteCalendar";


const Calendar = () => {
    //유저 id 저장
    const {member_id} = useParams();
    //일정 리스트 저장소.   리스트 범위는 현재 보이는 달력만큼. 따라서 달력을 일간/주간/월간으로 바꾸거나, 다음 달로 넘어 가는 등의 버튼을 누르거나, 수정 삭제 시 매번 새로 업데이트
    const [events, setEvents] = useState([
        //{ id: '99', title: 'event 1', start: '2024-05-02', end:'2024-05-05', allDay: false, content: '아파트 청소', borderColor: 'pink', backgroundColor:'skyblue',textColor:'yellow',eventDurationEditable: true },
        //{ title: 'event 2', date: '2024-06-01', end:'2024-06-03',allDay: true }
    ]);
    //일정 하나 저장소.    일정을 자세히 보여주거나, 수정 삭제 등의 일을 할 때 여기에 정보를 담음.
    const [event, setEvent] = useState({
        title:'' ,content:'' //,start:''    //필수로 넣어야함.
        ,id:'' ,calendar_memberId:member_id   //필수지만 자동으로 들어감. 일정 번호(seq)와 유저 아이디.
        //, allDay:'' ,backgroundColor:'' ,end:''
    });
    //현재 달력에서 보이는 날짜.    date가 변하면 일정 리스트를 다시 받아야한다.
    const [date, setDate] =useState({
        start: '',
        end: ''
    })
    // 일정 작성 모달 표시 여부를 위한 상태. 헷갈리면 나중에 W(write) 붙이자.
    const [showModalState, setShowModalState] = useState(false);
    // 일정 수정 혹은 삭제 모달 표시 여부를 위한 상태
    const [showUDModalState, setShowUDModalState] = useState(false);

    //DB에서 캘린더 정보 가져와 events에 저장. data로 넘겨서 requestbody로 받아도 됨.
    const getCalendarURL = "http://localhost:8080/calendar/getCalendarlist";
    const getCalendar = () => {
        //console.log(date.start+"\n"+date.end+"\n"+event.calendar_memberId)
        axios.get(getCalendarURL
            ,{params:{
                    startDate: date.start
                    ,endDate: date.end
                    ,memberId: event.calendar_memberId}}
            ).then(res => {
                //console.log("캘린더 가져옴!")
                //console.log(res.data)
                setEvents(prevEvents => [ ...res.data]);
        })
            .catch(err => console.log("캘린더 못가져옴\n" + err))
    }

    //날짜 정보가 바뀔 때마다 캘린더 정보 갱신.  date = 달력에서 보여주는 날짜.
    useEffect(()=>{
        if (date.start && date.end) {
            getCalendar();
        }
    },[date])

    // 달력에서 날짜 하나를 클릭한 경우 -> write와 관련된 모달 보여줌
    const dateClick=(info)=>{
        //console.log(info)

        //모달창이 나온다.
        setEvent( preData => ({
                //...preData
                start: info.date
                ,allDay: false  //allDay면 <->로 일정 조절 가능하다는 장점 & 시간이 안보인다는 단점
                ,calendar_memberId: member_id
        }));
        setShowModalState(true)
    }

    // 달력에서 드래그로 날짜 여러개를 선택(select)한 경우 -> write와 관련된 모달 보여줌
    const dateSelect=(info)=>{
        //console.log(info)

        //모달창이 나온다.
        setEvent( preData => ({
            start: info.start
            ,end: info.end
            ,title:''
            ,content:''
            ,backgroundColor:''
            ,calendar_memberId: member_id
        }));
        setShowModalState(true)
    }

    // 일정 내역을 저장.
    const onEvent= (e) => {
        //이름대로 값 연결함. 즉, name이 title인 곳에 적힌 값이 Event.title에 저장
        const { name, value } = e.target;
        setEvent(prevData => ({
            ...prevData,
            [name]: value, // `name`에 해당하는 상태를 `value`로 업데이트합니다.
            backgroundColor: name === 'backgroundColor' ? value : prevData.backgroundColor, // `backgroundColor` 속성을 업데이트합니다.

        }));
        //console.log('onEvent: ',event)
    }

    //현재 보고 있는 달력의 시작 및 종료 날짜 산출 -> date에 저장
    const dateSet = (dateInfo => {
        console.log(dateInfo.startStr); // 현재 보고 있는 달력의 시작 날짜
        console.log(dateInfo.endStr); // 현재 보고 있는 달력의 종료 날짜
        setDate(preDate=>({
            ...preDate
            ,start:dateInfo.startStr
            ,end:dateInfo.endStr}))

        // 현재 월을 확인하려면 dateInfo.startStr 또는 dateInfo.endStr을 파싱
        //const currentMonth = new Date(dateInfo.startStr).getMonth() + 1; // getMonth()는 0부터 시작하므로 +1
        //console.log(`현재 보고 있는 달: ${currentMonth}월`);
    })

    //작성되어있는 이벤트를 드래그를 통해 이동 시키거나 클릭한 경우
    const onDragEvent = (info) => {
        console.log("드래그")
        console.log(info)
        // console.log("캘린더아이디: " + info.event._def.extendedProps.calendar_id)
        // console.log("멤버아이디: " + info.event._def.extendedProps.calendar_memberId)
        // console.log("원래날짜: " + info.oldEvent._instance.range.start + " ~ " + info.oldEvent._instance.range.end)
        // console.log("바뀐날짜: " + info.event._instance.range.start + " ~ " + info.event._instance.range.end)
        // console.log("배경색: " + info.event._def.extendedProps.backgroundColor)
        // console.log('content: ' + info.el.fcSeg.eventRange.def.extendedProps.content
        //             +'\nid: ' + info.el.fcSeg.eventRange.def.extendedProps.calendar_id
        //             +'\nrange: ' + info.el.fcSeg.eventRange.range.start + ' ~ ' + info.el.fcSeg.eventRange.range.end
        //             +'\ncolor(경계선,바탕,글씨): '+ info.el.fcSeg.eventRange.ui.borderColor + "/" + info.el.fcSeg.eventRange.ui.backgroundColor + "/" + info.el.fcSeg.eventRange.ui.textColor)

        //클릭한 일정 정보를 event에 담음
        goUDModal(info)
        //수정 삭제 모달 보여줘
        setShowUDModalState(true)
    }

    //수정 삭제 작업을 위해 클릭한 일정 정보를 DB에서 가져와 저장한다. -> DB에 가지 안아도 정보를 받을 수 있으나 시간 정보가 불안정하며, allDay의 경우 시간을 받아오지 않는다.
    const getOneCalendar = "http://localhost:8080/calendar/getOneCalendar"
    const goUDModal = (info) =>{
        const calendar_id = info.event._def.extendedProps.calendar_id

        const utcNewStartDate = new Date(info.event._instance.range.start);
        const utcNewEndDate = new Date(info.event._instance.range.end);
        const kstNewStartDate = new Date(utcNewStartDate.getTime() - (9 * 60 * 60 * 1000)); // 9시간을 -
        const kstNewEndDate = new Date(utcNewEndDate.getTime() - (9 * 60 * 60 * 1000)); // 9시간을 -

        axios.post(getOneCalendar,calendar_id)
            .then(res => {
                console.log(res.data)
                const eventData = {
                    ...res.data
                    ,id: calendar_id
                    ,start: new Date(res.data.start)//KST
                    ,newStart: kstNewStartDate  //새로 바꿀 시작일 정보. UTC --(-9h)--> KST
                    //end 데이터가 있는 경우에만 값을 넣음
                    ,...(res.data.end != null && { end: new Date(res.data.end) })
                    ,...(res.data.end != null && { newEnd: kstNewEndDate })
                };
                setEvent(eventData)//console.log(res.data
            }).catch(err=> console.log(err))
    }

    // //event 변화할 때마다 콘솔 찍기
    // useEffect(() => {
    //     console.log(event);
    // }, [event]);

    return (
        <div>
            <div>{member_id}님의 캘린더</div>
            <div className='fullCalendar'>
                <FullCalendar
                    initialView={'dayGridMonth'}    /* 맨 처음에 보여주는 캘린더 유형. 월간 달력.*/
                    plugins={[dayGridPlugin,timeGridPlugin,interactionPlugin]}  /* interacton 필수. select 관련 정보 처리.*/
                    headerToolbar={                                             //달력 위에 어떤 걸, 어디에 놓을 지.
                        {
                            start: 'dayGridMonth,timeGridWeek,timeGridDay,today',
                            center: 'title',            //yyyy년mm월dd일
                            end: 'prev,next'
                        }
                    }
                    //height={"85vh"}
                    dateClick={dateClick}//날짜를 클릭했을 때 실행
                    selectable={true}//사용자가 일정 범위를 선택하여 이벤트를 추가
                    selectMirror={true} // 이벤트를 추가할 때 선택한 영역을 표시
                    select={dateSelect}//사용자가 일정범위를 선택한 경우 실행
                    events={events}//일정 리스트
                    editable={true}//이벤트의 드래그 앤 드롭, 리사이징, 이동
                    eventDurationEditable={true} //이벤트 생명주기
                    eventResizableFromStart={true}//이벤트의 시작부분 리사이즈 가능여부
                    eventResize={onDragEvent}//이벤트를 리사이즈(allDay = true만 가능)한 경우 실행
                    eventChange={onDragEvent}//이벤트의 드래그 앤 드롭 시 실행
                    locale='ko'//언어: 한국어
                    eventClick={onDragEvent}//이벤트 클릭시 실행
                    eventBackgroundColor={'pink'}//이벤트의 배경색. 디폴트 값.
                    eventBorderColor={'pink'}// 이벤트의 테두리 색. 달력 배경색과 동일하게 설정.
                    //timeZone="UTC" //캘린더의 시간대. 쓰면 좋아 보이나 이미 없는 상태에서 로직 짜서 빼겠습니다. 타임존 사용 시 로직 뒤집어야 함.
                    datesSet={dateSet}//현재 보고 있는 달력의 시작 ~ 종료일
                    weekends={true} // 주말 표시 여부
                    navLinks={true} // 달력의 날짜 클릭시 일간 스케줄로 이동
                    navLinkHint={"클릭시 해당 날짜로 이동합니다."} // 날짜에 호버 시 문구. 필요하면 nn일로 이동합니다. 문구를 출력할 수 있음.
                    dayMaxEvents= {true}//하루에 너무 많은 일정이 있으면 +more로 표시. 깔끔한 캘린더 디자인을 위함.
                    nowIndicator={true} //현재 시간을 표시하는 인디케이터를 활성화. 이건 아직 잘 모르겠음.
                />
            </div>

            {/* 보여줄까말까  /   닫을까말까   /   일정 작성을 위한 정보 저장소 /   event 정보 수정 /   작성 완료 시 리액트 화면 캘린더 리스트 갱신  /   event 정보 수정     */}
            <WriteCalendar show={showModalState}  onClose={() => setShowModalState(false)}  event={event} onEvent={onEvent} getCalendar={getCalendar} setEvent = {setEvent}>
                일정 작성하는 폼 불러올게
            </WriteCalendar>
            {/* 보여줄까말까  /   닫을까말까   /   일정 수정을 위한 정보 저장소 /   event 정보 수정 /   작성 완료 시 리액트 화면 캘린더 리스트 갱신  /   event 정보 수정     */}
            <UpdateDeleteCalendar show={showUDModalState}  onClose={() => setShowUDModalState(false) } event={event} onEvent={onEvent} getCalendar={getCalendar} setEvent={setEvent} >
                일정 수정 및 삭제하는 폼 부를게
            </UpdateDeleteCalendar>



            <h4><a href='https://fullcalendar.io/docs'>풀캘린더</a> + 리액트</h4>
            <p>
                Open API로 빠르게 해봅시다.
                우선은 dayGridPlugin로 월별 달력을 가져옵니다.
            </p>
            <ol>
                <li>
                    풀캘린더 모듈 설치 - npm은 오류가 잦아 yarn으로 함.<br/><br/>
                    npm install --save @fullcalendar/react @fullcalendar/core @fullcalendar/daygrid<br/>
                    npm install style-loader css-loader sass-loader node-sass --save<br/>

                    yarn add @fullcalendar/react @fullcalendar/core @fullcalendar/daygrid<br/>
                    yarn add style-loader css-loader sass-loader node-sass --save
                </li>
                <li>
                    {"<FullCalendar>"+"를만든다" }<br/>
                    plugins: 월별/<br/>
                    headerToolbar: 달력 헤더 버튼 생성. 오늘 날짜로 이동(이번달)/제목/이전이후이동버튼<br/>
                    dateClick:
                    events: 달력에 적은 일정 정보
                    locale: 언어정보
                </li>
            </ol>
        </div>
    );
};

export default Calendar;