package com.money.spier.api.core.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorized {

  boolean writeExpenses() default false;

  boolean readExpenses() default false;

  boolean writeIncomes() default false;

  boolean readIncomes() default false;

}
