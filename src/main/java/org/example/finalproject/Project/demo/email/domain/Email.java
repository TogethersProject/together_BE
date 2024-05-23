package org.example.finalproject.Project.demo.email.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Random;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Email {

    @Id
    @GeneratedValue
    private Long id ;
    @NonNull
    private String email ;
    @NonNull
    private String code ;
    @NonNull
    private String verifivation ;
    private String expire_at;




    // ====== 서비스 로직 ====== //

//    public String createCode() {
//        char[] chars = {
//                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
//                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
//                'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
//                'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
//                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
//                'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
//                '8', '9', '!', '@', '$'
//        };
//        Random random = new Random();
//        StringBuilder key = new StringBuilder();
//        int codeLength = 10;
//        for (int i = 0; i < codeLength; i++) {
//            int index = random.nextInt(chars.length);
//            key.append(chars[index]);
//        }
//        return key.toString();
//    }
//
//    // 이메일 형식을 생성하는 메서드
//    public MimeMessage createEmailForm(String MEM_EMAIL, String authstr) throws MessagingException {
//
//        String setFrom = "inhwan3596@gmail.com"; // 보내는 사람의 이메일 주소
//        String title = "나들이 회원가입 인증 코드"; // 메일 제목
//
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//
//        helper.setFrom(setFrom); // 보내는 사람 설정
//        helper.setTo(MEM_EMAIL); // email
//        helper.setSubject(title); // 제목 설정
//
//        // 메일 내용 설정
//        String msgOfEmail = "<div style='margin:20px;'>"
//                + "<h1> 회원가입 인증 코드 입니다 . . </h1>"
//                + "<br>"
//                + "<p>아래 코드를 입력해주세요<p>"
//                + "<br>"
//                + "<p>감사합니다.<p>"
//                + "<br>"
//                + "<div align='center' style='border:1px solid black; font-family:verdana';>"
//                + "<h3 style='color:blue;'>나들이 인증 코드입니다.</h3>"
//                + "<div style='font-size:130%'>"
//                + "CODE : <strong>" + authstr + "</strong><div><br/> "
//                + "</div>";
//
//        helper.setText(msgOfEmail, true); // 메일 내용을 HTML 형식으로 설정
//        return message;
//    }

}
