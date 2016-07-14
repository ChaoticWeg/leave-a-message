package cc.chaoticweg.mc.LeaveAMessage.command;

import cc.chaoticweg.mc.LeaveAMessage.LAMPlugin;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

/**
 * Handle the /msg command
 */
public class LAMCommandExecutor implements CommandExecutor {

    private LAMPlugin main;
    private Logger log;

    private LAMCommandExecutor(LAMPlugin main) {
        this.main = main;
        this.log = main.getLogger();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length < 2)
            return false;

        try {

            String recipientName = args[0];
            OfflinePlayer recipientPlayer = main.lookUpPlayerByName(recipientName);

            if (recipientPlayer == null) {
                sender.sendMessage(ChatColor.RED + "No such player: " + recipientName + ChatColor.RESET);
                return true;
            }

            // TODO create message object (recipient, sender, text, dateSent)

            if (recipientPlayer.isOnline()) {
                // TODO recipient is online, display message immediately to recipient, do not save in database
                sender.sendMessage("[LAM] - " + ChatColor.GREEN + recipientName + " is online!");
            } else {
                // TODO recipient is NOT online, store message in database
                sender.sendMessage("[LAM] - " + ChatColor.YELLOW + recipientName + " exists! But they are offline.");
            }

            sender.sendMessage("[LAM] - " + ChatColor.GOLD + "We can't send messages yet.");
            return true;

        } catch (IllegalArgumentException iae) {
            log.warning(String.format("%s attempted to send a message to an invalid recipient", sender.getName()));
            return false;
        }
    }


    /* Instance handling below */

    private static LAMCommandExecutor _instance = null;

    public static LAMCommandExecutor getInstance(LAMPlugin main) {
        if (_instance == null)
            _instance = new LAMCommandExecutor(main);

        return _instance;
    }
}
