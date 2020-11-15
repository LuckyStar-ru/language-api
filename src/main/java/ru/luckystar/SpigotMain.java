package ru.luckystar;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class SpigotMain extends JavaPlugin {

    @Override
    public void onEnable() {
        FileConfiguration config = getConfig();
        DatabaseRepository dbRepo = new DatabaseRepository(
                this.getDataFolder().getAbsolutePath(),
                config.getString("database.type"),
                config.getString("database.ipWithPort") + "/" + config.getString("database.tableName"),
                config.getString("database.username"),
                config.getString("database.password"),
                config.getString("defaultlanguage")
        );
        /* === Custom Config init === */
        LangConfig langConfig = new LangConfig();
        HashMap<String, String> temp = new HashMap<>();
        config.getConfigurationSection("langs").getKeys(false).forEach(lang -> {
            config.getConfigurationSection("langs." + lang).getKeys(false).forEach(key -> {
                temp.put(key, config.getString("langs." + lang + "." + key));
            });
            langConfig.addLang(lang, temp);
            temp.clear();
        });
        /* ===                    === */
        LangAPI.init(dbRepo, langConfig);
    }
}
