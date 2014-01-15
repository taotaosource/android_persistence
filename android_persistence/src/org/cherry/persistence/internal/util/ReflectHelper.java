package org.cherry.persistence.internal.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.cherry.persistence.AssertionFailure;
import org.cherry.persistence.PropertyNotFoundException;


/**
 * Utility class for various reflection operations.
 * 
 * 
 */
public final class ReflectHelper {

	// TODO: this dependency is kinda Bad

	public static final Class<?>[] NO_PARAM_SIGNATURE = new Class[0];
	public static final Object[] NO_PARAMS = new Object[0];

	public static final Class<?>[] SINGLE_OBJECT_PARAM_SIGNATURE = new Class[] { Object.class };

	private static final Method OBJECT_EQUALS;
	private static final Method OBJECT_HASHCODE;

	static {
		Method eq;
		Method hash;
		try {
			eq = extractEqualsMethod(Object.class);
			hash = extractHashCodeMethod(Object.class);
		} catch (Exception e) {
			throw new AssertionFailure("Could not find Object.equals() or Object.hashCode()", e);
		}
		OBJECT_EQUALS = eq;
		OBJECT_HASHCODE = hash;
	}

	/**
	 * Disallow instantiation of ReflectHelper.
	 */
	private ReflectHelper() {
	}

	/**
	 * Encapsulation of getting hold of a class's {@link Object#equals equals}
	 * method.
	 * 
	 * @param clazz
	 *            The class from which to extract the equals method.
	 * @return The equals method reference
	 * @throws NoSuchMethodException
	 *             Should indicate an attempt to extract equals method from
	 *             interface.
	 */
	public static Method extractEqualsMethod(Class<?> clazz) throws NoSuchMethodException {
		return clazz.getMethod("equals", SINGLE_OBJECT_PARAM_SIGNATURE);
	}

	/**
	 * Encapsulation of getting hold of a class's {@link Object#hashCode
	 * hashCode} method.
	 * 
	 * @param clazz
	 *            The class from which to extract the hashCode method.
	 * @return The hashCode method reference
	 * @throws NoSuchMethodException
	 *             Should indicate an attempt to extract hashCode method from
	 *             interface.
	 */
	public static Method extractHashCodeMethod(Class<?> clazz) throws NoSuchMethodException {
		return clazz.getMethod("hashCode", NO_PARAM_SIGNATURE);
	}

	/**
	 * Determine if the given class defines an {@link Object#equals} override.
	 * 
	 * @param clazz
	 *            The class to check
	 * @return True if clazz defines an equals override.
	 */
	public static boolean overridesEquals(Class<?> clazz) {
		Method equals;
		try {
			equals = extractEqualsMethod(clazz);
		} catch (NoSuchMethodException nsme) {
			return false; // its an interface so we can't really tell
							// anything...
		}
		return !OBJECT_EQUALS.equals(equals);
	}

	/**
	 * Determine if the given class defines a {@link Object#hashCode} override.
	 * 
	 * @param clazz
	 *            The class to check
	 * @return True if clazz defines an hashCode override.
	 */
	public static boolean overridesHashCode(Class<?> clazz) {
		Method hashCode;
		try {
			hashCode = extractHashCodeMethod(clazz);
		} catch (NoSuchMethodException nsme) {
			return false; // its an interface so we can't really tell
							// anything...
		}
		return !OBJECT_HASHCODE.equals(hashCode);
	}

	/**
	 * Determine if the given class implements the given interface.
	 * 
	 * @param clazz
	 *            The class to check
	 * @param intf
	 *            The interface to check it against.
	 * @return True if the class does implement the interface, false otherwise.
	 */
	public static boolean implementsInterface(Class<?> clazz, Class<?> intf) {
		assert intf.isInterface() : "Interface to check was not an interface";
		return intf.isAssignableFrom(clazz);
	}

