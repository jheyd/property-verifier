package de.janheyd.propertyverifier;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PropertyVerifier {

	public <T, U> ConstructablePropertyVerifier<T, U> withConstructor(Function<U, T> constructor) {
		return new ConstructablePropertyVerifier<>(constructor);
	}

	public <T, U> ConstructablePropertyVerifier<T, U> withSetter(BiConsumer<T, U> setter, Supplier<T> noArgConstructor) {
		return new ConstructablePropertyVerifier<>(pseudoConstructor(setter, noArgConstructor));
	}

	private <T, U> Function<U, T> pseudoConstructor(BiConsumer<T, U> setter, Supplier<T> noArgConstructor) {
		return x -> {
			T t = noArgConstructor.get();
			setter.accept(t, x);
			return t;
		};
	}
}
