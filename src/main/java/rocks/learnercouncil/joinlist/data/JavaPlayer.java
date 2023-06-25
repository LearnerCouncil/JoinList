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

package rocks.learnercouncil.joinlist.data;

import lombok.Getter;

import java.util.*;

public class JavaPlayer extends PlayerData {
    @Getter private final UUID id;

    public JavaPlayer(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public void add() {
        super.add();
        uuids.put(id, this);
    }

    @Override
    public void remove() {
        super.remove();
        uuids.remove(this.id);
    }

    private static final HashMap<UUID, JavaPlayer> uuids = new HashMap<>();
    public static Optional<JavaPlayer> get(UUID id) {
        return Optional.ofNullable(uuids.get(id));
    }
    public static boolean contains(UUID id) {
        return uuids.containsKey(id);
    }

    @Override
    public String serialize() {
        return name + ':' + id.toString();
    }
}
