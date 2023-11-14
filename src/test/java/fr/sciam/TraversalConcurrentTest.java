package fr.sciam;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class TraversalConcurrentTest {


    @Test
    public void synchronizedListTest() {
        assertThatThrownBy(() -> {
            List<String> list = Collections.synchronizedList(new ArrayList<>(List.of("one", "two", "three")));
            for (var item : list) {
                list.add("four");
            }
        }).isInstanceOf(ConcurrentModificationException.class);
    }

    @Test
    public void copyOnWriteArrayListTest() {
        List<String> list = new CopyOnWriteArrayList<>(List.of("one", "two", "three"));
        for (var item : list) {
            if ("three".equals(item))
                list.add("four");
        }
        assertThat(list).isEqualTo(List.of("one", "two", "three", "four"));
    }

    @Test
    public void copyOnWriteListIteratorTest() {
        assertThatThrownBy(() -> {
            List<String> list = new CopyOnWriteArrayList<>(List.of("one", "two", "three"));
            for (var iterator = list.listIterator(); iterator.hasNext(); ) {
                if ("three".equals(iterator.next()))
                    iterator.add("four");
            }
        }).isInstanceOf(UnsupportedOperationException.class);

    }
}
