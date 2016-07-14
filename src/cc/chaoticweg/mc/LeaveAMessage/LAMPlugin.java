package cc.chaoticweg.mc.LeaveAMessage;

import cc.chaoticweg.mc.LeaveAMessage.command.LAMCommandExecutor;
import com.google.common.collect.ImmutableList;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Main class, as far as CraftBukkit is concerned. This must be the class pointed to by {@code main} in plugin.yml
 */
public class LAMPlugin extends JavaPlugin {

    private Logger log;

    @Override
    public void onEnable() {
        this.log = this.getLogger();

        // TODO

        this.getCommand("msg").setExecutor(LAMCommandExecutor.getInstance(this));

        log.info("Enabled.");
    }


    /**
     * Look up a player (offline or online) by display name. This is not case sensitive.
     *
     * @param name The name of the player
     * @return A {@link Player} if the player exists and is online, an {@link OfflinePlayer} if the player exists and is
     * offline, or {@code null} if a player by that name has never joined this server.
     */
    public OfflinePlayer lookUpPlayerByName(String name) {
        if (name == null || name.equals("")) {
            // name is null or empty. this should be checked for before calling this function, so this is Very Bad
            throw new IllegalArgumentException("Player name may not be null or empty");
        }

        // TODO is there a better or faster way to do this?

        // check offline players first
        OfflinePlayer[] offlinePlayers = this.getServer().getOfflinePlayers();
        for (OfflinePlayer p : offlinePlayers) {
            if (p.getName().equalsIgnoreCase(name)) {
                // we have a player offline whose name matches
                return p;
            }
        }

        // check online players second
        // use ImmutableList to create a snapshot of players immediately, and work with that.
        // XXX POSSIBLE BUG IN THE FUTURE: player logs in between players snapshot and message send
        ImmutableList<Player> onlinePlayers = ImmutableList.copyOf(this.getServer().getOnlinePlayers());
        for (Player p : onlinePlayers) {
            if (p.getName().equalsIgnoreCase(name)) {
                // we have a player online whose name matches
                return p;
            }
        }

        return null;
    }

}
