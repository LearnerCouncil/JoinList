package rocks.learnercouncil.bungeejoinlist;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import rocks.learnercouncil.bungeejoinlist.data.NameChange;
import rocks.learnercouncil.bungeejoinlist.data.PlayerData;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static net.md_5.bungee.api.ChatColor.*;

public class BungeejlCommand extends Command implements TabExecutor {

    private String name;

    public BungeejlCommand() {
        super("bungeejl", "bungeejl.command", "joinlist");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("bungeejl.command")) {
            if(args.length >= 2)
                name = args[1];
            String prefix = DARK_AQUA + "[BungeeJoinlist] " + AQUA;
            if(args.length < 1) return;
            switch(args[0].toLowerCase()) {
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

                    if(!RequestHandler.requestUUID(name)) {
                        sender.sendMessage(new ComponentBuilder(prefix + RED + "Could not find player with name: " + name).create());
                        return;
                    }
                    sender.sendMessage(new ComponentBuilder(prefix + "Added " + args[1] + " to the Joinlist.").create());
                    return;
                case "remove":
                    if(args.length != 2) return;
                    if(PlayerData.get(name).isPresent())
                    PlayerData.players.remove(PlayerData.get(name).get());
                    sender.sendMessage(new ComponentBuilder(prefix + "Removed " + args[1] + " from the Joinlist.").create());
                    return;
                case "list":
                    ComponentBuilder list = new ComponentBuilder(DARK_AQUA + "Players: \n");
                    for(PlayerData data : PlayerData.players) {
                        list.append(data.getName()).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(data.id.toString())));
                        if(PlayerData.players.indexOf(data) != PlayerData.players.size() - 1)
                            list.append(", ");
                    }
                    sender.sendMessage(list.create());
            }
        }
        if(sender.hasPermission("bungeejl.viewnamechanges")) {
            List<NameChange> changes = new LinkedList<>();
            if(sender instanceof ProxiedPlayer)
                changes.addAll(NameChange.getUnseenNameChanges(((ProxiedPlayer) sender).getUniqueId()));
            changes.addAll(NameChange.getNameChanges().stream().filter(c -> {
                if(sender instanceof ProxiedPlayer)
                    return !NameChange.getUnseenNameChanges(((ProxiedPlayer) sender).getUniqueId()).contains(c);
                return false;
            }).collect(Collectors.toList()));
            ComponentBuilder changeList = new ComponentBuilder(DARK_AQUA + "[BungeeJoinlist] Name Changes: \n");
            if(args.length == 1 && args[0].equalsIgnoreCase("ncview")) {
                for (NameChange change : changes) {
                    changeList.append(change.toString() + "\n").color(AQUA).bold(sender instanceof ProxiedPlayer && !change.shownPlayers.contains(((ProxiedPlayer) sender).getUniqueId()));
                    if(sender instanceof ProxiedPlayer)
                        change.shownPlayers.add(((ProxiedPlayer) sender).getUniqueId());
                }
                sender.sendMessage(changeList.create());
                return;
            }
            if(args.length == 2 && args[0].equalsIgnoreCase("ncsearch")) {
                for(NameChange change : changes) {
                    if (change.toString().toLowerCase().contains(args[1].toLowerCase())) {
                        changeList.append(change + "\n").color(AQUA).bold(sender instanceof ProxiedPlayer && !change.shownPlayers.contains(((ProxiedPlayer) sender).getUniqueId()));
                        if (sender instanceof ProxiedPlayer)
                            change.shownPlayers.add(((ProxiedPlayer) sender).getUniqueId());
                    }
                }
                sender.sendMessage(changeList.create());
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
            addIfPartialMatch(completions, "ncview", args[0]);
            addIfPartialMatch(completions, "ncsearch", args[0]);
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
