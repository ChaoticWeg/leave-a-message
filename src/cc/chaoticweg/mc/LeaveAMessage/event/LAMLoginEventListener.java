package cc.chaoticweg.mc.LeaveAMessage.event;

import cc.chaoticweg.mc.LeaveAMessage.LAMPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.logging.Logger;

/**
 * Listens for {@link org.bukkit.event.Event}s.
 */
public class LAMLoginEventListener implements Listener {

    private LAMPlugin main;
    private Logger log;

    private LAMLoginEventListener(LAMPlugin main) {
        this.main = main;
        this.log = main.getLogger();
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        log.info("Player logged in: " + player.getDisplayName());

        // TODO database handler: search for messages with this player as the recipient
    }



    /* Instance handling */

    private static LAMLoginEventListener _instance = null;

    public static LAMLoginEventListener getInstance(LAMPlugin main) {
        if (_instance == null)
            _instance = new LAMLoginEventListener(main);

        return _instance;
    }

}
