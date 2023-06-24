package rocks.learnercouncil.bungeejoinlist.data;

import lombok.Getter;

import java.util.*;

public class PlayerData {
    @Getter private String name;
    public final UUID id;

    public PlayerData(String name, UUID id) {
        this.name = name;
        this.id = id;
        players.add(this);
        names.put(name, this);
        uuids.put(id, this);
    }

    public static final ArrayList<PlayerData> players = new ArrayList<>();
    private static final HashMap<String, PlayerData> names = new HashMap<>();
    private static final HashMap<UUID, PlayerData> uuids = new HashMap<>();

    public static Optional<PlayerData> get(String name) {
        return Optional.ofNullable(names.get(name));
    }
    public static Optional<PlayerData> get(UUID id) {
        return Optional.ofNullable(uuids.get(id));
    }
    public static boolean contains(UUID id) {
        return uuids.containsKey(id);
    }

    public void setName(String name) {
        this.name = name;
        names.put(name, this);
    }

    public String serialize() {
        return name + ':' + id.toString();
    }

    public static PlayerData deserialize(String serializedString) {
        String[] parts = serializedString.split(":");
        if(parts.length != 2) throw new IllegalArgumentException("Cannot deseirilaze provided string: " + serializedString);
        return new PlayerData(parts[0], UUID.fromString(parts[1]));
    }
}
