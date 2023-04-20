package commagic.demo.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "muti_lang")
@Getter
@Setter
public class MutiLang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String model;

    private String name;

    private String zhCN;

    private String enUS;
}
