package com.cu.sci.lambdaserver.utils.helpers;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectionHelper {
    public static <T> Collection<T> clearNulls(Collection<T> collection) {
        collection.removeIf(Objects::isNull);
        return collection;
    }

    public static <T> Set<T> clearNullsSet(Set<T> collection) {
        return collection.stream().filter(Objects::nonNull).collect(Collectors.toSet());
    }
}
