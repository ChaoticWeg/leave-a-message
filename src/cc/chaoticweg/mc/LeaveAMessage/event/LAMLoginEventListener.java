package cc.chaoticweg.mc.LeaveAMessage.event;

import cc.chaoticweg.mc.LeaveAMessage.LAMPlugin;
import org.bukkit.ChatColor;
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
        log.info("Player logged in: " + player.getName());

        // TODO database handler: search for messages with this player as the recipient
        int msgCount = main.getDatabaseHandler().getMessagesForPlayer(player.getUniqueId()).size();

        if (msgCount < 1) {
            // There are no messages for the player. Bail out now.
            log.info(String.format("Player %s has no unread messages.", player.getName()));
            return;
        }

        log.info(String.format("Player %s has %d unread messages.", player.getName(), msgCount));
        player.sendMessage(String.format("[LAM] - You have %s%d%s unread message(s).", ChatColor.GOLD, msgCount, ChatColor.RESET));
        player.sendMessage(String.format("[LAM] - Use %s/lam%s to check unread messages.", ChatColor.GOLD, ChatColor.RESET));
    }



    /* Instance handling */

    private static LAMLoginEventListener _instance = null;

    public static LAMLoginEventListener getInstance(LAMPlugin main) {
        if (_instance == null)
            _instance = new LAMLoginEventListener(main);

        return _instance;
    }

}
