package cc.chaoticweg.mc.LeaveAMessage.database;

import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Message class. Stores sender and uuidRecipient UUIDs, and message text
 */
@Entity()
@Table(name = "lam_message")
public class LAMMessage {

    // do we need a default constructor?
    public LAMMessage() {};

    public LAMMessage(String senderName, UUID uuidRecipient, String content) {
        this.setSenderName(senderName);
        this.setUuidRecipient(uuidRecipient);
        this.setContent(content);
    }

    @Id private int id;

    @NotNull private UUID uuidRecipient;
    @NotNull @NotEmpty private String senderName;
    @NotNull @NotEmpty private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    public static LAMMessage build(String uuidSender, UUID recipient, String content) {
        return new LAMMessage(uuidSender, recipient, content);
    }

}
