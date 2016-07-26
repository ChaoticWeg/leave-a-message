package cc.chaoticweg.mc.LeaveAMessage;

import cc.chaoticweg.mc.LeaveAMessage.command.LAMMsgCommandExecutor;
import cc.chaoticweg.mc.LeaveAMessage.database.LAMDatabaseHandler;
import cc.chaoticweg.mc.LeaveAMessage.database.LAMMessage;
import cc.chaoticweg.mc.LeaveAMessage.event.LAMLoginEventListener;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Logger;

/**
 * Main class, as far as CraftBukkit is concerned. This must be the class pointed to by {@code main} in plugin.yml
 */
public class LAMPlugin extends JavaPlugin {

    private Logger log;

    private LAMDatabaseHandler databaseHandler;
    public LAMDatabaseHandler getDatabaseHandler() { return databaseHandler; }

    @Override
    public void onEnable() {
        this.log = this.getLogger();

        // initialize database handler
        log.info("Initializing LAM database handler");
        this.databaseHandler = new LAMDatabaseHandler(this);
        databaseHandler.setup(); // don't forget this, lol

        // set command and event handlers
        this.getCommand("msg").setExecutor(new LAMMsgCommandExecutor(this));
        this.getServer().getPluginManager().registerEvents(LAMLoginEventListener.getInstance(this), this);

        // done.
        log.info("Enabled.");
    }

    public void installDatabase() {
        log.info("Installing database.");
        this.installDDL();
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> result = Lists.newArrayList();
        result.add(LAMMessage.class);
        return result;
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
