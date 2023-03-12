package sia5;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FluxCreationTest {
	@Test
	public void createAFlux_just() {
		Flux<String> girlGroupsFlux = Flux
				.just("Ive", "Lesserafim", "Newjeans", "G-IDLE", "ITZY", "BlackPink");
		girlGroupsFlux.subscribe(f -> System.out.println("Here's some girl group: " + f));

		StepVerifier.create(girlGroupsFlux)
				.expectNext("Ive")
				.expectNext("Lesserafim")
				.expectNext("Newjeans")
				.expectNext("G-IDLE")
				.expectNext("ITZY")
				.expectNext("BlackPink")
				.verifyComplete();
	}

	@Test
	public void createAFlux_fromArray() {
		String[] girlGroups = new String[] {"Ive", "Lesserafim", "Newjeans", "G-IDLE", "ITZY", "BlackPink"};
		Flux<String> girlGroupsFlux = Flux.fromArray(girlGroups);
		StepVerifier.create(girlGroupsFlux)
				.expectNext("Ive")
				.expectNext("Lesserafim")
				.expectNext("Newjeans")
				.expectNext("G-IDLE")
				.expectNext("ITZY")
				.expectNext("BlackPink")
				.verifyComplete();
	}

	@Test
	public void createAFlux_fromIterable() {
		List<String> girlGroupList = new ArrayList<>();
		girlGroupList.add("Ive");
		girlGroupList.add("Lesserafim");
		girlGroupList.add("Newjeans");
		girlGroupList.add("G-IDLE");
		girlGroupList.add("ITZY");
		girlGroupList.add("BlackPink");
		Flux<String> girlGroupsFlux = Flux.fromIterable(girlGroupList);

		StepVerifier.create(girlGroupsFlux)
				.expectNext("Ive")
				.expectNext("Lesserafim")
				.expectNext("Newjeans")
				.expectNext("G-IDLE")
				.expectNext("ITZY")
				.expectNext("BlackPink")
				.verifyComplete();
	}

	@Test
	public void createAFlux_fromStream() {
		Stream<String> girlGroupsStream = Stream.of("Ive", "Lesserafim", "Newjeans", "G-IDLE", "ITZY", "BlackPink");
		Flux<String> girlGroupsFlux = Flux.fromStream(girlGroupsStream);

		StepVerifier.create(girlGroupsFlux)
				.expectNext("Ive")
				.expectNext("Lesserafim")
				.expectNext("Newjeans")
				.expectNext("G-IDLE")
				.expectNext("ITZY")
				.expectNext("BlackPink")
				.verifyComplete();
	}

	@Test
	public void createAFlux_range() {
		Flux<Integer> intervalFlux = Flux.range(1,5);

		StepVerifier.create(intervalFlux)
				.expectNext(1)
				.expectNext(2)
				.expectNext(3)
				.expectNext(4)
				.expectNext(5)
				.verifyComplete();
	}
}
