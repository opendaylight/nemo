/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

/**
 * Created by z00293636 on 2015/11/26.
 */
public class ValueCheck {
    public boolean checkMac(String macAddress){
        Boolean legalValue = true;
        String[] value = macAddress.split(":");
        if (value.length!=6){
            legalValue = false;
        }
        else {
            for (int i=0; i<value.length;i++){
                if (value[i].length()!=2){
                    legalValue= false;
                }
                else {
                    for (int j=0;j<value[i].length();j++){
                        char cc = value[i].charAt(j);
                        if(!(cc=='0'||cc=='1'||cc=='2'||cc=='3'||cc=='4'||cc=='5'||cc=='6'||cc=='7'||cc=='8'||cc=='9'
                                ||cc=='A'||cc=='B'||cc=='C'||cc=='D'||cc=='E'||cc=='F'
                                ||cc=='a'||cc=='b'||cc=='c'||cc=='c'||cc=='d'||cc=='e'||cc=='f')){
                            legalValue = false;
                        }
                    }
                }
            }
        }
        return legalValue;
    }

    public Boolean checkIpPrefix(String ipPrefix){
        Boolean legalValue = true;
        String[] valuePrefix = ipPrefix.split("/");
        if (valuePrefix.length!=2){
            legalValue = false;
        }
        else {
            if (Integer.parseInt(valuePrefix[1])>32||Integer.parseInt(valuePrefix[1])<1){
                legalValue = false;
            }
            else {
                legalValue = checkIpAddress(valuePrefix[0]);
            }
        }
        return legalValue;
    }

    public Boolean checkIpAddress(String ipAddress){
        Boolean legalValue = true;
        String[] value = ipAddress.split("\\.");
        if (value.length!=4){
            legalValue = false;
        }
        else {
            for (int i=0;i<value.length;i++){
                if (Integer.parseInt(value[i])>255||Integer.parseInt(value[i])<0){
                    legalValue = false;
                }
            }
        }
        return legalValue;
    }

    public Boolean checkTime(String time){
        Boolean legalValue = true;
        String[] value = time.split(",");
        if (value.length == 1){
            String[] hourMS = value[0].split(":");
            if (hourMS.length!=3){
                legalValue = false;
            }
            else {
                if (Integer.parseInt(hourMS[0])>23||Integer.parseInt(hourMS[0])<0){
                    legalValue = false;
                }
                if (Integer.parseInt(hourMS[1])>59||Integer.parseInt(hourMS[1])<0){
                    legalValue = false;
                }
                if (Integer.parseInt(hourMS[2])>59||Integer.parseInt(hourMS[2])<0){
                    legalValue = false;
                }
            }
        }
        if (value.length == 2){
            String[] yearMD = value[0].split("-");
            if (yearMD.length!=3){
                legalValue = false;
            }
            else {
                if (Integer.parseInt(yearMD[0])<1){
                    legalValue = false;
                }
                if (Integer.parseInt(yearMD[1])>12||Integer.parseInt(yearMD[1])<1){
                    legalValue = false;
                }
                if (Integer.parseInt(yearMD[1])==1||Integer.parseInt(yearMD[1])==3||Integer.parseInt(yearMD[1])==5||Integer.parseInt(yearMD[1])==7
                        ||Integer.parseInt(yearMD[1])==8||Integer.parseInt(yearMD[1])==10||Integer.parseInt(yearMD[1])==2){
                    if (Integer.parseInt(yearMD[2])>31||Integer.parseInt(yearMD[2])<1){
                        legalValue = false;
                    }
                }
                if (Integer.parseInt(yearMD[1])==4||Integer.parseInt(yearMD[1])==6||Integer.parseInt(yearMD[1])==9
                        ||Integer.parseInt(yearMD[1])==11){
                    if (Integer.parseInt(yearMD[2])>30||Integer.parseInt(yearMD[2])<1){
                        legalValue = false;
                    }
                }
                if (Integer.parseInt(yearMD[1])==2){
                    if (Integer.parseInt(yearMD[2])>28||Integer.parseInt(yearMD[2])<1){
                        legalValue = false;
                    }
                }
            }
            String[] hourMS = value[1].split(":");
            if (hourMS.length!=3){
                legalValue = false;
            }
            else {
                if (Integer.parseInt(hourMS[0])>23||Integer.parseInt(hourMS[0])<0){
                    legalValue = false;
                }
                if (Integer.parseInt(hourMS[1])>59||Integer.parseInt(hourMS[1])<0){
                    legalValue = false;
                }
                if (Integer.parseInt(hourMS[2])>59||Integer.parseInt(hourMS[2])<0){
                    legalValue = false;
                }
            }
        }
        return legalValue;
    }
}
