package com.example.demo.unPyeongGuShop.controller;

import com.example.demo.unPyeongGuShop.domain.ShopVO;
import com.example.demo.unPyeongGuShop.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/")
    public String findShopList(Model model) { // 회원목록 가져옴
        // 목록 폼
        List<ShopVO> shopList = shopService.findShopList();


        log.info("회원목록요청");    //로그

        model.addAttribute("title", "가게조회");
        model.addAttribute("shopList", shopList);

        return "index";

    }
}
