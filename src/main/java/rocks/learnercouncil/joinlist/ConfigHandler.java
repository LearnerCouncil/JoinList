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

package rocks.learnercouncil.joinlist;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class ConfigHandler {

    private static final Joinlist plugin = Joinlist.getPlugin();
    @NotNull public static final Configuration config = loadConfig();

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static File loadResource() {
        File folder = plugin.getDataFolder();
        if (!folder.exists())
            folder.mkdir();
        File resourceFile = new File(folder, "config.yml");
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
                try (InputStream in = plugin.getResourceAsStream("config.yml");
                     OutputStream out = new FileOutputStream(resourceFile)) {
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceFile;
    }

    public static Configuration loadConfig() {
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(loadResource());
        } catch (IOException e) {
            plugin.getLogger().severe(ChatColor.DARK_RED + "Encountered IOExcetion loading config file, aborting.");
            e.printStackTrace();
            plugin.getProxy().stop();
            return new Configuration();
        }
    }

    public static void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            plugin.getLogger().severe("Encountered IOException saving config file. Config will not save.");
        }
    }
}
