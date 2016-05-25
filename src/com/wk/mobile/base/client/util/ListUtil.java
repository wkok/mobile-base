package com.wk.mobile.base.client.util;

import com.smartgwt.client.data.Record;

/**
 * User: werner
 * Date: 15/12/17
 * Time: 10:22 PM
 */
public class ListUtil {

    public static int getSelectedIdx(String field, String value, Record[] records) {
        for (int i = 0; i < records.length; i++) {
            Record record = records[i];
            if (record.getAttribute(field).equals(value)) {
                return i;
            }
        }
        return -1;
    }

}
