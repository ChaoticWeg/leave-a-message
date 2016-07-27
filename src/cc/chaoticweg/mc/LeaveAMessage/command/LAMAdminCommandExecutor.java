package cc.chaoticweg.mc.LeaveAMessage.command;

import cc.chaoticweg.mc.LeaveAMessage.LAMPlugin;
import cc.chaoticweg.mc.LeaveAMessage.database.LAMDatabaseHandler;
import cc.chaoticweg.mc.LeaveAMessage.database.LAMMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class LAMAdminCommandExecutor extends LAMCommandExecutor {

    public LAMAdminCommandExecutor(LAMPlugin main) {
        super(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player))
            return warnConsoleSenderNoMailbox(sender);

        if (args.length == 0 || args[0].equalsIgnoreCase("check"))
            return checkMailbox(sender);

        if (args[0].equalsIgnoreCase("clear"))
            return clearMailbox(sender);

        // TODO need more arguments?
        return false;
    }

    private boolean checkMailbox(CommandSender sender) {
        if (!(sender instanceof Player))
            return warnConsoleSenderNoMailbox(sender);

        UUID playerUUID = ((Player) sender).getUniqueId();

        // if msgCount <= 0, no messages available
        if (!checkMessageCountGreaterThanZero(playerUUID))
            return warnSenderMailboxEmpty(sender);

        // if msgCount > 0, send the available messages to the player
        return sendMessagesToPlayer(getMessagesForPlayer(playerUUID), sender);
    }

    private boolean clearMailbox(CommandSender sender) {
        if (!(sender instanceof Player))
            return warnConsoleSenderNoMailbox(sender);

        UUID uuid = ((Player)sender).getUniqueId();

        if (!checkMessageCountGreaterThanZero(uuid))
            return warnSenderMailboxEmpty(sender);

        LAMDatabaseHandler dbh = main.getDatabaseHandler();
        dbh.deleteMessages(dbh.getMessagesForPlayer(uuid));

        if (checkMessageCountGreaterThanZero(uuid))
            log.warning("Unable to clear mailbox for " + sender.getName());

        return true;
    }

    private boolean warnSenderMailboxEmpty(CommandSender sender) {
        sender.sendMessage("[LAM] Your mailbox is empty.");
        return true;
    }

    private boolean warnConsoleSenderNoMailbox(CommandSender sender) {
        sender.sendMessage(String.format("[LAM] %sThe console has no mailbox.", ChatColor.RED));
        return true;
    }

    private List<LAMMessage> getMessagesForPlayer(UUID playerUUID) {
        return main.getDatabaseHandler().getMessagesForPlayer(playerUUID);
    }

    private boolean sendMessagesToPlayer(List<LAMMessage> messages, CommandSender player) {
        messages.forEach((msg) -> player.sendMessage(msg.getDisplayString()));
        main.getDatabaseHandler().deleteMessages(messages);
        return true;
    }

    private boolean checkMessageCountGreaterThanZero(UUID uuid) {
        return main.getDatabaseHandler().getMessagesForPlayer(uuid).size() > 0;
    }

}
