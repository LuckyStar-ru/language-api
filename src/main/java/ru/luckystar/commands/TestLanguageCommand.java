package ru.luckystar.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import ru.luckystar.LangAPI;

public class TestLanguageCommand extends Command {

    public TestLanguageCommand() {
        super("testlanguage", "language.admin", "testlang");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("nonplayer")) {
                sender.sendMessage(LangAPI.getInst().getMessage(args[1], args[2]));
            } else if (args[0].equalsIgnoreCase("player")) {
                if (ProxyServer.getInstance().getPlayer(args[1]) == null) {
                    sender.sendMessage("§cИгрок должен быть онлайн!");
                } else {
                    sender.sendMessage(LangAPI.getInst().getLangMessage(ProxyServer.getInstance().getPlayer(args[1]).getUniqueId(), args[2]));
                }
            } else {
                sender.sendMessage("Использование: \n/testlanguage nonplayer язык путь_из_конфига\n/testlanguage player ник путь_из_конфига_без_языка");
            }
        } else {
            sender.sendMessage("Использование: \n/testlanguage nonplayer язык путь_из_конфига\n/testlanguage player ник путь_из_конфига_без_языка");
        }
    }
}
