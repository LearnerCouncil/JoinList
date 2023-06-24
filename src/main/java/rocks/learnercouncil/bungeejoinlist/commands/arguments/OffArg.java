package rocks.learnercouncil.bungeejoinlist.commands.arguments;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import rocks.learnercouncil.bungeejoinlist.BungeeJoinlist;
import rocks.learnercouncil.bungeejoinlist.commands.CommandArgument;
import rocks.learnercouncil.bungeejoinlist.commands.CommandResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OffArg implements CommandArgument {
    @Override
    public BaseComponent[] execute(CommandSender sender, String[] args) {
        if(!args[0].equalsIgnoreCase("off")) return CommandResult.NONE;
        if(args.length > 1) return CommandResult.TOO_MANY_ARGS;
        if(!BungeeJoinlist.enabled) return CommandResult.ALREADY_DISABLED;
        BungeeJoinlist.enabled = false;
        return CommandResult.DISABLED;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length == 1) return Collections.singletonList("off");
        return Collections.emptyList();
    }
}
