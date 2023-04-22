package com.magic.demo.entity;

import com.magic.demo.anno.TranslateField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Greet {
    private Integer id;
    @TranslateField(lookupFieldPath = "a.aa")
    private String desc;
}
