package Model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ToList {

    public static <E> List<Piece<E>> toList(Stream<Piece<E>> stream) {
        return stream.collect(Collectors.toList());
    }
}
