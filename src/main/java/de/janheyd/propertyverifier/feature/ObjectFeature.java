package de.janheyd.propertyverifier.feature;

import java.util.List;
import java.util.function.Function;

public interface ObjectFeature<T, U> {

	void verify(Function<U, T> constructor, List<U> values);
}
