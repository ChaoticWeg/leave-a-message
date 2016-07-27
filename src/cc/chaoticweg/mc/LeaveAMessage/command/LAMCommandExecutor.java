package cc.chaoticweg.mc.LeaveAMessage.command;

import cc.chaoticweg.mc.LeaveAMessage.LAMPlugin;
import org.bukkit.command.CommandExecutor;

import java.util.logging.Logger;

public abstract class LAMCommandExecutor implements CommandExecutor {

    private LAMCommandExecutor() { /* disallow default constructor */ };

    LAMPlugin main;
    Logger log;

    public LAMCommandExecutor(LAMPlugin main) {
        this.main = main;
        this.log = main.getLogger();
    }

    public LAMPlugin getLAMPlugin() {
        return main;
    }

    public Logger getLogger() {
        return log;
    }

}
