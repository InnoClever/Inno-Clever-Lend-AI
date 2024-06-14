/*
 * (C) Copyright 2013 medifilm AG
 *
 * All rights reserved
 *
 * Licensed Materials - Property of medifilm AG.
 */
package ai.inno.clever.service.lock;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;



/**
 * Annotation used for forcing the usage of the Pesimistic lock.
 */
@Documented
@Retention (RUNTIME)
@Target({METHOD})
public @interface PessimisticLock {
    boolean throwException() default false;
    String keyExpression();
    Class<?> type()  default Void.class;
}
