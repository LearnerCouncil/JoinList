package rocks.learnercouncil.bungeewhitelist.events;

import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import rocks.learnercouncil.bungeewhitelist.BungeeWhitelist;
import rocks.learnercouncil.bungeewhitelist.ConfigHandler;

import java.util.List;

public class PreLogin implements Listener {
    private BungeeWhitelist plugin;
    public PreLogin(BungeeWhitelist plugin) {
        this.plugin = plugin;
    }
    public void onPreLogin(PreLoginEvent e) {
        List<String> whitelist = ConfigHandler.config.getStringList("players");
        if(!whitelist.contains(e.getConnection().getName()))
            e.setCancelled(true);
    }
}
