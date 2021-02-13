package com.test.evaluation.constant;

public class HttpStatusString {
    // 2xx
    public static final String OK = "200";
    public static final String ACCEPTED = "202";

    // 4xx
    public static final String BAD_REQUEST = "400";
    public static final String UNAUTHORIZED = "401";
    public static final String FORBIDDEN = "403";
    public static final String TOO_MANY_REQUEST = "429";

    // 5xx
    public static final String INTERNAL_SERVER_ERROR = "500";

    /**
     * Private ctor to prevent this class from being instantiated.
     */
    private HttpStatusString() {}
}
