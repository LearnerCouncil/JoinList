package rocks.learnercouncil.bungeejoinlist;

import java.util.Optional;
import java.util.UUID;

public class PlayerData {
    public final String name;
    public final String id;

    public PlayerData(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public static Optional<PlayerData> get(String name) {
        return BungeeJoinlist.players.stream().filter(data -> data.name.equalsIgnoreCase(name)).findFirst();
    }

    public static Optional<PlayerData> get(UUID id) {
        return BungeeJoinlist.players.stream().filter(data -> id.equals(UUID.fromString(data.id))).findFirst();
    }

    public String serialize() {
        return name + ':' + id;
    }

    public static PlayerData deserialize(String serializedString) {
        String[] parts = serializedString.split(":");
        if(parts.length != 2) throw new IllegalArgumentException("Cannot deseirilaze provided string: " + serializedString);
        return new PlayerData(parts[0], parts[1]);
    }
}
