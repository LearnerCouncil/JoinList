package rocks.learnercouncil.bungeejoinlist.data;

import lombok.Getter;

import java.util.*;

public class PlayerData {
    @Getter private String name;
    @Getter private final UUID id;

    public PlayerData(String name, UUID id) {
        this.name = name;
        this.id = id;
    }

    public void add() {
        players.add(this);
        names.put(name, this);
        uuids.put(id, this);
    }

    public void remove() {
        players.remove(this);
        names.remove(this.name);
        uuids.remove(this.id);
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

    public static void deserialize(String serializedString) {
        String[] parts = serializedString.split(":");
        if(parts.length != 2) throw new IllegalArgumentException("Cannot deseirilaze provided string: " + serializedString);
        new PlayerData(parts[0], UUID.fromString(parts[1])).add();
    }
}
