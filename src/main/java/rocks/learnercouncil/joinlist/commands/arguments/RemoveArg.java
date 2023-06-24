/*
 * This file is part of JoinList - https://github.com/LearnerCouncil/Joinlist
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

package rocks.learnercouncil.joinlist.commands.arguments;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import rocks.learnercouncil.joinlist.commands.CommandArgument;
import rocks.learnercouncil.joinlist.commands.CommandResult;
import rocks.learnercouncil.joinlist.data.JavaPlayer;
import rocks.learnercouncil.joinlist.data.PlayerData;

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
