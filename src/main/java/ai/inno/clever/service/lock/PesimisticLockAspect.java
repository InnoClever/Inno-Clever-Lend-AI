/*
 * (C) Copyright 2012 medifilm AG
 *
 * All rights reserved
 *
 * Licensed Materials - Property of medifilm AG.
 */
package ai.inno.clever.service.lock;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;

/**
 * Aspect used to perform a pessimitic lock.
 *
 */
@Aspect
@Component
@Order(6)
public class PesimisticLockAspect {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(PesimisticLockAspect.class);
    private KeyedMutexes<LockKey> mutexes = new KeyedMutexes<>();

    private SpelExpressionParser parser = new SpelExpressionParser();
    private MethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();

    private static class LockKey {

        String key;
        Class<?> _class;

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((_class == null) ? 0 : _class.hashCode());
            result = prime * result + ((key == null) ? 0 : key.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            LockKey other = (LockKey) obj;
            if (_class == null) {
                if (other._class != null)
                    return false;
            } else if (!_class.equals(other._class))
                return false;
            if (key == null) {
                if (other.key != null)
                    return false;
            } else if (!key.equals(other.key))
                return false;
            return true;
        }

        public LockKey(String key, Class<?> _class) {
            super();
            this.key = key;
            this._class = _class;
        }

        /**
         * @see Object#toString()
         */
        @Override
        public String toString() {
            return "LockKey [key=" + key + ", _class=" + _class + "]";
        }



    }

    /**
     * Audit.
     *
     * @param call the call

     * @return the object
     * @throws Throwable the throwable
     */
    @Around(value = "execution(public * *(..)) && @annotation(pessimisticLock)", argNames = "call,pessimisticLock")
    public Object lock(final ProceedingJoinPoint call, PessimisticLock pessimisticLock)
            throws Throwable {

        LockKey key = lockKey(call, pessimisticLock);

        Object returnValue = null;


        try {
            if (key != null) {
                if (pessimisticLock.throwException() && mutexes.isLocked(key)) {
                    throw new PessimisticLockException(pessimisticLock.type());
                }
                LOGGER.debug("Lock: {}", key);
                mutexes.lock(key);
            }

            returnValue = call.proceed();
        } finally {

            if (key != null) {
                LOGGER.debug("Unlock: {}", key);
                mutexes.unlock(key);
            }
        }

        return returnValue;
    }

    private LockKey lockKey(final ProceedingJoinPoint call, PessimisticLock pessimisticLock) {
        LockKey key = null;
        try {
            MethodSignature methodSignature = (MethodSignature) call.getSignature();

            Expression expression = getLockId(methodSignature.getMethod(), call.getTarget().getClass());

            MethodInvocationProceedingJoinPoint ff = (MethodInvocationProceedingJoinPoint) call;

            Field f = MethodInvocationProceedingJoinPoint.class.getDeclaredField("methodInvocation");
            f.setAccessible(true);

            MethodInvocation mi = (MethodInvocation) f.get(ff);

            EvaluationContext ctx =
                    expressionHandler.createEvaluationContext(new AnonymousAuthenticationToken("anonymous",
                            "anonymous", Collections.singleton(new SimpleGrantedAuthority("anonymous"))), mi);
            Object keyValue = expression.getValue(ctx);

            if (keyValue != null) {
                key = new LockKey(String.valueOf(keyValue), pessimisticLock.type());
            }

        } catch (Exception e) {
            LOGGER.warn("Error extracting lock key", e);
        }
        return key;
    }

    private Expression getLockId(Method method, Class<?> targetClass) {
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        PessimisticLock
                annotation = AnnotationUtils.findAnnotation(specificMethod, PessimisticLock.class);
        if (annotation == null) {
            return null;
        }

        return parser.parseExpression(annotation.keyExpression());

    }
}
