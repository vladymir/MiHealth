package br.ufc.ubicomp.mihealth.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.types.TimeStampType;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by vladymirbezerra on 25/04/15.
 */
@DatabaseTable
public class HeartBeatFreq implements Serializable {
    @DatabaseField(generatedId = true, canBeNull = false)
    private Long id;
    @DatabaseField(canBeNull = false)
    TimeStampType timeStamp;
    @DatabaseField
    Integer frequency;
    @DatabaseField(canBeNull = false)
    User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeStampType getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(TimeStampType timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }
}
