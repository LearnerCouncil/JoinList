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

public class PlayerData {
    @Getter private String name;
    @Getter private final UUID id;

    public PlayerData(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public void add() {
        players.add(this);
        names.put(name, this);
        uuids.put(id, this);
    }

    public void remove() {
        players.remove(this);
        names.remove(this.name);
        uuids.remove(this.id);
    }

    public static final ArrayList<PlayerData> players = new ArrayList<>();
    private static final HashMap<String, PlayerData> names = new HashMap<>();
    private static final HashMap<UUID, PlayerData> uuids = new HashMap<>();

    public static Optional<PlayerData> get(String name) {
        return Optional.ofNullable(names.get(name));
    }
    public static Optional<PlayerData> get(UUID id) {
        return Optional.ofNullable(uuids.get(id));
    }
    public static boolean contains(UUID id) {
        return uuids.containsKey(id);
    }

    public void setName(String name) {
        this.name = name;
        names.put(name, this);
    }

    public String serialize() {
        return name + ':' + id.toString();
    }

    public static void deserialize(String serializedString) {
        String[] parts = serializedString.split(":");
        if(parts.length != 2) throw new IllegalArgumentException("Cannot deseirilaze provided string: " + serializedString);
        new PlayerData(parts[0], UUID.fromString(parts[1])).add();
    }
}
