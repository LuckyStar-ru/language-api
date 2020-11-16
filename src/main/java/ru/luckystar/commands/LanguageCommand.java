package ru.luckystar.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import ru.luckystar.LangAPI;

public class LanguageCommand extends Command {

    public LanguageCommand() {
        super("language", "", "lang");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (args.length == 1) {

                if (LangAPI.getInst().updatePlayerLang(player.getUniqueId(), args[0])) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            LangAPI.getInst().getMessage(args[0].toLowerCase(), "lang")));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            LangAPI.getInst().getMessage(args[0].toLowerCase(), "not-a-language")));
                }

            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        LangAPI.getInst().getMessage("en", "change-language")));
            }
        }
    }

}
