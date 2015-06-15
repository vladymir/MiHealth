package br.ufc.ubicomp.mihealth.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import br.ufc.ubicomp.mihealth.enums.Gender;


public class User implements Serializable {
    private int id;
    private Gender gender;
    private String name;
    private Date birthDate;
    private List<HeartBeatFreq> heartBeatFreq;

    public User() {
        this.heartBeatFreq = new ArrayList<HeartBeatFreq>();

    }

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

    public List<HeartBeatFreq> getFreqs() {
        return heartBeatFreq;
    }

    public void setFreqs(List<HeartBeatFreq> freqs) {
        this.heartBeatFreq = freqs;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if(!(other instanceof User))
            return false;

        User user = (User)other;

        return (this.id == user.getId()       &&
                this.birthDate.equals(user.birthDate) &&
                this.name.equals(user.name) &&
                this.gender == user.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.birthDate, this.name, this.gender);
    }
}
