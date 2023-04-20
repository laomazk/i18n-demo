package commagic.demo.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import commagic.demo.entity.MutiLang;
import commagic.demo.reposity.MutiLangReposity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Component("messageSource")
@Slf4j
public class MyMessageSource extends AbstractMessageSource implements ResourceLoaderAware,MyMessageComponent {
    private ResourceLoader resourceLoader;
    private static final Map<String, Map<String, String>> LOCAL_CACHE = new ConcurrentHashMap<>(256);
    private final MutiLangReposity mutiLangReposity;


    public Map<String, Map<String, String>> loadAllMessageResourcesFromDB() {
        List<MutiLang> all = mutiLangReposity.findAll();
        if (CollUtil.isNotEmpty(all)) {
            final Map<String, String> zhCnMessageResources = new HashMap<>(all.size());
            final Map<String, String> enUsMessageResources = new HashMap<>(all.size());
            for (MutiLang mutiLang : all) {
                String name = mutiLang.getModel() + "." + mutiLang.getName();
                String zhText = mutiLang.getZhCN();
                String enText = mutiLang.getEnUS();
                zhCnMessageResources.put(name, zhText);
                enUsMessageResources.put(name, enText);
            }
            LOCAL_CACHE.put("zh", zhCnMessageResources);
            LOCAL_CACHE.put("en", enUsMessageResources);
        }
        return MapUtil.empty();
    }

    public String getSourceFromCache(String code, Locale locale) {
        String language = locale.getLanguage();
        Map<String, String> props = LOCAL_CACHE.get(language);
        if (null != props && props.containsKey(code)) {
            return props.get(code);
        } else {
            try {
                if (null != this.getParentMessageSource()) {
                    return this.getParentMessageSource().getMessage(code, null, locale);
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
            return code;
        }
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = getSourceFromCache(code, locale);
        return new MessageFormat(msg, locale);
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return getSourceFromCache(code, locale);
    }

    @PostConstruct
    public void init() {
        this.reload();
    }

    @Override
    public void reload() {
        LOCAL_CACHE.clear();
        LOCAL_CACHE.putAll(loadAllMessageResourcesFromDB());
    }
}
