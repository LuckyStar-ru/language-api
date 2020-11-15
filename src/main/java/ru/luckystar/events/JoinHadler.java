package ru.luckystar.events;

import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import ru.luckystar.LangAPI;

public class JoinHadler implements Listener {

    @EventHandler
    public void onPlayerJoin(PostLoginEvent event) {
        LangAPI.getInst().getDatabase().savePlayer(event.getPlayer().getUniqueId());
    }
}
