package rocks.learnercouncil.bungeejoinlist;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class ConfigHandler {

    private static final BungeeJoinlist plugin = BungeeJoinlist.getPlugin();
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
