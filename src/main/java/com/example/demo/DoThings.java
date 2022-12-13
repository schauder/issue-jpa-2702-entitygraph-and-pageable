package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class DoThings {

    @Autowired
    private SomeThingRepository someThingRepository;

    @PostConstruct
    void doThings() {
        for (int i = 0; i < 100; i++) {
            final SomeThing someThing = new SomeThing("Thing " + i);
            someThing.addOtherThing(new OtherThing("Thing " + i + " child a"));
            someThing.addOtherThing(new OtherThing("Thing " + i + " child b"));
            someThingRepository.saveAndFlush(someThing);
        }

        final Page<SomeThing> thingsPage = someThingRepository.findAll(Pageable.ofSize(10));

        System.out.println("Things: " + thingsPage.getContent().size() + ", Expected: 10");
        System.out.println("Available Things: " + thingsPage.getTotalElements() + ", Expected: 100");
    }
}
