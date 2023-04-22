package com.magic.demo.controller;

import com.magic.demo.entity.Greet;
import com.magic.demo.entity.MutiLang;
import com.magic.demo.reposity.MutiLangReposity;
import com.magic.demo.component.MyMessageComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HelloController {
    private final MessageSource messageSource;
    private final MutiLangReposity mutiLangReposity;
    private final MyMessageComponent myMessageComponent;

    @GetMapping("hello")
    public String hello() {
        return messageSource.getMessage("a.aa", null, LocaleContextHolder.getLocale());
    }

    @GetMapping("list")
    public List<MutiLang> list(){
        return mutiLangReposity.findAll();
    }

    @GetMapping("reload")
    public void init(){
        myMessageComponent.reload();
    }

    @GetMapping("greet")
    public Greet greet(){
        Greet greet = new Greet();
        greet.setId(1);
        greet.setDesc("你好");
        return greet;
    }
}
