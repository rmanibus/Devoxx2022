package fr.sciam;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class EqualityTest {

    @Test
    public void setContainsTest() {
        List<String> list = new ArrayList<>(List.of("one", "two", "three"));
        Set<List<String>> set = new HashSet<>(Set.of(list));
        assertThat(set.contains(list)).isTrue();
        list.add("four");
        assertThat(set.contains(list)).isFalse();
    }
}
