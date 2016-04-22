package com.github.becausetesting.reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;

public class RefelectionUtils {

	public boolean isInterfaceFrom(Class instance, Class interfaceclass) {
		return interfaceclass.isAssignableFrom(instance);
	}

	public static Object invokeMethod(Object instance, Class clazz, String methodname, Class[] parameterTypes,
			Object... parameterValues) {
		Object returnObj = null;
		try {
			Method declaredMethod = null;
			if (parameterTypes != null) {
				declaredMethod = clazz.getMethod(methodname, parameterTypes);
			} else {
				declaredMethod = clazz.getMethod(methodname);
			}
			if (parameterValues.length > 0) {
				returnObj = declaredMethod.invoke(instance, parameterValues);
			} else {
				returnObj = declaredMethod.invoke(instance);
			}
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

	public static Object createContractorInstance(Class<?> forName, Class<?>[] parametersTypes,
			Object... parameterValues) {

		Object newInstance = null;
		try {
			Constructor<?> constructor = null;
			if (parametersTypes != null) {
				constructor = forName.getConstructor(Reporter.class, Formatter.class, boolean.class);
				constructor = forName.getConstructor(parametersTypes);

			} else {
				constructor = forName.getConstructor();
			}
			if (parameterValues.length > 0) {
				newInstance = constructor.newInstance(parameterValues);
			} else {
				newInstance = constructor.newInstance();
			}
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
