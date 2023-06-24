package rocks.learnercouncil.joinlist.commands.arguments;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import rocks.learnercouncil.joinlist.Joinlist;
import rocks.learnercouncil.joinlist.commands.CommandArgument;
import rocks.learnercouncil.joinlist.commands.CommandResult;

import java.util.Collections;
import java.util.List;

public class OffArg implements CommandArgument {
    @Override
    public BaseComponent[] execute(CommandSender sender, String[] args) {
        if(!args[0].equalsIgnoreCase("off")) return CommandResult.NONE;
        if(args.length > 1) return CommandResult.TOO_MANY_ARGS;
        if(!Joinlist.enabled) return CommandResult.ALREADY_DISABLED;
        Joinlist.enabled = false;
        return CommandResult.DISABLED;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length == 1) return Collections.singletonList("off");
        return Collections.emptyList();
    }
}
