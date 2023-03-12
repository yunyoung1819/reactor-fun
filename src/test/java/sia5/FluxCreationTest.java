package sia5;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 리액티브 타입 생성하기
 */
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

	@Test
	public void createAFlux_interval() {
		Flux<Long> intervalFlux =
				Flux.interval(Duration.ofSeconds(1))
						.take(5);

		StepVerifier.create(intervalFlux)
				.expectNext(0L)
				.expectNext(1L)
				.expectNext(2L)
				.expectNext(3L)
				.expectNext(4L)
				.verifyComplete();
	}
}
