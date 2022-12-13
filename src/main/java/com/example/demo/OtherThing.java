package com.example.demo;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class OtherThing {
    @Id
    @GeneratedValue
    private UUID id;

    @Basic
    private String thingName;

    @ManyToOne
    private SomeThing someThing;

    public OtherThing() {
    }

    public OtherThing(String thingName) {
        this.thingName = thingName;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getThingName() {
        return thingName;
    }

    public void setThingName(String thingName) {
        this.thingName = thingName;
    }

    public SomeThing getSomeThing() {
        return someThing;
    }

    public void setSomeThing(SomeThing someThing) {
        this.someThing = someThing;
    }
}
