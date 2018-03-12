package de.janheyd.propertyverifier.feature;

import java.util.List;
import java.util.function.Function;

public class HashCodeFeature<T, U> implements ObjectFeature<T, U> {

	private boolean guaranteedDistinct;

	public HashCodeFeature(boolean guaranteedDistinct) {
		this.guaranteedDistinct = guaranteedDistinct;
	}

	public HashCodeFeature() {
		this(false);
	}

	@Override
	public void verify(Function<U, T> constructor, List<U> values) {
		if(!equalValuesHaveEqualHashCode(constructor, values))
			throw new AssertionError("HashCode verification failed");
		if(guaranteedDistinct && distinctValuesProduceSameHashCode(constructor, values))
			throw new AssertionError("HashCode verification failed");

	}

	private boolean distinctValuesProduceSameHashCode(Function<U, T> constructor, List<U> values) {
		return values.stream().map(constructor).map(Object::hashCode).distinct().count() != values.size();
	}

	private boolean equalValuesHaveEqualHashCode(Function<U, T> constructor, List<U> values) {
		return values.stream().allMatch(value -> hashCodeFor(constructor, value) == hashCodeFor(constructor, value));
	}

	private int hashCodeFor(Function<U, T> constructor, U value) {
		return constructor.apply(value).hashCode();
	}
}
