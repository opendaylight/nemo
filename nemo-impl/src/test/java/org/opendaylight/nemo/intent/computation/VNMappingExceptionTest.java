package org.test.computation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.intent.computation.VNMappingException;
import org.opendaylight.nemo.intent.computation.VNMappingUnit;

import static org.junit.Assert.*;

/**
 * Created by zhangmeng on 2015/11/10.
 */
public class VNMappingExceptionTest {
    private VNMappingException vnMappingException1;
    private VNMappingException vnMappingException2;
    private VNMappingException vnMappingException3;
    private VNMappingException vnMappingException4;

    private String message;
    private Throwable cause;
    @Before
    public void setUp() throws Exception {
        message = new String();
        cause = new Throwable();
    }

    @Test
    public void Construction()throws Exception{
        Assert.assertNull(vnMappingException1);
        Assert.assertNull(vnMappingException2);
        Assert.assertNull(vnMappingException3);
        Assert.assertNull(vnMappingException4);

        vnMappingException1 = new VNMappingException();
        vnMappingException2 = new VNMappingException(message);
        vnMappingException3 = new VNMappingException(cause);
        vnMappingException4 = new VNMappingException(message,cause);

        Assert.assertNotNull(vnMappingException1);
        Assert.assertNotNull(vnMappingException2);
        Assert.assertNotNull(vnMappingException3);
        Assert.assertNotNull(vnMappingException4);
    }
}