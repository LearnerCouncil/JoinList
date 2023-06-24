package rocks.learnercouncil.bungeejoinlist;

import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import rocks.learnercouncil.bungeejoinlist.commands.JoinlistCmd;
import rocks.learnercouncil.bungeejoinlist.data.NameChange;
import rocks.learnercouncil.bungeejoinlist.data.PlayerData;
import rocks.learnercouncil.bungeejoinlist.events.Login;
import rocks.learnercouncil.bungeejoinlist.events.PostLogin;

import java.util.List;
import java.util.stream.Collectors;

public final class BungeeJoinlist extends Plugin {

    @Getter private static BungeeJoinlist plugin;

    public static boolean enabled;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        //
        enabled = ConfigHandler.config.getBoolean("enabled");
        ConfigHandler.config.getStringList("players").forEach(PlayerData::deserialize);
        NameChange.deserialize();
        getLogger().info("Loaded Joinlist.");
        getProxy().getPluginManager().registerListener(this, new Login());
        getProxy().getPluginManager().registerListener(this, new PostLogin());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new JoinlistCmd());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        List<String> playerList = PlayerData.players.stream().map(PlayerData::serialize).collect(Collectors.toList());
        ConfigHandler.config.set("players", playerList);
        ConfigHandler.config.set("enabled", enabled);
        NameChange.serialize();
        ConfigHandler.saveConfig();
    }
}
