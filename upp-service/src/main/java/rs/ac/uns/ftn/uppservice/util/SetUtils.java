package rs.ac.uns.ftn.uppservice.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetUtils {

    public static <T> Set<T> fromListToSet(List<T> list) {
        HashSet<T> set = new HashSet<>();
        list.stream().forEach(element -> set.add(element));
        return set;
    }
}
