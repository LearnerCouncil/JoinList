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

package rocks.learnercouncil.joinlist;

import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import rocks.learnercouncil.joinlist.commands.JoinlistCmd;
import rocks.learnercouncil.joinlist.data.JavaPlayer;
import rocks.learnercouncil.joinlist.data.NameChange;
import rocks.learnercouncil.joinlist.data.PlayerData;
import rocks.learnercouncil.joinlist.events.Login;
import rocks.learnercouncil.joinlist.events.PostLogin;

import java.util.List;
import java.util.stream.Collectors;

public final class Joinlist extends Plugin {

    @Getter private static Joinlist plugin;

    public static boolean enabled;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        //
        enabled = ConfigHandler.config.getBoolean("enabled");
        ConfigHandler.config.getStringList("JAVA_PLAYERS").forEach(PlayerData::deserialize);
        NameChange.deserialize();
        getLogger().info("Loaded Joinlist.");
        getProxy().getPluginManager().registerListener(this, new Login());
        getProxy().getPluginManager().registerListener(this, new PostLogin());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new JoinlistCmd());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        List<String> playerList = PlayerData.players.stream().map(PlayerData::serialize).collect(Collectors.toList());
        ConfigHandler.config.set("JAVA_PLAYERS", playerList);
        ConfigHandler.config.set("enabled", enabled);
        NameChange.serialize();
        ConfigHandler.saveConfig();
    }
}
