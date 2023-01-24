package rocks.learnercouncil.bungeejoinlist;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.md_5.bungee.api.ChatColor.*;

public class BungeejlCommand extends Command implements TabExecutor {

    private String name;

    public BungeejlCommand() {
        super("bungeejl", "bungeejl.command");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("bungeejl.command")) {
            if(args.length >= 2)
                name = args[1];
            String prefix = DARK_AQUA + "[BungeeJoinlist] " + AQUA;
            if(args.length < 1) return;
            switch(args[0]) {
                case "on":
                    if (BungeeJoinlist.enabled) {
                        sender.sendMessage(new ComponentBuilder(prefix + RED + "Joinlist already enabled").create());
                        return;
                    }
                    BungeeJoinlist.enabled = true;
                    sender.sendMessage(new ComponentBuilder(prefix + "Joinlist enabled.").create());
                    return;
                case "off":
                    if (!BungeeJoinlist.enabled) {
                        sender.sendMessage(new ComponentBuilder(prefix + RED + "Joinlist already disabled").create());
                        return;
                    }
                    BungeeJoinlist.enabled = false;
                    sender.sendMessage(new ComponentBuilder(prefix + "Joinlist disabled.").create());
                    return;
                case "add":
                    if(args.length != 2) return;
                    Optional<PlayerData> playerData = RequestManager.requestUUID(name);
                    if(!playerData.isPresent()) {
                        sender.sendMessage(new ComponentBuilder(prefix + RED + "Could not find player with name: " + name).create());
                        return;
                    }
                    BungeeJoinlist.players.add(playerData.get());
                    sender.sendMessage(new ComponentBuilder(prefix + "Added " + args[1] + " to the Joinlist.").create());
                    return;
                case "remove":
                    if(args.length != 2) return;
                    if(PlayerData.get(name).isPresent())
                    BungeeJoinlist.players.remove(PlayerData.get(name).get());
                    sender.sendMessage(new ComponentBuilder(prefix + "Removed " + args[1] + " from the Joinlist.").create());
                    return;
                case "list":
                    ComponentBuilder list = new ComponentBuilder("§3Players: \n");
                    for(PlayerData data : BungeeJoinlist.players) {
                        list.append(data.name).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(data.id)));
                        if(BungeeJoinlist.players.indexOf(data) != BungeeJoinlist.players.size() - 1)
                            list.append(", ");
                    }
                    sender.sendMessage(list.create());
            }
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<>();
        if(args.length == 1) {
            addIfPartialMatch(completions, "on", args[0]);
            addIfPartialMatch(completions, "off", args[0]);
            addIfPartialMatch(completions, "add", args[0]);
            addIfPartialMatch(completions, "remove", args[0]);
            addIfPartialMatch(completions, "list", args[0]);
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                completions.add("<player>");
            }
        }
        return completions;
    }

    private void addIfPartialMatch(List<String> list, String entry, String match) {
        if(entry.startsWith(match))
            list.add(entry);
    }
}
