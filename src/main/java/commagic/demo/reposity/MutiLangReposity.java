package commagic.demo.reposity;

import commagic.demo.entity.MutiLang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MutiLangReposity extends JpaRepository<MutiLang,Integer> {
}
