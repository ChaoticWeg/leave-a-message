package cc.chaoticweg.mc.LeaveAMessage;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class LAMPlugin extends JavaPlugin {

    private Logger log;

    @Override
    public void onEnable() {
        this.log = this.getLogger();

        // TODO this

        log.info("Enabled.");
    }

}
