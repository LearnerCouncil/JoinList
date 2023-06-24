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

package rocks.learnercouncil.joinlist.data;

import net.md_5.bungee.config.Configuration;
import rocks.learnercouncil.joinlist.ConfigHandler;

import java.util.*;
import java.util.stream.Collectors;

public class NameChange {

    private final String date;
    private final String oldName;
    private final String newName;
    public final Set<UUID> shownPlayers;

    public NameChange(String date, String oldName, String newName, Set<UUID> shownPlayers) {
        this.date = date;
        this.oldName = oldName;
        this.newName = newName;
        this.shownPlayers = shownPlayers;
        nameChanges.add(this);
        if(JavaPlayer.get(oldName).isPresent())
            JavaPlayer.get(oldName).get().setName(newName);

    }

    private static final ArrayList<NameChange> nameChanges = new ArrayList<>();

    public static List<NameChange> getNameChanges(UUID uuid, boolean seen) {
        return nameChanges.stream().filter(c -> c.shownPlayers.contains(uuid) == seen).collect(Collectors.toList());
    }

    public static void serialize() {
        Configuration config = ConfigHandler.config;
        for(NameChange change : nameChanges) {
            String basePath = "name-changes." + change.oldName + '-' + change.newName + '.';
            config.set(basePath + "date", change.date);
            config.set(basePath + "shown-JAVA_PLAYERS", change.shownPlayers.stream().map(UUID::toString).collect(Collectors.toList()));
        }
    }

    public static void deserialize() {
        Configuration section = ConfigHandler.config.getSection("name-changes");
        for(String key : section.getKeys()) {
            String date = section.getString(key + ".date");
            String[] names = key.split("-");
            if(names.length != 2) return;
            String oldName = names[0];
            String newName = names[1];
            Set<UUID> shownPlayers = section.getStringList(key + ".shown-JAVA_PLAYERS").stream().map(UUID::fromString).collect(Collectors.toSet());
            new NameChange(date, oldName, newName, shownPlayers);
        }
    }

    @Override
    public String toString() {
        return oldName + " -> " + newName + ": " + date;
    }
}
