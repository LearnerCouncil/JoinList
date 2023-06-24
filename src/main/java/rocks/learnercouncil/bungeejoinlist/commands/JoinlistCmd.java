package rocks.learnercouncil.bungeejoinlist.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import rocks.learnercouncil.bungeejoinlist.commands.arguments.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class JoinlistCmd extends Command implements TabExecutor {

    Set<CommandArgument> arguments = new HashSet<>();

    public JoinlistCmd() {
        super("joinlist", "bungeejl.command", "bungeejl");
        initalizeArguments();
    }

    private void initalizeArguments() {
        arguments.add(new OnArg());
        arguments.add(new OffArg());
        arguments.add(new AddArg());
        arguments.add(new RemoveArg());
        arguments.add(new ListArg());
        arguments.add(new NCListArg());
        arguments.add(new NCSearchArg());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("bungeejl.command")) return;
        if(args.length < 1) {
            sender.sendMessage(CommandResult.TOO_FEW_ARGS);
            return;
        }

        for (CommandArgument argument : arguments) {
            BaseComponent[] result = argument.execute(sender, args);
            if (result.length != 0) {
                sender.sendMessage(result);
                return;
            }
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return arguments.stream()
                .flatMap(a -> a.tabComplete(sender, args).stream())
                .filter(a -> a.toLowerCase().startsWith(args[args.length-1].toLowerCase()))
                .collect(Collectors.toSet());
    }
}
