package sia5;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 리액티브 스트림 데이터의 버퍼링하기
 */
public class FluxBufferingTests {

	/**
	 * buffer() 오퍼레이션은 지정된 최대 크기의 리스트(입력 Flux로부터 수집된)로 된 Flux를 생성한다
	 */
	@Test
	public void buffer() {
		Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");
		Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);
		StepVerifier.create(bufferedFlux)
				.expectNext(Arrays.asList("apple", "orange", "banana"))
				.expectNext(Arrays.asList("kiwi", "strawberry"))
				.verifyComplete();
	}

	@Test
	public void bufferFlatMap() {
		Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry")
				.buffer(3)
				.flatMap(x ->
						Flux.fromIterable(x)
								.map(y -> y.toUpperCase())
								.subscribeOn(Schedulers.parallel())
								.log()
				);
		fruitFlux.subscribe();
	}

	/**
	 * CollectionList() 오퍼레이션은 입력 Flux가 방출한 모든 메시지를 갖는 List의 Mono를 생성한다
	 */
	@Test
	public void collectList() {
		Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");
		Mono<List<String>> fruitListMono = fruitFlux.collectList();
		StepVerifier
				.create(fruitListMono)
				.expectNext(Arrays.asList("apple", "orange", "banana", "kiwi", "strawberry"))
				.verifyComplete();
	}

	/**
	 * collectMap() 오퍼레이션은 Map을 포함하는 Mono를 생성한다
	 * 이때 입력 Flux가 방출한 메시지가 해당 Map의 항목으로 저장되며 각 항목의 키는 입력 메시지의 특성에 따라 추출된다
	 */
	@Test
	public void collectMap() {
		Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");
		Mono<Map<Character, String>> animalMapMono = animalFlux.collectMap(a -> a.charAt(0));
		StepVerifier
				.create(animalMapMono)
				.expectNextMatches(map -> {
					return
							map.size() == 3 &&
							map.get('a').equals("aardvark") &&
							map.get('e').equals("eagle") &&
							map.get('k').equals("kangaroo");
				}).verifyComplete();
	}
}
