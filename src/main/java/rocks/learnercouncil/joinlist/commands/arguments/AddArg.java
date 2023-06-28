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

package rocks.learnercouncil.joinlist.commands.arguments;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import rocks.learnercouncil.joinlist.Joinlist;
import rocks.learnercouncil.joinlist.commands.CommandArgument;
import rocks.learnercouncil.joinlist.commands.CommandResult;
import rocks.learnercouncil.joinlist.data.BedrockPlayer;
import rocks.learnercouncil.joinlist.data.JavaPlayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AddArg implements CommandArgument {
    private static final Joinlist plugin = Joinlist.getPlugin();

    @Override
    public BaseComponent[] execute(CommandSender sender, String[] args) {
        if(!args[0].equalsIgnoreCase("add")) return CommandResult.NONE;
        if(args.length < 2) return CommandResult.TOO_FEW_ARGS;
        if(args.length > 2) return CommandResult.TOO_MANY_ARGS;

        String username = args[1];
        if(username.startsWith("+")) {
            new BedrockPlayer(username).add();
            return CommandResult.added(username);
        }
        Optional<JavaPlayer> javaPlayer = requestUUID(username);
        if(!javaPlayer.isPresent()) return CommandResult.notFound(username);
        javaPlayer.get().add();
        return CommandResult.added(javaPlayer.get().getName());
    }

    public Optional<JavaPlayer> requestUUID(String username) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());
            BufferedReader in = new BufferedReader(inputStreamReader);

            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(in);
            String uuid = jsonObject.get("id").getAsString().replaceAll("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");
            String name = jsonObject.get("name").getAsString();
            in.close();

            return Optional.of(new JavaPlayer(name, UUID.fromString(uuid)));
        } catch (Exception e) {
            plugin.getLogger().severe("Could net get UUID of " + username);
            return Optional.empty();
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length == 1) return Collections.singletonList("add");
        return Collections.emptyList();
    }
}
