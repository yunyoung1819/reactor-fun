package sia5;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

/**
 * 리액티브 타입 조합하기
 * 두 개의 리액티브 타입을 결합해야 하거나 하나의 Flux를 두 개 이상의 리액티브 타입으로 분할해야 하는 경우가 있을 수 있다.
 * 여기서는 리액터의 Flux나 Mono를 결합하거나 분할하는 오퍼레이션을 알아본다.
 */
public class FluxMergingTests {
	@Test
	public void mergeFluxes() {
		Flux<String> characterFlux = Flux
				.just("IronMan", "Hulk", "Thor")
				.delayElements(Duration.ofMillis(500));
		Flux<String> foodFlux = Flux
				.just("Steak", "Grape", "Beer")
				.delaySubscription(Duration.ofMillis(250))
				.delayElements(Duration.ofMillis(500));

		Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);

		StepVerifier.create(mergedFlux)
				.expectNext("IronMan")
				.expectNext("Steak")
				.expectNext("Hulk")
				.expectNext("Grape")
				.expectNext("Thor")
				.expectNext("Beer")
				.verifyComplete();
	}

	@Test
	public void zipPluxes() {
		Flux<String> characterFlux = Flux.just("IronMan", "Ultron", "Thor");
		Flux<String> foodFlux = Flux.just("Steak", "Oil", "Beer");
		Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux, foodFlux);

		StepVerifier.create(zippedFlux)
				.expectNextMatches(p ->
						p.getT1().equals("IronMan") &&
						p.getT2().equals("Steak"))
				.expectNextMatches(p ->
						p.getT1().equals("Ultron") &&
						p.getT2().equals("Oil"))
				.expectNextMatches(p ->
						p.getT1().equals("Thor") &&
						p.getT2().equals("Beer"))
				.verifyComplete();
	}

	@Test
	public void zipFluxesToObject() {
		Flux<String> characterFlux = Flux
				.just("IronMan", "Ultron", "Thor");
		Flux<String> foodFlux = Flux
				.just("Steak", "Oil", "Beer");
		Flux<String> zippedFlux =
				Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f);

		StepVerifier.create(zippedFlux)
				.expectNext("IronMan eats Steak")
				.expectNext("Ultron eats Oil")
				.expectNext("Thor eats Beer")
				.verifyComplete();
	}

	@Test
	public void firstFlux() {
		Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth")
				.delaySubscription(Duration.ofMillis(100));
		Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");

		Flux<String> firstFlux = Flux.first(slowFlux, fastFlux);

		StepVerifier.create(firstFlux)
				.expectNext("hare")
				.expectNext("cheetah")
				.expectNext("squirrel")
				.verifyComplete();
	}
}
