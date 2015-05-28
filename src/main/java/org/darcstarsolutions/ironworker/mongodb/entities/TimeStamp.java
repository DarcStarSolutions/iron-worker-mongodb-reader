package org.darcstarsolutions.ironworker.mongodb.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.UUID;

/**
 * Created by mharris on 5/27/15.
 */

@Document(collection = "time-stamps")
public class TimeStamp {

    @Id
    private BigInteger id;

    private long time;

    private UUID uuid;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "TimeStamp{" +
                "id=" + id +
                ", time=" + time +
                ", uuid=" + uuid +
                '}';
    }
}
