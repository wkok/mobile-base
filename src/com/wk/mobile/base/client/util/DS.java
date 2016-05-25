package com.wk.mobile.base.client.util;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.*;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.Offline;
import com.wk.mobile.base.client.BaseClientFactory;
import com.wk.mobile.base.client.MobileDSCallback;
import com.wk.mobile.base.client.Session;
import com.wk.mobile.base.client.widget.Loader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * User: werner
 * Date: 15/07/29
 * Time: 7:38 PM
 */
public class DS {

    public static final Criteria CUSTOMER_CRITERIA() {
        return new Criteria("customer_id", Integer.toString(Session.getCustomerID()));
    }

    public static final Criterion CUSTOMER_CRITERION() {
        return new Criterion("customer_id", OperatorId.EQUALS, Integer.toString(Session.getCustomerID()));
    }


    public static void fetch(@NotNull DataSource dataSource, @NotNull MobileDSCallback dsCallback, @NotNull DSRequest requestProperties, BaseClientFactory clientFactory) {
        fetch(dataSource, CUSTOMER_CRITERIA(), dsCallback, requestProperties, clientFactory);
    }

    public static void fetch(@NotNull DataSource dataSource, @NotNull Criteria criteria, @NotNull MobileDSCallback dsCallback, BaseClientFactory clientFactory) {
        fetch(dataSource, criteria, dsCallback, null, clientFactory);
    }

    public static void fetch(@NotNull DataSource dataSource, @NotNull Criteria criteria, @NotNull MobileDSCallback dsCallback, @Nullable DSRequest requestProperties, BaseClientFactory clientFactory) {
        if (requestProperties == null) {
            requestProperties = new DSRequest();
        }
        if (criteria.getAttribute("customer_id") == null) {
            criteria.addCriteria(CUSTOMER_CRITERIA());
        }
        setAuthToken(requestProperties);
        Loader.showProgress(clientFactory);
        GWT.log(DS.class.getName() + " DataSource: " + dataSource.getID() + " - fetchData");
        dataSource.fetchData(criteria, dsCallback, requestProperties);
    }

    public static void add(@NotNull DataSource dataSource, @NotNull Record record, @Nullable DSRequest requestProperties, BaseClientFactory clientFactory) {
        add(dataSource, record, null, requestProperties, clientFactory);
    }

    public static void add(@NotNull DataSource dataSource, @NotNull Record record, @Nullable MobileDSCallback dsCallback, @Nullable DSRequest requestProperties, BaseClientFactory clientFactory) {
        if (requestProperties == null) {
            requestProperties = new DSRequest();
        }
        if (record.getAttribute("customer_id") == null) {
            record.setAttribute("customer_id", Integer.toString(Session.getCustomerID()));
        }
        setAuthToken(requestProperties);
        Loader.showProgress(clientFactory);
        dataSource.addData(record, dsCallback, requestProperties);
    }

    public static void update(@NotNull DataSource dataSource, @NotNull Record record, @Nullable DSRequest requestProperties, BaseClientFactory clientFactory) {
        update(dataSource, record, null, requestProperties, clientFactory);
    }

    public static void update(@NotNull DataSource dataSource, @NotNull Record record, @Nullable MobileDSCallback dsCallback, @Nullable DSRequest requestProperties, BaseClientFactory clientFactory) {
        if (requestProperties == null) {
            requestProperties = new DSRequest();
        }
        if (record.getAttribute("customer_id") == null) {
            record.setAttribute("customer_id", Integer.toString(Session.getCustomerID()));
        }
        setAuthToken(requestProperties);
        Loader.showProgress(clientFactory);
        dataSource.updateData(record, dsCallback, requestProperties);
    }

    public static void remove(@NotNull DataSource dataSource, @NotNull Record record, @Nullable DSRequest requestProperties, BaseClientFactory clientFactory) {
        remove(dataSource, record, null, requestProperties, clientFactory);
    }

    public static void remove(@NotNull DataSource dataSource, @NotNull Record record, @Nullable MobileDSCallback dsCallback, @Nullable DSRequest requestProperties, BaseClientFactory clientFactory) {
        if (requestProperties == null) {
            requestProperties = new DSRequest();
        }
        setAuthToken(requestProperties);
        Loader.showProgress(clientFactory);
        dataSource.removeData(record, dsCallback, requestProperties);
    }


    public static Record constructErrorDebugString(String customMsg, RPCRequest request, RPCResponse response) {
        Record record = new Record();
        record.setAttribute("custom_error", constructCustomDebugString(customMsg));
        record.setAttribute("request_errors", constructRequestDebugString(request));
        record.setAttribute("response_errors", constructResponseDebugString(response));
        return record;
    }

