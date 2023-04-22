package com.magic.demo.aspect;

import com.magic.demo.anno.TranslateField;
import com.magic.demo.entity.Greet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class TranslateAspect {

    private final MessageSource messageSource;

    @Around("@within(org.springframework.web.bind.annotation.RestController) && (@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping))")
    public Object translateFields(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result =joinPoint.proceed();
        if(result instanceof Greet){
            Field[] declaredFields = result.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Class<?> type = declaredField.getType();
                declaredField.setAccessible(true);
                Object fieldValue = declaredField.get(result);
                if (declaredField.isAnnotationPresent(TranslateField.class) && fieldValue instanceof String) {
                    TranslateField annotation = declaredField.getAnnotation(TranslateField.class);
                    String s = annotation.lookupFieldPath();
                    String message = messageSource.getMessage(s, null, LocaleContextHolder.getLocale());
                    declaredField.set(result, message);
                }

            }
        }
        return result;
    }
}
