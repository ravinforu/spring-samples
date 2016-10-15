package com.crossover.trial.weather.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Mean {
    int minMean() default 0;
    int maxMean() default 0;
    boolean isAvoidMax() default false;
}