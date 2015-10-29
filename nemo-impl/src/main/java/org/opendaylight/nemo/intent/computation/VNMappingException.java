/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.computation;

/**
 * An exception in virtual network mapping.
 *
 * @author Zhigang Ji
 */
public class VNMappingException extends Exception {
    private static final long serialVersionUID = -3854801203901305270L;

    public VNMappingException() {
        super();

        return;
    }

    public VNMappingException(String message) {
        super(message);

        return;
    }

    public VNMappingException(Throwable cause) {
        super(cause);

        return;
    }

    public VNMappingException(String message, Throwable cause) {
        super(message, cause);

        return;
    }

    public VNMappingException(String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

        return;
    }
}
