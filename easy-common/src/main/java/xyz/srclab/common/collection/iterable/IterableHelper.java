package xyz.srclab.common.collection.iterable;

import org.apache.commons.collections4.IterableUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IterableHelper {

    public static <E> List<E> asList(Iterable<E> iterable) {
        return iterable instanceof List ?
                (List<E>) iterable : IterableUtils.toList(iterable);
    }

    public static <E> Collection<E> asCollection(Iterable<E> iterable) {
        return iterable instanceof Collection ?
                (Collection<E>) iterable : IterableUtils.toList(iterable);
    }

    public static <E> Set<E> asSet(Iterable<E> iterable) {
        return iterable instanceof Set ?
                (Set<E>) iterable : toSet(iterable);
    }

    private static <E> Set<E> toSet(Iterable<E> iterable) {
        Set<E> set = new HashSet<>();
        for (E e : iterable) {
            set.add(e);
        }
        return set;
    }
}