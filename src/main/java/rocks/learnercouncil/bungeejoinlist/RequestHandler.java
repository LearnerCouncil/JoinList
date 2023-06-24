package rocks.learnercouncil.bungeejoinlist;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import rocks.learnercouncil.bungeejoinlist.data.PlayerData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class RequestHandler {
    private static final BungeeJoinlist plugin = BungeeJoinlist.getPlugin();

    public static boolean requestUUID(String username) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());
            BufferedReader in = new BufferedReader(inputStreamReader);

            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(in);
            String uuid = jsonObject.get("id").getAsString().replaceAll("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");
            String name = jsonObject.get("name").getAsString();
            in.close();
            new PlayerData(name, UUID.fromString(uuid));
            return true;
        } catch (Exception e) {
            plugin.getLogger().severe("Could net get UUID of " + username);
            return false;
        }
    }
}
