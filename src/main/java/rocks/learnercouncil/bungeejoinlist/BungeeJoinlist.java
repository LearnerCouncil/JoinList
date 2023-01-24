package rocks.learnercouncil.bungeejoinlist;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import rocks.learnercouncil.bungeejoinlist.events.Login;

import java.util.List;
import java.util.stream.Collectors;

public final class BungeeJoinlist extends Plugin {

    private static BungeeJoinlist plugin;

    public static boolean enabled;
    public static List<PlayerData> players;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        //
        enabled = ConfigHandler.config.getBoolean("enabled");
        players = ConfigHandler.config.getStringList("players").stream().map(PlayerData::deserialize).collect(Collectors.toList());
        getLogger().info("Loaded Joinlist.");
        getProxy().getPluginManager().registerListener(this, new Login());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new BungeejlCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        List<String> playerList = players.stream().map(PlayerData::serialize).collect(Collectors.toList());
        getLogger().info("Players: " + playerList);
        ConfigHandler.config.set("players", playerList);
        ConfigHandler.config.set("enabled", enabled);
        ConfigHandler.saveConfig();
    }

    public static BungeeJoinlist getPlugin() {
        return plugin;
    }
}
