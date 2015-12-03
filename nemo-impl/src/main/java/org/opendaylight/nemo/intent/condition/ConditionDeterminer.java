/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.condition;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment.ConditionParameterMatchPattern;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment.PrecursorRelationOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment.PrecursorRelationOperator.*;

/**
 * Created by hj on 11/19/15.
 */
public class ConditionDeterminer {
    private static final Logger log = LoggerFactory.getLogger(ConditionMonitor.class);
    private static final SimpleDateFormat dateFormatAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");

    public static boolean isConditionMet(List<ConditionSegment> conditionSegments) {
        if (conditionSegments == null || conditionSegments.size() == 0) {
            log.debug("No condition.");
            return true;
        }
        sortSegments(conditionSegments);
        ConditionSegment firstSegment = conditionSegments.get(0);
        if (!None.equals(firstSegment.getPrecursorRelationOperator())) {
            log.warn("Segment: order:[{}],name:[{}],value[{}] with  precursor not \"None\",ignored precursor.");
        }
        int index = 0;
        while (index < conditionSegments.size()) {
            ArrayList<ConditionSegment> subSegments = new ArrayList<ConditionSegment>();
            subSegments.add(conditionSegments.get(index));
            index = index + 1;
            for (; index < conditionSegments.size(); index++) {
                ConditionSegment nextSegment = conditionSegments.get(index);
                PrecursorRelationOperator nextOperator = nextSegment.getPrecursorRelationOperator();
                if (!Or.equals(nextOperator)) {
                    subSegments.add(nextSegment);
                } else {
                    break;
                }
            }
            boolean subCondition = isSubConditionMet(subSegments);
            if (subCondition) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSubConditionMet(List<ConditionSegment> segments) {
        for (ConditionSegment conditionSegment : segments) {
            if (!isConditionMetaMet(conditionSegment)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isConditionMetaMet(ConditionSegment conditionSegment) {
        String conditionName = conditionSegment.getConditionParameterName().getValue();
        if ("time".equals(conditionName)) {
            return isTimeConditionMet(conditionSegment);
        } else {
            log.warn("Unsupported condition type : {}. ", conditionName);
        }
        if (Not.equals(conditionSegment.getPrecursorRelationOperator())) {
            log.warn("Segment: order:[{}], name:[{}], value[{}] with \"Not\" precursor,ignored precursor.");
        }
        return false;
    }

    private static boolean isTimeConditionMet(ConditionSegment conditionSegment) {
        ConditionParameterMatchPattern matchPattern = conditionSegment.getConditionParameterMatchPattern();
        String timeStr = conditionSegment.getConditionParameterTargetValue().getStringValue();
        switch (matchPattern) {
            case GreaterThan:
            case NotGreaterThan:
            case LessThan:
            case NotLessThan:
                Date currentDate = new Date();
                Date dateValue = setDate(timeStr, currentDate);

                if (dateValue == null) {
                    return false;
                }
                switch (matchPattern) {
                    case GreaterThan:
                        return currentDate.compareTo(dateValue) > 0;
                    case NotGreaterThan:
                        return currentDate.compareTo(dateValue) <= 0;
                    case LessThan:
                        return currentDate.compareTo(dateValue) < 0;
                    case NotLessThan:
                        return currentDate.compareTo(dateValue) >= 0;
                }
                return false;
            case Between:
                try {
                    String beginStr = timeStr.substring(timeStr.indexOf("(")+1, timeStr.indexOf(",")).trim();
                    String endStr = timeStr.substring(timeStr.indexOf(",") + 1, timeStr.indexOf(")")).trim();
                    Date currentDate1 = new Date();
                    Date beginDate = setDate(beginStr, currentDate1);
                    Date endDate = setDate(endStr, currentDate1);


                    if (endDate != null && beginDate != null) {
                        return currentDate1.compareTo(beginDate) >= 0 && currentDate1.compareTo(endDate) < 0;
                    }
                } catch (Exception e) {
                    log.warn("Illegal time range: {}.", timeStr);
                }
            default:
                log.warn("Unsupported match pattern {}.", matchPattern);
        }
        return false;
    }

    private static Date setDate(String dateStr, Date currentDate) {
        Date dateValue = null;
        try {
            dateValue = dateFormatAll.parse(dateStr);
            currentDate.setTime(new Date().getTime());
        } catch (ParseException e) {
            try {
                dateValue = dateFormatTime.parse(dateStr);
                Date tDate = dateFormatTime.parse(dateFormatTime.format(new Date()));
                currentDate.setTime(tDate.getTime());
            } catch (ParseException e2) {
                dateValue = null;
                log.warn("Illegal date: {}", dateStr);
            }
        }
        return dateValue;
    }

    private static void sortSegments(final List<ConditionSegment> conditionSegments) {
        Collections.sort(conditionSegments, new Comparator<ConditionSegment>() {
            @Override
            public int compare(ConditionSegment o1, ConditionSegment o2) {
                if (o1.getOrder() != null)
                    return o1.getOrder().compareTo(o2.getOrder());
                return 0;
            }
        });
    }

    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(date));
    }
}
