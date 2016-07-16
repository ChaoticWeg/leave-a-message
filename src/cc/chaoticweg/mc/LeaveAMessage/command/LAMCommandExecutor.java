package cc.chaoticweg.mc.LeaveAMessage.command;

import cc.chaoticweg.mc.LeaveAMessage.LAMPlugin;
import cc.chaoticweg.mc.LeaveAMessage.LAMUtils;
import cc.chaoticweg.mc.LeaveAMessage.database.LAMMessage;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
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
        if (args.length < 3)
            return false;

        try {

            String recipientName = args[0];
            OfflinePlayer recipientPlayer = main.lookUpPlayerByName(recipientName);

            if (recipientPlayer == null) {
                sender.sendMessage("[LAM] - " + ChatColor.RED + "No such player: " + recipientName + ChatColor.RESET);
                return true;
            }

            // TODO create message object (recipient, sender, text, dateSent)

            if (recipientPlayer.isOnline()) {

                // TODO recipient is online, display message immediately to recipient, do not save in database
                sender.sendMessage("[LAM] - " + ChatColor.GREEN + recipientName + " is online!");

            } else {

                if (!(sender instanceof Player)) {
                    sender.sendMessage("[LAM] - " + ChatColor.RED + "Sorry, console messaging is not supported yet.");
                    return true;
                }

                // TODO recipient is NOT online, store message in database

                UUID uuidSender = ((Player) sender).getUniqueId();
                UUID uuidRecipient = recipientPlayer.getUniqueId();
                String strContent = LAMUtils.join(LAMUtils.splice(args, 1));

                LAMMessage message = LAMMessage.build(uuidSender, uuidRecipient, strContent);
                main.getDatabaseHandler().storeMessage(message);

                sender.sendMessage(String.format("[LAM] - %sLeft a message for %s%s%s.", ChatColor.GOLD, ChatColor.RESET, recipientPlayer, ChatColor.GOLD));
                return true;
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
