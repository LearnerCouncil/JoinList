package rocks.learnercouncil.joinlist;

import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import rocks.learnercouncil.joinlist.commands.JoinlistCmd;
import rocks.learnercouncil.joinlist.data.NameChange;
import rocks.learnercouncil.joinlist.data.PlayerData;
import rocks.learnercouncil.joinlist.events.Login;
import rocks.learnercouncil.joinlist.events.PostLogin;

import java.util.List;
import java.util.stream.Collectors;

public final class Joinlist extends Plugin {

    @Getter private static Joinlist plugin;

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
