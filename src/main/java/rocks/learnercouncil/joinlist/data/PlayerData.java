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

import lombok.Getter;

import java.util.*;

public abstract class PlayerData {
    @Getter protected String name;

    public static List<PlayerData> players = new ArrayList<>();

    private static final HashMap<String, PlayerData> names = new HashMap<>();
    public static Optional<PlayerData> get(String name) {
        return Optional.ofNullable(names.get(name));
    }
    public void setName(String name) {
        this.name = name;
        names.put(name, this);
    }

    public void add() {
        players.add(this);
        names.put(name, this);
    }
    public void remove() {
        players.remove(this);
        names.remove(this.name);
    }


    public abstract String serialize();
    public static void deserialize(String serializedString) {
        String[] parts = serializedString.split(":");
        if(parts.length != 2) throw new IllegalArgumentException("Cannot deseirilaze provided string: " + serializedString);
        if(parts[1].equals("BEDROCK"))
            players.add(new BedrockPlayer(parts[0]));
        else players.add(new JavaPlayer(parts[0], UUID.fromString(parts[1])));
        new JavaPlayer(parts[0], UUID.fromString(parts[1])).add();
    }

}
