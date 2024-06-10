package com.together.common.security.jwt;

import com.together.common.security.jwt.bean.AuthenticationRequest;
import com.together.common.security.jwt.bean.JwtToken;
import org.springframework.security.core.Authentication;

public interface JwtTokenProvider {
    //accessToken과 refreshToken 생성
    public JwtToken generateToken(Authentication authentication);
    //토큰 정보 추출 = 복호화
    public Authentication getAuthentication(String accessToken);
    //토큰 정보 검증
    public boolean validateToken(String token);
}
