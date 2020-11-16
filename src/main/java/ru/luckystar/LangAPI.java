package ru.luckystar;

import java.util.UUID;

public class LangAPI {

    private static LangAPI langAPI;
    private DatabaseRepository dbRepo;
    private LangConfig config;

    private LangAPI(DatabaseRepository dataBase, LangConfig config) {
        this.dbRepo = dataBase;
        this.config = config;
    }

    static void init(DatabaseRepository dbRepo, LangConfig config) {
        langAPI = new LangAPI(dbRepo, config);
    }

    public static LangAPI getInst() {
        return langAPI;
    }

    public boolean updatePlayerLang(UUID uuid, String lang) {
        return this.config.getLangs().contains(lang.toLowerCase()) ? this.dbRepo.updatePlayer(uuid, lang.toLowerCase()) : false;
    }

    public String getMessage(String lang, String path) {
        return this.config.getLangMessage(lang, path);
    }

    public String getLangMessage(UUID uuid, String path) {
        return this.config.getLangMessage(this.dbRepo.getPlayerLang(uuid), path);
    }

}