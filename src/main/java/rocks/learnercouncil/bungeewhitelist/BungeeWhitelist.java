package rocks.learnercouncil.bungeewhitelist;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import rocks.learnercouncil.bungeewhitelist.events.Login;

import java.util.List;

public final class BungeeWhitelist extends Plugin {

    public static BungeeWhitelist plugin;

    public static boolean enabled;
    public static List<String> players;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        //
        enabled = ConfigHandler.config.getBoolean("enabled");
        players = ConfigHandler.config.getStringList("players");
        getProxy().getPluginManager().registerListener(this, new Login());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new BungeewlCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ConfigHandler.config.set("players", players);
        ConfigHandler.config.set("enabled", enabled);
        ConfigHandler.saveConfig();
    }

    public static BungeeWhitelist getPlugin() {
        return plugin;
    }

}
