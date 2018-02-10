import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class DoOneEachTest {


    private List<Integer> processMe(List<Integer> input) {
        return Collections.<Integer>emptyList();
    }

    @Test
    public void shallExecuteSideEffectsCallback() {

        Flux<Integer> result = Mono.just(Arrays.asList(1, 2))
                //.doOnEach(sig -> System.out.println("SIGNAL beforeMap " + sig))// <- if enabled than everything is fine
                .map(x -> processMe(x))
                .doOnEach(sig -> {throw new RuntimeException("expected");})
                .flatMapIterable(Function.identity());


        StepVerifier.create(result).expectError().verify();
    }
}
