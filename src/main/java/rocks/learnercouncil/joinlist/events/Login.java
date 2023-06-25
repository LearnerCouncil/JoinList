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

package rocks.learnercouncil.joinlist.events;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import rocks.learnercouncil.joinlist.Joinlist;
import rocks.learnercouncil.joinlist.data.BedrockPlayer;
import rocks.learnercouncil.joinlist.data.JavaPlayer;

public class Login implements Listener {


    @EventHandler
    public void onPlayerLogin(LoginEvent e) {
        if(!Joinlist.enabled) return;
        if(JavaPlayer.contains(e.getConnection().getUniqueId()) || BedrockPlayer.contains(e.getConnection().getName())) return;
        e.getConnection().disconnect(new ComponentBuilder(
                "You are not on the joinlist.\n Please type")
                .color(ChatColor.RED)
                .append(" /joinlist ")
                .bold(true)
                .append("followed by your username in any\n" +
                        "text channel on our discord.")
                .bold(false)
                .create());
    }
}
