package com.example.demo.util;

public class Constant {

    public static final String YES = "yes";

    public static final String NO = "no";

    public static final Integer ACTIVE = 1;

    public static final Integer INACTIVE = 0;

    public interface ROLE {

        Integer SUPER_ADMIN = 0;

        Integer ADMIN = 1;

        Integer SHIPPER = 2;

        Integer USER = 3;

    }

    public interface ORDER_STATE {

        Integer CANCELLED = 4;

        Integer DELIVERED = 3;

        Integer DELIVERING = 2;

        Integer CONFIRMED = 1;

        Integer PENDING = 0;
    }

    public static final Integer MIN_PAGE = 0;

    public static final Integer MAX_PAGE = 99999999;

    public static final Integer CODE_LENGTH = 8;

    public static final Integer OTP_LENGTH = 8;

    public static final String PATH_ADMIN = "/WEB-INF/admin";

    public static final String PATH_CLIENT = "/WEB-INF/views";

    public static final String ROOT_FOLDER_FILE = "sweet_cake/";

    public static final String CAKE_FOLDER_FILE = "cake/";

    public static final String STORE_FOLDER_FILE = "store/";

    public static final String USER_FOLDER_FILE = "/user";

    public static final String ORDER_FOLDER_FILE = "/order";

    public static final String ADDRESS_FOLDER_FILE = "/address";

    public static final Integer OTP_TTL = 60;

    public static String OTP = "OTP";

}
