package com.example.demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PageableWithEntityGraphTest {

	@Autowired
	private SomeThingRepository someThingRepository;

	@Autowired
	EntityManager em;

	private void setup() {
		for (int i = 0; i < 100; i++) {
			SomeThing someThing = new SomeThing("Thing " + i);
			someThing.addOtherThing(new OtherThing("Thing " + i + " child a"));
			someThing.addOtherThing(new OtherThing("Thing " + i + " child b"));
			em.persist(someThing);
		}
		em.flush();
	}

	@Test
	void doThings() {
		setup();

		Page<SomeThing> thingsPage = someThingRepository.findAll(Pageable.ofSize(10));

		SoftAssertions.assertSoftly(softly -> {
			softly.assertThat(thingsPage.getContent().size()).isEqualTo(10);
			softly.assertThat(thingsPage.getTotalElements()).isEqualTo(100);

		});
	}

	@Test
	void doThingsWithEntityManager() {
		setup();
		TypedQuery<SomeThing> query = em.createQuery("select st from SomeThing st", SomeThing.class);
		query.setHint("jakarta.persistence.loadgraph", em.getEntityGraph("SomeThing.graph"));
		List<SomeThing> tenThings = query.setMaxResults(10).getResultList();
		assertThat(tenThings).hasSize(10);
	}
}
