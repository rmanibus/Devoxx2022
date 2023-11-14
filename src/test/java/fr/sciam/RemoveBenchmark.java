package fr.sciam;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Fork(value = 1, jvmArgs = {"-Xms4G", "-Xmx4G"})
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 500, timeUnit = MILLISECONDS)
@Measurement(iterations = 3, time = 500, timeUnit = MILLISECONDS)
@OutputTimeUnit(MILLISECONDS)
public class RemoveBenchmark {

    @State(Scope.Thread)
    public static class RemoveState {
        ArrayList<Integer> original;
        ArrayList<Integer> toRemove;

        @Setup(Level.Trial)
        public void doSetup() {
            original = IntStream.range(0, 100_000).boxed().collect(Collectors.toCollection(ArrayList::new));
            toRemove = IntStream.range(0, 1_000).boxed().collect(Collectors.toCollection(ArrayList::new));
        }

    }


    @SuppressWarnings("UseBulkOperation")
    @Benchmark
    public void arrayListRemove(RemoveState state) {
        for (Integer item : state.toRemove) {
            state.original.remove(item);
        }
    }

    @Benchmark
    public void arrayListRemoveAll(RemoveState state) {
        state.original.removeAll(state.toRemove);
    }

    @Benchmark
    public void arrayListRemoveIf(RemoveState state) {
        state.original.removeIf(item -> item < 1000);
    }
}
