package rocks.learnercouncil.bungeejoinlist.commands.arguments;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import rocks.learnercouncil.bungeejoinlist.BungeeJoinlist;
import rocks.learnercouncil.bungeejoinlist.commands.CommandArgument;
import rocks.learnercouncil.bungeejoinlist.commands.CommandResult;
import rocks.learnercouncil.bungeejoinlist.data.PlayerData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AddArg implements CommandArgument {
    private static final BungeeJoinlist plugin = BungeeJoinlist.getPlugin();

    @Override
    public BaseComponent[] execute(CommandSender sender, String[] args) {
        if(!args[0].equalsIgnoreCase("add")) return CommandResult.NONE;
        if(args.length < 2) return CommandResult.TOO_FEW_ARGS;
        if(args.length > 2) return CommandResult.TOO_MANY_ARGS;

        String username = args[1];
        Optional<PlayerData> playerData = requestUUID(username);
        if(!playerData.isPresent()) return CommandResult.notFound(username);
        playerData.get().add();
        return CommandResult.added(playerData.get().getName());
    }

    public Optional<PlayerData> requestUUID(String username) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());
            BufferedReader in = new BufferedReader(inputStreamReader);

            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(in);
            String uuid = jsonObject.get("id").getAsString().replaceAll("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");
            String name = jsonObject.get("name").getAsString();
            in.close();

            return Optional.of(new PlayerData(name, UUID.fromString(uuid)));
        } catch (Exception e) {
            plugin.getLogger().severe("Could net get UUID of " + username);
            return Optional.empty();
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length == 1) return Collections.singletonList("add");
        return Collections.emptyList();
    }
}
