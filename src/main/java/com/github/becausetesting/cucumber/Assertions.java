package com.github.becausetesting.cucumber;

import cucumber.runtime.CucumberException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * ClassName: Assertions  
 * Function: TODO ADD FUNCTION.  
 * Reason: TODO ADD REASON 
 * date: Apr 23, 2016 6:10:43 PM  
 * @author alterhu2020@gmail.com
 * @version 1.0.0
 * @since JDK 1.8
 */
public class Assertions {
    public static void assertNoCucumberAnnotatedMethods(Class clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
            	String name = annotation.annotationType().getName();
                if (annotation.annotationType().getName().startsWith("cucumber")) {
                    throw new CucumberException(
                            "\n\n" +
                                    "Classes annotated with @RunWith(Cucumber.class) must not define any\n" +
                                    "Step Definition or Hook methods. Their sole purpose is to serve as\n" +
                                    "an entry point for JUnit. Step Definitions and Hooks should be defined\n" +
                                    "in their own classes. This allows them to be reused across features.\n" +
                                    "Offending class: " + clazz + "\n"
                    );
                }
            }
        }
    }
}
