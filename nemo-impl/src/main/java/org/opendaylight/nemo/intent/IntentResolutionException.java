/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent;

/**
 * An exception in resolving the user intent.
 *
 * @author Zhigang Ji
 */
public class IntentResolutionException extends Exception {
    private static final long serialVersionUID = -6843701204012305270L;

    public IntentResolutionException() {
        super();

        return;
    }

    public IntentResolutionException(String message) {
        super(message);

        return;
    }

    public IntentResolutionException(Throwable cause) {
        super(cause);

        return;
    }

    public IntentResolutionException(String message, Throwable cause) {
        super(message, cause);

        return;
    }

    public IntentResolutionException(String message, Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

        return;
    }
}