	/**
	 * Is this member publicly accessible.
	 * <p/>
	 * Short-hand for {@link #isPublic(Class, Member)} passing the member +
	 * {@link Member#getDeclaringClass()}
	 * 
	 * @param member
	 *            The member to check
	 * @return True if the member is publicly accessible.
	 */
	public static boolean isPublic(Member member) {
		return isPublic(member.getDeclaringClass(), member);
	}

	/**
	 * Is this member publicly accessible.
	 * 
	 * @param clazz
	 *            The class which defines the member
	 * @param member
	 *            The memeber.
	 * @return True if the member is publicly accessible, false otherwise.
	 */
	public static boolean isPublic(Class<?> clazz, Member member) {
		return Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(clazz.getModifiers());
	}

	/**
	 * Is this member static .
	 */
	public static boolean isStatic(Member member) {
		return Modifier.isStatic(member.getModifiers());
	}

	/**
	 * Retrieve the default (no arg) constructor from the given class.
	 * 
	 * @param clazz
	 *            The class for which to retrieve the default ctor.
	 * @return The default constructor.
	 * @throws PropertyNotFoundException
	 *             Indicates there was not publicly accessible, no-arg
	 *             constructor (todo : why PropertyNotFoundException???)
	 */
	public static <T> ObjectConstructor<T> getDefaultConstructor(Class<T> clazz) throws PropertyNotFoundException {
		if (isAbstractClass(clazz)) {
			return null;
		}
		ObjectConstructor<T> defaultConstructor = newDefaultConstructor(clazz);
		if (defaultConstructor != null) {
			return defaultConstructor;
		}
		return newUnsafeAllocator(clazz);
	}

	private static <T> ObjectConstructor<T> newDefaultConstructor(Class<T> clazz) {
		try {
			final Constructor<T> constructor = clazz.getDeclaredConstructor(NO_PARAM_SIGNATURE);
			if (!isPublic(clazz, constructor)) {
				constructor.setAccessible(true);
			}
			return new ObjectConstructor<T>() {

				@Override
				public T construct() {
					try {
						Object[] args = null;
						return (T) constructor.newInstance(args);
					} catch (InstantiationException e) {
						throw new RuntimeException("Failed to invoke " + constructor + " with no args", e);
					} catch (InvocationTargetException e) {
						throw new RuntimeException("Failed to invoke " + constructor + " with no args", e.getTargetException());
					} catch (IllegalAccessException e) {
						throw new AssertionError(e);
					}
				}
			};
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	private static <T> ObjectConstructor<T> newUnsafeAllocator(final Class<? super T> type) {
		return new ObjectConstructor<T>() {
			private final UnsafeAllocator unsafeAllocator = UnsafeAllocator.create();

 			@SuppressWarnings("unchecked")
			public T construct() {
				try {
					Object newInstance = unsafeAllocator.newInstance(type);
					return (T) newInstance;
				} catch (Exception e) {
					throw new RuntimeException(
							("Unable to invoke no-args constructor for " + type + ". " + "Register an InstanceCreator with  for this type may fix this problem."),
							e);
				}
			}
		};
	}

	/**
	 * Determine if the given class is declared abstract.
	 * 
	 * @param clazz
	 *            The class to check.
	 * @return True if the class is abstract, false otherwise.
	 */
	public static boolean isAbstractClass(Class<?> clazz) {
		int modifier = clazz.getModifiers();
		return Modifier.isAbstract(modifier) || Modifier.isInterface(modifier);
	}

	/**
	 * Determine is the given class is declared final.
	 * 
	 * @param clazz
	 *            The class to check.
	 * @return True if the class is final, flase otherwise.
	 */
	public static boolean isFinalClass(Class<?> clazz) {
		return Modifier.isFinal(clazz.getModifiers());
	}

	public static Method getMethod(Class<?> clazz, Method method) {
		try {
			return clazz.getMethod(method.getName(), method.getParameterTypes());
		} catch (Exception e) {
			return null;
		}
	}

}
