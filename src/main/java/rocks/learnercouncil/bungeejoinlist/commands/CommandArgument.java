package rocks.learnercouncil.bungeejoinlist.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.List;

public interface CommandArgument {

    BaseComponent[] execute(CommandSender sender, String[] args);

    List<String> tabComplete(CommandSender sender, String[] args);
}
