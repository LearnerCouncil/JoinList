package rocks.learnercouncil.bungeewhitelist;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import rocks.learnercouncil.bungeewhitelist.events.PreLogin;

import java.io.File;

public final class BungeeWhitelist extends Plugin {

    public static BungeeWhitelist plugin;
    public Configuration config = ConfigHandler.loadConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        getProxy().getPluginManager().registerListener(this, new PreLogin(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BungeeWhitelist getPlugin() {
        return plugin;
    }

}
