package rocks.learnercouncil.bungeewhitelist.events;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import rocks.learnercouncil.bungeewhitelist.BungeeWhitelist;

public class Login implements Listener {


    @EventHandler
    public void onPlayerLogin(LoginEvent e) {
        if(!BungeeWhitelist.enabled) return;
        if(BungeeWhitelist.players.contains(e.getConnection().getName())) return;
        e.getConnection().disconnect(new ComponentBuilder("§cYou are not whitelisted on this server.\n Please §b@The Council §con discord and ask to be whitelisted.").create());
    }


}
