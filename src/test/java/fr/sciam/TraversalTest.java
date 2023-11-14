package fr.sciam;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class TraversalTest {

    @Test
    public void iterableTest() {
        List<String> list = new ArrayList<>(List.of("one", "two", "three"));
        assertThatThrownBy(() -> {
            for (var item : list) {
                if ("three".equals(item))
                    list.add("four");
            }
        }).isInstanceOf(ConcurrentModificationException.class);
    }

    @Test
    public void forLoopTest() {
        // Sans Iterator:
        List<String> list = new ArrayList<>(List.of("one", "two", "three"));
        for (int index = 0; index < list.size(); index++) {
            if ("three".equals(list.get(index)))
                list.add("four");
        }
        assertThat(list).isEqualTo(List.of("one", "two", "three", "four"));
    }

    @Test
    public void listIteratorTest() {
        // Avec ListIterator (attention, on ajoute pas a la fin de la liste):
        List<String> list = new ArrayList<>(List.of("one", "two", "three"));
        for (var iterator = list.listIterator(); iterator.hasNext(); ) {
            if ("three".equals(iterator.next()))
                iterator.add("four");
        }
        assertThat(list).isEqualTo(List.of("one", "two", "three", "four"));
    }

}
