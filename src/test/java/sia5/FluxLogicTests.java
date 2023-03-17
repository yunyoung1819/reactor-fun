package sia5;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * 리액티브 타입 로직 오퍼레이션 수행
 * Mono나 Flux가 발행한 항목이 어떤 조건과 일치하는지만 알아야할 경우가 있다. 이때는 all()이나 any() 오퍼레이션이 그런 로직을 수행한다.
 */
public class FluxLogicTests {

	/**
	 * 모든 메시지가 조건을 충족하는지 확인하기 위해 all() 오퍼레이션으로 Flux를 검사할 수 있다.
	 */
	@Test
	public void all() {
		Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");
		Mono<Boolean> hasAMono = animalFlux.all(a -> a.contains("a"));
		StepVerifier.create(hasAMono)
				.expectNext(true)
				.verifyComplete();

		Mono<Boolean> hasKMono = animalFlux.all(a -> a.contains("k"));
		StepVerifier.create(hasKMono)
				.expectNext(false)
				.verifyComplete();
	}

	/**
	 * 최소한 하나의 메시지가 조건을 충족하는지 확인하기 위해 any() 오퍼레이션으로 Flux를 검사할 수 있다
	 */
	@Test
	public void any() {
		Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");
		Mono<Boolean> hasTMono = animalFlux.any(a -> a.contains("t"));
		StepVerifier.create(hasTMono)
				.expectNext(true)
				.verifyComplete();

		Mono<Boolean> hasYMono = animalFlux.any(a -> a.contains("y"));
		StepVerifier.create(hasYMono)
				.expectNext(false)
				.verifyComplete();
	}
}
