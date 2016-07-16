package cc.chaoticweg.mc.LeaveAMessage.database;

import cc.chaoticweg.mc.LeaveAMessage.LAMPlugin;
import com.google.common.collect.Lists;

import javax.persistence.PersistenceException;
import java.util.List;

public class LAMDatabaseHandler {

    private LAMPlugin main;

    public LAMDatabaseHandler(LAMPlugin main) {
        this.main = main;
    }

    public void setup() {
        try {
            main.getDatabase().find(LAMMessage.class).findRowCount();
        } catch (PersistenceException ex) {
            ex.printStackTrace();
            main.installDatabase();
        }
    }

    public void storeMessage(LAMMessage message) {
        main.getDatabase().save(message);
    }

    public List<LAMMessage> getMessagesForPlayer(String uuid) {
        List<LAMMessage> result = Lists.newArrayList();
        result.addAll(main.getDatabase().find(LAMMessage.class).where().ieq("uuidRecipient", uuid).findList());
        return result;
    }

}
