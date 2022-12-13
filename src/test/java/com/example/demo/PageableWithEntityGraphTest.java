package com.example.demo;

import jakarta.transaction.Transactional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@Transactional
class PageableWithEntityGraphTest {

	@Autowired
	private SomeThingRepository someThingRepository;



	@Test
	void doThings() {
		for (int i = 0; i < 100; i++) {
			SomeThing someThing = new SomeThing("Thing " + i);
			someThing.addOtherThing(new OtherThing("Thing " + i + " child a"));
			someThing.addOtherThing(new OtherThing("Thing " + i + " child b"));
			someThingRepository.saveAndFlush(someThing);
		}

		Page<SomeThing> thingsPage = someThingRepository.findAll(Pageable.ofSize(10));

		SoftAssertions.assertSoftly(softly -> {
			softly.assertThat(thingsPage.getContent().size()).isEqualTo(10);
			softly.assertThat(thingsPage.getTotalElements()).isEqualTo(100);

		});
	}
}
