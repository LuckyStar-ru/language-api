package ru.luckystar.events;

import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import ru.luckystar.DatabaseRepository;
import ru.luckystar.LangAPI;

public class JoinHadler implements Listener {

    DatabaseRepository dbRepo;

    public JoinHadler(DatabaseRepository dbRepo) {
        this.dbRepo = dbRepo;
    }

    @EventHandler
    public void onPlayerJoin(PostLoginEvent event) {
        this.dbRepo.savePlayer(event.getPlayer().getUniqueId());
    }
}
