package org.moosetechnology.jdt2famix;

import java.util.stream.Stream;

import org.moosetechnology.model.famix.Class;
import org.moosetechnology.model.famix.ContainerEntity;
import org.moosetechnology.model.famix.Method;
import org.moosetechnology.model.famix.Type;

/*
 * This is a utility class for helping us navigate through FAMIX models
 * These methods would be better suited for the FAMIX classes, but as those get generated out of Pharo code,
 * we factored the navigation methods here 
 */
public class Famix {
	public static Type superclassOf(Type type) {
		return type.getSuperInheritances()
			.stream()
			.filter(t -> 
				! (t.getSuperclass() instanceof Class) || 
				! ((Class) t.getSuperclass()).getIsInterface())
			.findAny()
			.get().
			getSuperclass();
	}

	public static Stream<Type> interfacesOf(Type type) {
		 return type.getSuperInheritances()
				.stream()
				.filter(i -> 
				(i.getSuperclass() instanceof Class) && 
				((Class) i.getSuperclass()).getIsInterface())
				.map(i -> i.getSuperclass());
	}
	
	
	public static String qualifiedNameOf(Method method) {
		return qualifiedNameOf(method.getParentType()) + "." + method.getSignature();
	}
	public static String qualifiedNameOf(Type type) {
		if (type.getContainer() instanceof Method)
			return qualifiedNameOf((Method) type.getContainer()) + "." + type.getName();		
		if (type.getContainer() instanceof Type)
			return qualifiedNameOf((Type) type.getContainer()) + "." + type.getName();
		return qualifiedNameOf(type.getContainer()) + "." + type.getName();
		
	}
	public static String qualifiedNameOf(ContainerEntity container) {
		return container.getName();
	}
}
