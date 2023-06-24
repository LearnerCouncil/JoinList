package rocks.learnercouncil.joinlist.events;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import rocks.learnercouncil.joinlist.Joinlist;
import rocks.learnercouncil.joinlist.data.PlayerData;

public class Login implements Listener {


    @EventHandler
    public void onPlayerLogin(LoginEvent e) {
        if(!Joinlist.enabled) return;
        if(PlayerData.contains(e.getConnection().getUniqueId())) return;
        e.getConnection().disconnect(new ComponentBuilder(
                "You are not on the joinlist.\n Please type")
                .color(ChatColor.RED)
                .append(" /joinlist ")
                .bold(true)
                .append("followed by your username in any\n" +
                        "text channel on our discord.")
                .bold(false)
                .create());
    }
}