    private static Map<String, String> constructRequestDebugString(RPCRequest request) {

        Map<String, String> map = new HashMap<>();

        map.put("getContentType", request.getContentType());
        map.put("getData", request.getData() != null ? request.getData().toSource() : "");
        map.put("getHttpHeaders", request.getHttpHeaders() != null ? request.getHttpHeaders().toString() : "");
        map.put("getHttpMethod", request.getHttpMethod());



        if (request instanceof DSRequest) {
            DSRequest dsRequest = (DSRequest) request;

            map.put("getAdditionalOutputs", dsRequest.getAdditionalOutputs());
            if (dsRequest.getOperationType().getValue().equalsIgnoreCase("FETCH")) {
                map.put("getCriteria", dsRequest.getCriteria() != null && dsRequest.getCriteria().getValues() != null ? dsRequest.getCriteria().getValues().toString() : "");
            }
            map.put("getDataSource", dsRequest.getDataSource());
            map.put("getGroupBy", dsRequest.getGroupBy() != null ? Arrays.toString(dsRequest.getGroupBy()) : "");
            map.put("getOperationId", dsRequest.getOperationId());
            map.put("getOperationType", dsRequest.getOperationType().getValue());
            map.put("getRequestId", dsRequest.getRequestId());
            map.put("getStartRow", dsRequest.getStartRow() != null ? dsRequest.getStartRow().toString() : "");
            map.put("getEndRow", dsRequest.getStartRow() != null ? dsRequest.getEndRow().toString() : "");
        }

        extractAttrs(map, request.getAttributes(), request);

        return map;
    }

    private static void extractAttrs(Map<String, String> map, String[] attributes, RPCRequest request) {
        for (int i = 0; i < attributes.length; i++) {
            String name = attributes[i];
            if (name.equalsIgnoreCase("context") ||
                    name.equalsIgnoreCase("results") ||
                    name.equalsIgnoreCase("errors") ||
                    name.equalsIgnoreCase("xmlHttpRequest") ||
                    name.equalsIgnoreCase("data") ||
                    name.equalsIgnoreCase("unconvertedDSRequest") ||
                    name.equalsIgnoreCase("cursorTrackerProperties") ||
                    name.equalsIgnoreCase("_dsRequest") ||
                    name.equalsIgnoreCase("httpHeaders") ||
                    name.equalsIgnoreCase("operation") ||
                    name.equalsIgnoreCase("callback") ||
                    name.equalsIgnoreCase("oldValues")
                    ) {
                map.put(name, Arrays.toString(request.getAttributeAsStringArray(attributes[i])));
            }
            else {
                map.put(name, request.getAttribute(attributes[i]));
            }
        }
    }

    private static void extractAttrs(Map<String, String> map, String[] attributes, RPCResponse response) {
        for (int i = 0; i < attributes.length; i++) {
            String name = attributes[i];
            if (name.equalsIgnoreCase("context") ||
                    name.equalsIgnoreCase("results") ||
                    name.equalsIgnoreCase("errors") ||
                    name.equalsIgnoreCase("xmlHttpRequest") ||
                    name.equalsIgnoreCase("data") ||
                    name.equalsIgnoreCase("unconvertedDSRequest") ||
                    name.equalsIgnoreCase("cursorTrackerProperties") ||
                    name.equalsIgnoreCase("_dsRequest") ||
                    name.equalsIgnoreCase("httpHeaders") ||
                    name.equalsIgnoreCase("operation") ||
                    name.equalsIgnoreCase("callback") ||
                    name.equalsIgnoreCase("oldValues")
                    ) {
                map.put(name, Arrays.toString(response.getAttributeAsStringArray(attributes[i])));
            }
            else {
                map.put(name, response.getAttribute(attributes[i]));
            }
        }
    }


    private static String constructCustomDebugString(String customMsg) {

        if (customMsg != null && !customMsg.equals("")) {
            return customMsg;
        }

        return "";
    }

    private static Map<String, String> constructResponseDebugString(RPCResponse response) {

        Map<String, String> map = new HashMap<>();

        if (response instanceof DSResponse) {
            DSResponse dsResponse = (DSResponse) response;

            map.put("getErrors", dsResponse.getErrors() != null ? dsResponse.getErrors().toString() : "");
            map.put("getStartRow", dsResponse.getStartRow() != null ? dsResponse.getStartRow().toString() : "");
            map.put("getEndRow", dsResponse.getEndRow() != null ? dsResponse.getEndRow().toString() : "");
            map.put("getTotalRows", dsResponse.getTotalRows() != null ? dsResponse.getTotalRows().toString() : "");
            map.put("getOperationType", dsResponse.getOperationType() != null ? dsResponse.getOperationType().getValue() : "");
            map.put("getDataSource", dsResponse.getDataSource());
        }

        map.put("getHttpHeaders", response.getHttpHeaders() != null ? response.getHttpHeaders().toString() : "");
        map.put("getHttpResponseCode", response.getHttpResponseCode() != null ? response.getHttpResponseCode().toString() : "");
        map.put("getHttpResponseText", response.getHttpResponseText());
        map.put("getStatus", String.valueOf(response.getStatus()));
        map.put("getDataAsString", response.getDataAsString());

        extractAttrs(map, response.getAttributes(), response);

        return map;
    }

    public static void setAuthToken(RPCRequest rpcRequest) {
        Object keepMeLoggedIn = Offline.get("keepMeLoggedIn");
        if (keepMeLoggedIn != null) {
            if (keepMeLoggedIn.equals("true")) {
                Map headers = new HashMap();
                headers.put("auth_token", Offline.get("auth_token"));
                rpcRequest.setHttpHeaders(headers);
            }
        }
    }


}
