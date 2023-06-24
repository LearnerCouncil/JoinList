package rocks.learnercouncil.joinlist.commands;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import rocks.learnercouncil.joinlist.data.NameChange;
import rocks.learnercouncil.joinlist.data.PlayerData;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static net.md_5.bungee.api.ChatColor.*;

public class CommandResult {
    public static final BaseComponent[] NONE = new BaseComponent[0];

    private static ComponentBuilder prefix() {
        return new ComponentBuilder().append("[Joinlist] ").color(DARK_AQUA);
    }
    private static BaseComponent[] error(String message) {
        return prefix().append(message).color(RED).create();
    }
    private static BaseComponent[] result(String message) {
        return prefix().append(message).color(AQUA).create();
    }

    public static final BaseComponent[]
            TOO_MANY_ARGS = error("Too many arguments."),
            TOO_FEW_ARGS = error("Too few arguments."),
            ONLY_PLAYER = error("This command can only be executed by a player."),
            NO_PERMISSION = error("Sorry, but you don't have permission to execute this command."),
            ALREADY_ENABLED = error("Joinlist already enabled."),
            ALREADY_DISABLED = error("Joinlist already disabled");

    public static BaseComponent[] notFound(String username) {
        return prefix().append("Could not find player with name: ").color(RED)
                .append(username).color(DARK_RED)
                .append(".").color(RED).create();
    }
    public static BaseComponent[] notAdded(String username) {
        return prefix().append("'").color(RED)
                .append(username).color(DARK_RED)
                .append("' is not on the joinlist.").color(RED).create();
    }

    public static final BaseComponent[]
            ENABLED = result("Joinlist enabled."),
            DISABLED = result("Joinlist disabled.");

    public static BaseComponent[] added(String username) {
        return prefix().append("Added '").color(AQUA)
                .append(username).color(YELLOW)
                .append("' to the Joinlist.").color(AQUA).create();

    }
    public static BaseComponent[] removed(String username) {
        return prefix().append("Removed '").color(AQUA)
                .append(username).color(YELLOW)
                .append("' from the Joinlist.").color(AQUA).create();

    }
    public static BaseComponent[] list() {
        ComponentBuilder b = prefix().append("Players: \n").color(DARK_AQUA);
        for (Iterator<PlayerData> iterator = PlayerData.players.iterator(); iterator.hasNext(); ) {
            PlayerData data = iterator.next();
            b.append(data.getName()).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(data.getId().toString())));
            if(iterator.hasNext())
                b.append(", ");
        }
        return b.create();
    }
    public static BaseComponent[] listChanges(List<NameChange> unseen, List<NameChange> seen, String query) {
        return prefix().append("Name Changes: \n").color(DARK_AQUA)
                .append(unseen.stream().map(NameChange::toString).filter(c -> c.contains(query)).collect(Collectors.joining("\n"))).color(AQUA).bold(true)
                .append("\n")
                .append(seen.stream().map(NameChange::toString).filter(c -> c.contains(query)).collect(Collectors.joining("\n"))).color(AQUA).bold(false)
                .create();
    }
}
