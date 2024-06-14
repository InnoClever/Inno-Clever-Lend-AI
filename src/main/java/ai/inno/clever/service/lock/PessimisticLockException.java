/*
 * (C) Copyright 2019 medifilm AG
 *
 * All rights reserved
 *
 * Licensed Materials - Property of medifilm AG.
 */
package ai.inno.clever.service.lock;



/**
 * Exception thrown when an object is being modified in an exclusion mode.
 *
 * Created May 2, 2019
 * @author prese
 */

public class PessimisticLockException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -4383267557037370729L;
    private Class<?> type;

    public PessimisticLockException(Class<?> type) {
        super();
        this.type = type;
    }

    /**
     * @return the type
     */
    public Class<?> getType() {
        return type;
    }

}
