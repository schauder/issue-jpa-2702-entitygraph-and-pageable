package com.example.demo;

import jakarta.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NamedEntityGraph(
        name = "SomeThing.graph",
        attributeNodes = {
            @NamedAttributeNode(value = "otherThings", subgraph = "SomeThing.graph.otherThings")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "SomeThing.graph.otherThings",
                        type = OtherThing.class,
                        attributeNodes = {
                                @NamedAttributeNode("thingName")
                        }
                )
        }
)
public class SomeThing {
    @Id
    @GeneratedValue
    private UUID id;

    @Basic
    private String thingName;

    @OneToMany(mappedBy = "someThing", cascade = {CascadeType.ALL, CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<OtherThing> otherThings = new ArrayList<>();

    public SomeThing() {
    }

    public SomeThing(String thingName) {
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

    public List<OtherThing> getOtherThings() {
        return otherThings;
    }

    public void addOtherThing(OtherThing otherThing) {
        otherThing.setSomeThing(this);
        this.otherThings.add(otherThing);
    }
}
