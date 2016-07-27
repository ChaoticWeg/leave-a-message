package cc.chaoticweg.mc.LeaveAMessage.database;

import cc.chaoticweg.mc.LeaveAMessage.LAMPlugin;
import com.google.common.collect.Lists;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.UUID;

public class LAMDatabaseHandler {

    private LAMPlugin main;

    public LAMDatabaseHandler(LAMPlugin main) {
        this.main = main;
    }

    public void setup() {
        try {
            main.getDatabase().find(LAMMessage.class).findRowCount();
        } catch (PersistenceException ex) {
            main.getLogger().warning("Database read error, installing database");
            main.installDatabase();
        }
    }

    public void storeMessage(LAMMessage message) {
        main.getDatabase().save(message);
    }

    public List<LAMMessage> getMessagesForPlayer(UUID uuidRecipient) {
        List<LAMMessage> result = Lists.newArrayList();
        result.addAll(main.getDatabase().find(LAMMessage.class).where().ieq("uuidRecipient", uuidRecipient.toString()).findList());
        return result;
    }

    public void deleteMessages(List<LAMMessage> messages) {
        main.getDatabase().delete(messages);
    }

}
