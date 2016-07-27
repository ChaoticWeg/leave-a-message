package cc.chaoticweg.mc.LeaveAMessage.command;

import cc.chaoticweg.mc.LeaveAMessage.LAMPlugin;
import cc.chaoticweg.mc.LeaveAMessage.database.LAMDatabaseHandler;
import cc.chaoticweg.mc.LeaveAMessage.database.LAMMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class LAMAdminCommandExecutor extends LAMCommandExecutor {

    public LAMAdminCommandExecutor(LAMPlugin main) {
        super(main);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length == 0)
            return checkMailbox(sender);

        return false;
    }

    private boolean checkMailbox(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(String.format("[LAM] %sThe console cannot receive messages%s.", ChatColor.RED, ChatColor.RESET));
            return true;
        }

        Player player = (Player) sender;
        LAMDatabaseHandler dbh = this.main.getDatabaseHandler();

        List<LAMMessage> messages = dbh.getMessagesForPlayer(((Player) sender).getUniqueId());

        if (messages.size() == 0) {
            sender.sendMessage("[LAM] No new messages.");
            return true;
        }

        String[] msgsDisplay = new String[messages.size()];
        for (int i = 0; i < messages.size(); i++) {
            LAMMessage msg = messages.get(i);

            String senderDisplayName = String.format("%s%s%s", ChatColor.YELLOW, msg.getSenderName(), ChatColor.RESET);
            String thisMessage = String.format("[LAM] <%s> %s", senderDisplayName, msg.getContent());

            msgsDisplay[i] = thisMessage;
        }

        sender.sendMessage(String.format("[LAM] Dumping %s%d%s unread message(s)...", ChatColor.GREEN, msgsDisplay.length, ChatColor.RESET));
        sender.sendMessage(msgsDisplay);

        return true;
    }

}
