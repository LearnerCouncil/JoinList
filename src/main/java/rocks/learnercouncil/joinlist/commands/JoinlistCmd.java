/*
 * This file is part of Joinlist - https://github.com/LearnerCouncil/Joinlist
 * Copyright (c) 2023 ALP Learner Council and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package rocks.learnercouncil.joinlist.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import rocks.learnercouncil.joinlist.commands.arguments.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class JoinlistCmd extends Command implements TabExecutor {

    Set<CommandArgument> arguments = new HashSet<>();

    public JoinlistCmd() {
        super("joinlist", "joinlist.command", "bungeejl");
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
        if(!sender.hasPermission("joinlist.command")) return;
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
