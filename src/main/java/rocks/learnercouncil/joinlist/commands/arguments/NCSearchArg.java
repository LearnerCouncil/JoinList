package rocks.learnercouncil.joinlist.commands.arguments;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import rocks.learnercouncil.joinlist.commands.CommandArgument;
import rocks.learnercouncil.joinlist.commands.CommandResult;
import rocks.learnercouncil.joinlist.data.NameChange;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NCSearchArg implements CommandArgument {
    @Override
    public BaseComponent[] execute(CommandSender sender, String[] args) {
        if(!args[0].equalsIgnoreCase("ncsearch")) return CommandResult.NONE;
        if(!sender.hasPermission("bungeejl.viewnamechanges")) return CommandResult.NO_PERMISSION;
        if(!(sender instanceof ProxiedPlayer)) return CommandResult.ONLY_PLAYER;
        if(args.length < 2) return CommandResult.TOO_FEW_ARGS;
        ProxiedPlayer player = (ProxiedPlayer) sender;

        String query = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        return CommandResult.listChanges(NameChange.getNameChanges(player.getUniqueId(), false), NameChange.getNameChanges(player.getUniqueId(), true), query);

    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(!sender.hasPermission("bungeejl.viewnamechanges")) return Collections.emptyList();
        if(args.length == 1) return Collections.singletonList("ncsearch");
        return Collections.emptyList();
    }
}
