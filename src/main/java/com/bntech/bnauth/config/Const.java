package com.bntech.bnauth.config;

import java.time.Duration;

public class Const {
    //      Auth
    public static final String WILDCARD = "*";
    public static final String BEARER = "Bearer ";
    public static final Duration TOKEN_DURATION = Duration.ofMinutes(30);
    public final static String ROLE_USER = "role_user";

    //      Database
    public static final String USERS_TABLE = "users";
    public static final String USERS_FULL_NAME = "full_name";
    public static final String USERS_CREATED_AT = "created_at";
    public static final String USERS_UPDATED_AT = "updated_at";
    public static final String ROLE_TABLE = "roles";
    public static final String USER_ROLE_TABLE = "user_roles";
    public static final String USER_ROLE_USER_ID = "user_id";
    public static final String USER_ROLE_ROLE_ID = "role_id";

    //      API
    public static final String api_VERSION = "/v1";
    public static final String api_AUTHENTICATE = "/authenticate";
    public static final String api_ERROR = "/error";
    public static final String api_RESOURCES = "/resources";
    public static final String api_403 = "/403";
    public static final String ws_ERRORS_TOPIC = "/topic/errors";

    //      Resources
    public static final String res_PEPE = "static/images/pepe.png";
    public static final String res_ERROR_CSS = "static/css/error.css";
}
