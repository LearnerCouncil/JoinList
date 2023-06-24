package rocks.learnercouncil.joinlist.commands.arguments;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import rocks.learnercouncil.joinlist.commands.CommandArgument;
import rocks.learnercouncil.joinlist.commands.CommandResult;

import java.util.Collections;
import java.util.List;

public class ListArg implements CommandArgument {
    @Override
    public BaseComponent[] execute(CommandSender sender, String[] args) {
        if(!args[0].equalsIgnoreCase("list")) return CommandResult.NONE;
        if(args.length > 1) return CommandResult.TOO_MANY_ARGS;

        return CommandResult.list();
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length == 1) return Collections.singletonList("list");
        return Collections.emptyList();
    }
}
