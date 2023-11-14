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

@Fork(value = 1, jvmArgs = {"-Xms4G", "-Xmx4G", "-Xint"})
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 500, timeUnit = MILLISECONDS)
@Measurement(iterations = 3, time = 500, timeUnit = MILLISECONDS)
@OutputTimeUnit(MILLISECONDS)
public class AddBenchmark {

    @State(Scope.Thread)
    public static class AddState {
        ArrayList<Integer> original;
        ArrayList<Integer> toAdd;

        @Setup(Level.Trial)
        public void doSetup() {
            original = new ArrayList<>();
            toAdd = IntStream.range(0, 100_000).boxed().collect(Collectors.toCollection(ArrayList::new));
        }
    }


    @SuppressWarnings("UseBulkOperation")
    @Benchmark
    public ArrayList<Integer> arrayListAdd(AddState state) {
        for (int item : state.toAdd) {
            state.original.add(item);
        }
        return state.original;
    }

    @Benchmark
    public ArrayList<Integer> arrayListAddAll(AddState state) {
        state.original.addAll(state.toAdd);
        return state.original;

    }
}
