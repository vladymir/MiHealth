package br.ufc.ubicomp.mihealth.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

import br.ufc.ubicomp.mihealth.enums.Gender;

/**
 * Created by vladymirbezerra on 25/04/15.
 */
@DatabaseTable
public class User implements Serializable {
    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;
    @DatabaseField
    private Gender gender;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = false)
    private Date birthDate;
    @DatabaseField
    private HeartBeatFreq heartBeatFreq;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public HeartBeatFreq getHeartBeatFreq() {
        return heartBeatFreq;
    }

    public void setHeartBeatFreq(HeartBeatFreq heartBeatFreq) {
        this.heartBeatFreq = heartBeatFreq;
    }
}
