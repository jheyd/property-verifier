package de.janheyd.propertyverifier.feature;

import java.util.List;
import java.util.function.Function;

public class HashCodeFeature<T, U> implements ObjectFeature<T, U> {

	@Override
	public void verify(Function<U, T> constructor, List<U> values) {
		if(!equalValuesHaveEqualHashCode(constructor, values))
			throw new RuntimeException("HashCode verification failed");
	}

	private boolean equalValuesHaveEqualHashCode(Function<U, T> constructor, List<U> values) {
		return values.stream().allMatch(value -> hashCodeFor(constructor, value) == hashCodeFor(constructor, value));
	}

	private int hashCodeFor(Function<U, T> constructor, U value) {
		return constructor.apply(value).hashCode();
	}
}
