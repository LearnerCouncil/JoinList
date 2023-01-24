package rocks.learnercouncil.bungeejoinlist.data;

import net.md_5.bungee.config.Configuration;
import rocks.learnercouncil.bungeejoinlist.ConfigHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class NameChange {

    private final String date;
    private final String oldName;
    private final String newName;
    public final Set<UUID> shownPlayers;

    public NameChange(String date, String oldName, String newName, Set<UUID> shownPlayers) {
        this.date = date;
        this.oldName = oldName;
        this.newName = newName;
        this.shownPlayers = shownPlayers;
        nameChanges.add(this);
        if(PlayerData.get(oldName).isPresent())
            PlayerData.get(oldName).get().setName(newName);

    }

    private static final ArrayList<NameChange> nameChanges = new ArrayList<>();

    public static List<NameChange> getUnseenNameChanges(UUID uuid) {
        List<NameChange> c = new ArrayList<>();
        for (NameChange change : nameChanges) {
            if (!change.shownPlayers.contains(uuid)) c.add(change);
        }
        return c;
    }

    public static List<NameChange> getNameChanges() {
        return nameChanges;
    }

    public static void serialize() {
        Configuration config = ConfigHandler.config;
        for(NameChange change : nameChanges) {
            String basePath = "name-changes." + change.oldName + '-' + change.newName + '.';
            config.set(basePath + "date", change.date);
            config.set(basePath + "shown-players", change.shownPlayers.stream().map(UUID::toString).collect(Collectors.toList()));
        }
    }

    public static void deserialize() {
        Configuration section = ConfigHandler.config.getSection("name-changes");
        for(String key : section.getKeys()) {
            String date = section.getString(key + ".date");
            String[] names = key.split("-");
            if(names.length != 2) return;
            String oldName = names[0];
            String newName = names[1];
            Set<UUID> shownPlayers = section.getStringList(key + ".shown-players").stream().map(UUID::fromString).collect(Collectors.toSet());
            new NameChange(date, oldName, newName, shownPlayers);
        }
    }

    @Override
    public String toString() {
        return oldName + " -> " + newName + ": " + date;
    }
}
