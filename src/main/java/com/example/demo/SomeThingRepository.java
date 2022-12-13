package com.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SomeThingRepository extends JpaRepository<SomeThing, UUID> {
    @EntityGraph(value = "SomeThing.graph", type = EntityGraph.EntityGraphType.FETCH)
    Page<SomeThing> findAll(Pageable p);

}
