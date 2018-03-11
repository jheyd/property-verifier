package de.janheyd.propertyverifier.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EqualsFeature<T, U> implements ObjectFeature<T, U> {

	@Override
	public void verify(Function<U, T> constructor, List<U> values) {
		if (!objectsWithEqualPropertiesAreEqual(constructor, values))
			throw new RuntimeException("Equals Verification failed");
		if (!objectsWithDistinctPropertiesAreDistinct(constructor, values))
			throw new RuntimeException("Equals Verification failed");
	}

	private boolean objectsWithEqualPropertiesAreEqual(Function<U, T> constructor, List<U> values) {
		return values.stream().allMatch(value -> constructor.apply(value).equals(constructor.apply(value)));
	}

	private boolean objectsWithDistinctPropertiesAreDistinct(Function<U, T> constructor, List<U> values) {
		List<T> objects = values.stream().map(constructor).collect(Collectors.toList());
		return countDistinct(objects) == values.size();
	}

	/*
	 * Since we only verfiy equals and not hashCode we have to write our own method to count distinct elements
	 * instead of relying on java built-ins like Stream::distinct (which uses hashCode)
	 */
	private int countDistinct(List<T> objects) {
		List<T> distinct = new ArrayList<>();
		for(T t: objects)
			if(distinct.stream().noneMatch(t::equals))
				distinct.add(t);
		return distinct.size();
	}

}
