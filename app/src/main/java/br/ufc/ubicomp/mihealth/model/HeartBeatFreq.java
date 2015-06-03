package br.ufc.ubicomp.mihealth.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class HeartBeatFreq implements Serializable {
    private int id;
    private Date timeStamp;
    private int frequency;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(!(other instanceof HeartBeatFreq))
            return false;

        HeartBeatFreq freq = (HeartBeatFreq)other;

        return (this.id == freq.getId()       &&
                this.timeStamp.equals(freq.getTimeStamp()) &&
                this.getFrequency() == freq.getFrequency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.timeStamp, this.frequency);
    }
}
