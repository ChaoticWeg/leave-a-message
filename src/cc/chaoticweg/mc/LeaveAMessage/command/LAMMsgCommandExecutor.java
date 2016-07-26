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

/**
 * Handle the /msg command
 */
public class LAMMsgCommandExecutor extends LAMCommandExecutor implements CommandExecutor {

    public LAMMsgCommandExecutor(LAMPlugin main) {
        super(main);
    }

    // /msg <recipient> <message...>

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length < 2)
            return false;

        try {

            String recipientNameGiven = args[0];
            OfflinePlayer recipientPlayer = main.lookUpPlayerByName(recipientNameGiven);

            if (recipientPlayer == null) {
                // The recipient does not exist.
                sender.sendMessage("[LAM] " + ChatColor.RED + "No such player: " + recipientNameGiven + ChatColor.RESET);
                return true;
            }

            // The recipient exists.

            UUID uuidRecipient = recipientPlayer.getUniqueId();
            String messageContent = LAMUtils.join(LAMUtils.splice(args, 1));

            if (recipientPlayer.isOnline() && recipientPlayer instanceof Player) {

                // TODO recipient is online (and is a Player), display message immediately to recipient, do not save in database

                ((Player) recipientPlayer).sendMessage(String.format("%s%s * %s: %s", ChatColor.ITALIC, ChatColor.GRAY, sender.getName(), messageContent));
                sender.sendMessage(String.format("[LAM] Sent message to %s%s%s.", ChatColor.GOLD, recipientPlayer.getName(), ChatColor.RESET));

                return true;

            } else {

                // TODO recipient is NOT online (or is not a Player), store message in database

                String senderName = sender.getName();

                LAMMessage message = LAMMessage.build(senderName, uuidRecipient, messageContent);
                main.getDatabaseHandler().storeMessage(message);

                sender.sendMessage(String.format("[LAM] Left message for %s%s%s.", ChatColor.GOLD, recipientPlayer.getName(), ChatColor.RESET));
                return true;

            }

        } catch (IllegalArgumentException iae) {

            // ......this should never happen, if our input sanitation is working.
            log.warning(String.format("%s attempted to send a message to an invalid recipient", sender.getName()));
            return false;

        }
    }
}
