package com.github.becausetesting.reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RefelectionUtils {

	public boolean isInterfaceFrom(Class instance, Class interfaceclass) {
		return interfaceclass.isAssignableFrom(instance);
	}

	public static Class getclass(String className) {
		Class c = null;
		try {
			c = Class.forName(className);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return c;
	}

	public static Object getMethod(Object instance, String methodname, Object... parameterValues) {
		Object returnObj = null;
		try {
			Class parameterTypes[] = null;
			if (parameterValues != null) {
				int length = parameterValues.length;
				parameterTypes = new Class[length];
				for (int k = 0; k < length; k++) {
					parameterTypes[k] = parameterValues[k].getClass();
				}
			}
			Method declaredMethod = instance.getClass().getDeclaredMethod(methodname, parameterTypes);
			returnObj = declaredMethod.invoke(instance, parameterValues);

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnObj;
	}

	public static Object getContractorInstance(Class<?> forName, Object... parameterValues) {
		Object newInstance = null;
		try {
			Class parameterTypes[] = null;
			if (parameterValues != null) {
				int length = parameterValues.length;
				parameterTypes = new Class[length];
				for (int k = 0; k < length; k++) {
					parameterTypes[k] = parameterValues[k].getClass();
				}
			}
			Constructor<?> constructor = forName.getConstructor(parameterTypes);
			newInstance = constructor.newInstance(parameterValues);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newInstance;
	}

}
