package rocks.learnercouncil.bungeewhitelist;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class BungeewlCommand extends Command implements TabExecutor {

    private String name;

    public BungeewlCommand() {
        super("bungeewl", "bungeewl.command");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("bungeewl.command")) {
            if(args.length >= 2)
                name = args[1];
            String prefix = "§3[BungeeWhitelist] §b";
            switch(args[0]) {
                case "on":
                    if(!BungeeWhitelist.enabled) {
                        BungeeWhitelist.enabled = true;
                        sender.sendMessage(new ComponentBuilder(prefix + "Whitelist enabled.").create());
                        return;
                    }
                    sender.sendMessage(new ComponentBuilder(prefix + "Whitelist already enabled").create());
                    return;
                case "off":
                    if(BungeeWhitelist.enabled) {
                    BungeeWhitelist.enabled = false;
                    sender.sendMessage(new ComponentBuilder(prefix + "Whitelist disabled.").create());
                    return;
                }
                sender.sendMessage(new ComponentBuilder(prefix + "Whitelist already disabled").create());
                return;
                case "add":
                    if(args.length != 2) return;
                    BungeeWhitelist.players.add(name);
                    sender.sendMessage(new ComponentBuilder(prefix + "Added " + args[1] + " to the whitelist.").create());
                    return;
                case "remove":
                    if(args.length != 2) return;
                    BungeeWhitelist.players.remove(name);
                    sender.sendMessage(new ComponentBuilder(prefix + "Removed " + args[1] + " from the whitelist.").create());
                    return;
                case "list":
                    ComponentBuilder list = new ComponentBuilder("§3Players: \n");
                    for(String name : BungeeWhitelist.players) {
                        list.append(name).append(", ");
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
