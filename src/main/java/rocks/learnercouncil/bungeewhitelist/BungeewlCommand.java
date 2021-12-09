package rocks.learnercouncil.bungeewhitelist;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;

public class BungeewlCommand extends Command {
    public BungeewlCommand() {
        super("bungeewl");
    }
    private List<String> wl = ConfigHandler.config.getStringList("players");

    private void updateConfig() {
        ConfigHandler.config.set("players", wl);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("bungeewl.command")) {

            switch(args[0]) {
                case "on":
                    ConfigHandler.config.set("enabled", true);
                    ConfigHandler.saveConfig();
                    return;
                case "off":
                    ConfigHandler.config.set("enabled", false);
                    ConfigHandler.saveConfig();
                    return;
                case "add":
                    if(args.length != 2) return;
                    wl.add(args[1]);
                    updateConfig();
                    ConfigHandler.saveConfig();
                    return;
                case "remove":
                    if(args.length !=2) return;
                    if(!ConfigHandler.config.getStringList("players").contains(args[1])) return;
                    wl.remove(args[1]);
                    updateConfig();
                    ConfigHandler.saveConfig();
                case "clear":
                    wl.clear();
                    updateConfig();
                    ConfigHandler.saveConfig();
                default:
                    return;
            }
        }
    }
}
