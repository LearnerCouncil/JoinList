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

package rocks.learnercouncil.joinlist.events;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import rocks.learnercouncil.joinlist.Joinlist;
import rocks.learnercouncil.joinlist.data.NameChange;
import rocks.learnercouncil.joinlist.data.PlayerData;

import java.text.SimpleDateFormat;
import java.util.*;

public class PostLogin implements Listener {

    @EventHandler
    public void onPostLogin(PostLoginEvent e) {
        if(!Joinlist.enabled) return;
        ProxiedPlayer player = e.getPlayer();
        UUID uuid = e.getPlayer().getUniqueId();
        handleNameChange(player, uuid);
        if(!player.hasPermission("bungeejl.viewnamechanges")) return;
        List<NameChange> unseenNameChanges = NameChange.getNameChanges(uuid, false);
        if(unseenNameChanges.size() <= 0) return;

        player.sendMessage(new ComponentBuilder(ChatColor.DARK_AQUA + "[JoinList] " + ChatColor.YELLOW + unseenNameChanges.size() + ChatColor.AQUA + " player(s) have changed their username since you last logged on. Click here to view them.").event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bungeejl ncview")).create());
    }

    private void handleNameChange(ProxiedPlayer player, UUID uuid) {
        if(!PlayerData.contains(uuid)) return;
        PlayerData playerData = PlayerData.get(uuid).orElseThrow(() -> new NullPointerException("PlayerData contains UUID, yet get() returned null."));
        if(playerData.getName().equals(player.getName())) return;
        HashSet<UUID> shownPlayers = new HashSet<>();
        Joinlist.getPlugin().getProxy().getPlayers().forEach(p -> {
            if(p.hasPermission("bungeejl.viewnamechanges")) {
                p.sendMessage(new ComponentBuilder(ChatColor.DARK_AQUA + "[JoinList] " + ChatColor.AQUA + "Player " + ChatColor.YELLOW + playerData.getName() + ChatColor.AQUA + " has joined with a new username: " + ChatColor.YELLOW + player.getName()).create());
                shownPlayers.add(p.getUniqueId());
            }
        });
        new NameChange(getDate(), playerData.getName(), player.getName(), shownPlayers);
    }

    private String getDate() {
        Date date = new Date();
        TimeZone timeZone = TimeZone.getTimeZone("America/Los_Angeles");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }
}
