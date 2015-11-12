package org.opendaylight.nemo.intent;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.intent.IntentResolutionException;

import static org.junit.Assert.*;

/**
 * Created by zhangmeng on 2015/11/10.
 */
public class IntentResolutionExceptionTest{
    private IntentResolutionException intentResolutionException1;
    private IntentResolutionException intentResolutionException2;
    private IntentResolutionException intentResolutionException3;
    private IntentResolutionException intentResolutionException4;
    private IntentResolutionException intentResolutionException5;

    private String message;
    private Throwable cause;
    private boolean enableSuppression;
    private boolean writableStackTrace;
    @Before
    public void setUp() throws Exception {
        message = new String();
        cause = new Throwable();
        enableSuppression = true;
        writableStackTrace = true;
    }

    @Test
    public void Construction()throws Exception{
        Assert.assertNull(intentResolutionException1);
        Assert.assertNull(intentResolutionException2);
        Assert.assertNull(intentResolutionException3);
        Assert.assertNull(intentResolutionException4);
        Assert.assertNull(intentResolutionException5);

        intentResolutionException1 = new IntentResolutionException();
        intentResolutionException2 = new IntentResolutionException(message);
        intentResolutionException3 = new IntentResolutionException(cause);
        intentResolutionException4 = new IntentResolutionException(message,cause);
        intentResolutionException5 = new IntentResolutionException(message,cause,enableSuppression,writableStackTrace);

        Assert.assertNotNull(intentResolutionException1);
        Assert.assertNotNull(intentResolutionException2);
        Assert.assertNotNull(intentResolutionException3);
        Assert.assertNotNull(intentResolutionException4);
        Assert.assertNotNull(intentResolutionException5);
    }

}