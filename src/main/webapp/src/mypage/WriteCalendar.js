import React, {useEffect, useState} from 'react';
import axios from "axios";

const WriteCalendar = ({ show, onClose, event, onEvent, getCalendar, setEvent }) => {
    const calendarSubmitURL ='http://localhost:8080/calendar/writecalendar';
    const [allDay, setAllDay] = useState(false);

    useEffect(() => {
        setAllDay(event.allDay || false); // event가 변경될 때 allDay 상태 설정
    },[show, event]);
    if (!show) {
        return null;
    }
    const onSubmit = () => {
        console.log('제출 전, 정보 확인: ',event)
        axios.post(calendarSubmitURL,event)
            .then(res => {
                console.log('제출성공'+res)
                getCalendar()//갱신된 일정 보여주기 위함. 나중에 수정해야...

                setEvent(preData => ({
                    calendar_memberId: preData.calendar_memberId
                    ,allDay:false
                }))

            })
            .catch(err => console.log('제출오류'+err))

        //setEvent(null)
    }
    const onStartTime = (e) => {
        const [hours, minutes] = e.target.value.split(':');
        const newStart = new Date(event.start);
        newStart.setHours(hours);
        newStart.setMinutes(minutes);
        console.log(newStart)
        setEvent({ ...event, start: newStart });
    }
    const onEndTime = (e) => {
        const [hours, minutes] = e.target.value.split(':');
        const newStart = new Date(event.end);
        newStart.setHours(hours);
        newStart.setMinutes(minutes);
        console.log(newStart)
        setEvent({ ...event, end: newStart });
    }
    const handleAllDayChange = (e) => {
        const isChecked = e.target.checked;
        setAllDay(isChecked);
        setEvent({ ...event, allDay: isChecked });
    };
    return (
        <div className="modal" onClick={onClose}>
            <div className="modal-content" onClick={e => e.stopPropagation()}>
                <div className="modal-header">
                    <button onClick={onClose}>&times;모달닫기</button>
                </div>
                <div className="modal-body">
                    칠드런<br/>
                    <p>
                        일정 시작일: {new Date(event.start).toLocaleString()}
                        시간: <input type='time'  onChange={onStartTime} name='eventStartTime'/>
                    </p>
                    <p>일정 마감일: {new Date(event.end).toLocaleString()} 시간:
                        <input type='time' onChange={onEndTime} name='eventEndTime' />
                    </p>
                    
                    <input type='checkbox' onChange={handleAllDayChange} checked={allDay} name='allDay'/>allDay 체크여부
                    <input type='text' onChange={onEvent} name='title' value={event.title} placeholder='제목'/>
                    <input type='text' onChange={onEvent} name='content' value={event.content} placeholder='내용'/>

                    <input type='radio' onChange={onEvent} name='backgroundColor' value='skyblue' checked={event.backgroundColor === 'skyblue'}/>파랑
                    <input type='radio' onChange={onEvent} name='backgroundColor' value='orange' checked={event.backgroundColor === 'orange'}/>오렌지
                    <input type='text' onChange={onEvent} name='backgroundColor' value={event.backgroundColor}  placeholder='색상 알아서 잘 넣어보도록. 나중에는 체크박스나 다른 기능 필요'/>
            </div>
            <div className="modal-footer">
                <button type='button' onClick={onSubmit}>제출하기</button>
            </div>
        </div>
    </div>
)
    ;
};

export default WriteCalendar;