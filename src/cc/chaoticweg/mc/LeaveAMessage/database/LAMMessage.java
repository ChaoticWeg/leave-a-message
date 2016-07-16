package cc.chaoticweg.mc.LeaveAMessage.database;

import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;
import org.bukkit.entity.Player;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Message class. Stores sender and recipient UUIDs, and message text
 */
@Entity()
@Table(name = "lam_message")
public class LAMMessage {

    /** pls no instantiate (except for {@link LAMMessage#build(UUID, UUID, String)}) */
    private LAMMessage() {}

    @Id private int id;

    @NotNull private UUID uuidSender;
    @NotNull private UUID uuidRecipient;
    @NotEmpty private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getUuidSender() {
        return uuidSender;
    }

    public void setUuidSender(UUID uuidSender) {
        this.uuidSender = uuidSender;
    }

    public UUID getUuidRecipient() {
        return uuidRecipient;
    }

    public void setUuidRecipient(UUID uuidRecipient) {
        this.uuidRecipient = uuidRecipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static LAMMessage build(UUID uuidSender, UUID uuidRecipient, String content) {
        LAMMessage result = new LAMMessage();

        result.setUuidSender(uuidSender);
        result.setUuidRecipient(uuidRecipient);
        result.setContent(content);

        return result;
    }

}
