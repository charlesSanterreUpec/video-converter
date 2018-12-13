package edu.esipe.i3.ezipflix.frontend;

import java.util.UUID;

/**
 * Created by Gilles GIRAUD gil on 11/4/17.
 */
public class ConversionResponse {

    private String uuid;
    private String messageId;
    private String dbRetour;

    public ConversionResponse() {
    }

    public ConversionResponse(String uuid, String messageId, String dbRetour){
        this.uuid = uuid;
        this.messageId = messageId;
        this.dbRetour = dbRetour;
    }


    public final String getUuid() {
        return uuid;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getDbRetour() {
        return dbRetour;
    }

    public void setDbOutcome(String dbRetour) {
        this.dbRetour = dbRetour;
    }
}
