package ru.luckystar;

import java.util.HashMap;
import java.util.Map;

public class LangConfig {

    private Map<String, Map<String, String>> langs = new HashMap<>();

    public void addLang(String lang, HashMap<String, String> map) {
        this.langs.put(lang, (Map<String, String>) map.clone());
    }

    public Map<String, String> getLang(String lang) {
        return this.langs.get(lang);
    }

    public String getLangMessage(String lang, String message) {
        return this.langs.get(lang).getOrDefault(message, " §c<Error> ");
    }

}