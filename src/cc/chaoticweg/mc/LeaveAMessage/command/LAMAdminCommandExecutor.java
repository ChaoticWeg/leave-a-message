package cc.chaoticweg.mc.LeaveAMessage.command;

import cc.chaoticweg.mc.LeaveAMessage.LAMPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class LAMAdminCommandExecutor extends LAMCommandExecutor {

    public LAMAdminCommandExecutor(LAMPlugin main) {
        super(main);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }

}
