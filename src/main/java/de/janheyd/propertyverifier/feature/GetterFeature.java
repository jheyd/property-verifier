package de.janheyd.propertyverifier.feature;

import java.util.List;
import java.util.function.Function;

public class GetterFeature<T, U> implements ObjectFeature<T, U> {

	private Function<T, U> getter;

	public GetterFeature(Function<T, U> getter) {
		this.getter = getter;
	}

	@Override
	public void verify(Function<U, T> constructor, List<U> values) {
		if(!values.stream().allMatch(value -> constructor.andThen(getter).apply(value).equals(value)))
			throw new AssertionError("Constructor <=> Getter verification failed");
	}
}
