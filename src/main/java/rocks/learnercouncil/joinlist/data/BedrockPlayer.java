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

public class BedrockPlayer extends PlayerData {

    public BedrockPlayer(String name) {
        this.name = name.toLowerCase();
    }

    public static boolean contains(String name) {
        if(!name.startsWith("+")) return false;
        return names.containsKey(name.toLowerCase());
    }

    @Override
    public String serialize() {
        return name + ":BEDROCK";
    }

    @Override
    public String toString() {
        return "BedrockPlayer{" +
                "name='" + name + '\'' +
                '}';
    }
}
