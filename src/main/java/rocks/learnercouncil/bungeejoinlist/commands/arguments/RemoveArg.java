package rocks.learnercouncil.bungeejoinlist.commands.arguments;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import rocks.learnercouncil.bungeejoinlist.commands.CommandArgument;
import rocks.learnercouncil.bungeejoinlist.commands.CommandResult;
import rocks.learnercouncil.bungeejoinlist.data.PlayerData;

import java.util.Collections;
import java.util.List;

public class RemoveArg implements CommandArgument {
    @Override
    public BaseComponent[] execute(CommandSender sender, String[] args) {
        if(!args[0].equalsIgnoreCase("remove")) return CommandResult.NONE;
        if(args.length < 2) return CommandResult.TOO_FEW_ARGS;
        if(args.length > 2) return CommandResult.TOO_MANY_ARGS;

        String username = args[1];
        if(!PlayerData.get(username).isPresent()) return CommandResult.notAdded(username);
        PlayerData.get(username).get().remove();
        return CommandResult.removed(username);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length == 1) return Collections.singletonList("remove");
        return Collections.emptyList();
    }
}
