package rocks.learnercouncil.bungeejoinlist.commands.arguments;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import rocks.learnercouncil.bungeejoinlist.RequestHandler;
import rocks.learnercouncil.bungeejoinlist.commands.CommandArgument;
import rocks.learnercouncil.bungeejoinlist.commands.CommandResult;
import rocks.learnercouncil.bungeejoinlist.data.PlayerData;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AddArg implements CommandArgument {
    @Override
    public BaseComponent[] execute(CommandSender sender, String[] args) {
        if(!args[0].equalsIgnoreCase("add")) return CommandResult.NONE;
        if(args.length < 2) return CommandResult.TOO_FEW_ARGS;
        if(args.length > 2) return CommandResult.TOO_MANY_ARGS;

        String username = args[1];
        Optional<PlayerData> playerData = RequestHandler.requestUUID(username);
        if(!playerData.isPresent()) return CommandResult.notFound(username);
        playerData.get().add();
        return CommandResult.added(playerData.get().getName());
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length == 1) return Collections.singletonList("add");
        return Collections.emptyList();
    }
}
