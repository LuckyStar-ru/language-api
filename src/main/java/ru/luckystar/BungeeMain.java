package ru.luckystar;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import ru.luckystar.commands.LanguageCommand;
import ru.luckystar.commands.TestLanguageCommand;
import ru.luckystar.events.JoinHadler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;

public class BungeeMain extends Plugin {

    /* TODO: solve SLF4J warn */
    @Override
    public void onEnable() {
        DatabaseRepository dbRepo = null;
        try {
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(getConfigFile());
            /* === DataBase init === */
            dbRepo = new DatabaseRepository(
                    this.getDataFolder().getAbsolutePath(),
                    config.getString("database.type"),
                    config.getString("database.ipWithPort") + "/" + config.getString("database.tableName"),
                    config.getString("database.username"),
                    config.getString("database.password"),
                    config.getString("defaultlanguage")
            );
            /* === Custom Config init === */
            LangConfig langConfig = new LangConfig();
            config.getSection("langs").getKeys().forEach(lang -> {
                HashMap<String, String> temp = new HashMap<>();
                config.getSection("langs." + lang).getKeys().forEach(key -> {
                    temp.put(key, config.getString("langs." + lang + "." + key));
                });
                langConfig.addLang(lang, temp);
            });
            /* === API init === */
            LangAPI.init(dbRepo, langConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }

        getProxy().getPluginManager().registerCommand(this, new LanguageCommand());
        getProxy().getPluginManager().registerCommand(this, new TestLanguageCommand());
        getProxy().getPluginManager().registerListener(this, new JoinHadler(dbRepo));
    }

    public File getConfigFile() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
