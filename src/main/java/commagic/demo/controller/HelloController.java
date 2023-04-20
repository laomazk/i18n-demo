package commagic.demo.controller;

import commagic.demo.component.MyMessageComponent;
import commagic.demo.component.MyMessageSource;
import commagic.demo.entity.MutiLang;
import commagic.demo.reposity.MutiLangReposity;
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
}
