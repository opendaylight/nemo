/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class ValueCheckTest{
	private ValueCheck valuecheck;
@Before
public void setUp() throws Exception{
  valuecheck=new ValueCheck();
    }
@Test
public void checkMacTest() throws Exception{
	//branch 1
	String macAddress1="11:22:33";
	Assert.assertFalse(valuecheck.checkMac(macAddress1));
	//branch 2
	String macAddress2="11:222:33:44:55:66";
	Assert.assertFalse(valuecheck.checkMac(macAddress2));
	//branch 3
	String macAddress3="1s:22:33:44:55:66";
	Assert.assertFalse(valuecheck.checkMac(macAddress3));
	//branch 4 
	String macAddress4="00:00:00:00:00:00";
	Assert.assertTrue(valuecheck.checkMac(macAddress4));
}
@Test
public void checkIpPrefixTest()throws Exception{
	//branch 1 
	String ipPrefix1="11/22/33";
	Assert.assertFalse(valuecheck.checkIpPrefix(ipPrefix1));
	//branch 2
	String ipPrefix2="22/33";
	Assert.assertFalse(valuecheck.checkIpPrefix(ipPrefix2));
	//branch 3 
	String ipPrefix3="00:00:00:00:00:000/12";
	Assert.assertFalse(valuecheck.checkIpPrefix(ipPrefix3));
	//branch 4 
	String ipPrefix4="10.1.1.1/12";
	Assert.assertTrue(valuecheck.checkIpPrefix(ipPrefix4));
}
@Test
public void checkIpAddressTest() throws Exception{
	//branch 1 
	String ipAdress1="1.1";
	Assert.assertFalse(valuecheck.checkIpAddress(ipAdress1));
	//branch 2
	String ipAdress2="1111.1.1.1";
	Assert.assertFalse(valuecheck.checkIpAddress(ipAdress2));
	//branch 3
	String ipAdress3="11.1.1.1";
	Assert.assertTrue(valuecheck.checkIpAddress(ipAdress3));
}
@Test
public void  checkTimeTest() throws Exception{
	//branch 1 
	String time1="1,1,1";
	Assert.assertTrue(valuecheck.checkTime(time1));
	//branch 2 
	String time2="12:12:12:12";
	Assert.assertFalse(valuecheck.checkTime(time2));
	//branch 3 
	String time3="66:12:12";
	Assert.assertFalse(valuecheck.checkTime(time3));
    String time4="22:66:12";
	Assert.assertFalse(valuecheck.checkTime(time4));
	String time5="21:12:66";
	Assert.assertFalse(valuecheck.checkTime(time5));
	String time6="12:12:12";
	Assert.assertTrue(valuecheck.checkTime(time6));
	//branch 4
    String time7="2015-12-16-22,12:12:12";
	Assert.assertFalse(valuecheck.checkTime(time7));
    	//branch 5
    String time8="2015-66-16,12:12:12";
	Assert.assertFalse(valuecheck.checkTime(time8));
    	//branch 6
    String time9="2015-1-32,12:12:12";
	Assert.assertFalse(valuecheck.checkTime(time9));
    	//branch 7
    String time10="2015-4-31,12:12:12";
	Assert.assertFalse(valuecheck.checkTime(time10));
    	//branch 8
    String time11="2015-2-29,12:12:12";
	Assert.assertFalse(valuecheck.checkTime(time11));
   	//branch 9
    String time12="2015-2-28,12:12";
	Assert.assertFalse(valuecheck.checkTime(time12));
    	//branch 10
    String time13="2015-12-16,12:12:12";
	Assert.assertTrue(valuecheck.checkTime(time13));	
}
}