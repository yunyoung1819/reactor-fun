package sia5;

import lombok.Data;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * 리액티브 타입으로부터 데이터 필터링하기
 */
public class FluxTransformingTests {

	/**
	 * skip() 오퍼레이션은 지정된 수의 메시지를 건너띈 후에
	 * 나머지 메시지를 결과 Flux로 전달한다
	 */
	@Test
	public void skipAFew() {
		Flux<String> skipFlux = Flux
				.just("one", "two", "skip a few", "ninety nine", "one hundred")
				.skip(3);
		StepVerifier.create(skipFlux)
				.expectNext("ninety nine", "one hundred")
				.verifyComplete();
	}

	/**
	 * 이런 형태의 skip() 오퍼레이션은 지정된 시간이 경과할 때까지 기다렸다가
	 * 결과 Flux로 메시지를 전달한다
	 */
	@Test
	public void skipAFewSeconds() {
		Flux<String> skipFlux = Flux.just(
				"one", "two", "skip a few", "ninety nine", "one hundred")
				.delayElements(Duration.ofSeconds(1))
				.skip(Duration.ofSeconds(4));
		StepVerifier.create(skipFlux)
				.expectNext("ninety nine", "one hundred")
				.verifyComplete();
	}

	/**
	 * take() 오퍼레이션은 입력 Flux로부터 처음부터 지정된 수의 메시지만
	 * 전달하고 구독을 취소시킨다
	 */
	@Test
	public void take() {
		Flux<String> cityFlux = Flux
				.just("NewYork", "London", "Paris", "Tyokyo", "Seoul", "Beijing")
				.take(3);
		StepVerifier.create(cityFlux)
				.expectNext("NewYork", "London", "Paris")
				.verifyComplete();
	}


	/** 이런 형태의 take() 오퍼레이션은 일정 시간이 경과될 동안만
	 * 결과 Flux로 메시지를 전달한다
	 */
	@Test
	public void takeAFewSeconds() {
		Flux<String> nationalParkFlux = Flux.just("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
				.delayElements(Duration.ofSeconds(1))
				.take(Duration.ofMillis(3500));
		StepVerifier.create(nationalParkFlux)
				.expectNext("Yellowstone", "Yosemite", "Grand Canyon")
				.verifyComplete();
	}

	/**
	 * 지정된 조건식에 일치되는 메시지만 결과 Flux가 수신하도록 입력 Flux를 필터링 할 수 있다
	 */
	@Test
	public void filter() {
		Flux<String> nationalParkFlux = Flux
				.just("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
				.filter(np -> !np.contains(" "));
		StepVerifier.create(nationalParkFlux)
				.expectNext("Yellowstone", "Yosemite", "Zion")
				.verifyComplete();
	}

	/**
	 * distinct() 오퍼레이션을 사용하면 발행된 적이 없는(중복되지 않는) 소스 Flux의 항목만 발행하는 결과 Flux를 생성한다
	 */
	@Test
	public void distinct() {
		Flux<String> animalFlux = Flux.just("dog", "cat", "bird", "dog", "bird", "anteater")
				.distinct();
		StepVerifier.create(animalFlux)
				.expectNext("dog", "cat", "bird", "anteater")
				.verifyComplete();
	}

	@Test
	public void map() {
		Flux<Player> playerFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
				.map(n -> {
					String[] split = n.split("\\s");
					return new Player(split[0], split[1]);
				});

		StepVerifier.create(playerFlux)
				.expectNext(new Player("Michael", "Jordan"))
				.expectNext(new Player("Scottie", "Pippen"))
				.expectNext(new Player("Steve", "Kerr"))
				.verifyComplete();
	}

	/**
	 * flatMap() 오퍼레이션은 수행 도중 생성되는 임시 Flux를 사용해서 변환을 수행하므로
	 * 비동기 변환이 가능하다
	 */
	@Test
	public void flatMap() {
		Flux<Player> playerFlux = Flux
				.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
				.flatMap(n -> Mono.just(n)
						.map(p -> {
							String[] split = p.split("\\s");
							return new Player(split[0], split[1]);
						})
						.subscribeOn(Schedulers.parallel()));

		List<Player> playerList = Arrays.asList(
				new Player("Michael", "Jordan"),
				new Player("Scottie", "Pippen"),
				new Player("Steve", "Kerr"));

		StepVerifier.create(playerFlux)
				.expectNextMatches(p -> playerList.contains(p))
				.expectNextMatches(p -> playerList.contains(p))
				.expectNextMatches(p -> playerList.contains(p))
				.verifyComplete();
	}

	@Data
	private static class Player {
		private final String firstName;
		private final String lastName;
	}
}


