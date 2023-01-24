package rocks.learnercouncil.bungeejoinlist;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

public class ConfigHandler {

    private static final BungeeJoinlist plugin = BungeeJoinlist.getPlugin();
    public static Configuration config = loadConfig();

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
            e.printStackTrace();
        }
        return null;
    }

    public static void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
