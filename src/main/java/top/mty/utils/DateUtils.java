package top.mty.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum DateUtils {

    INSTANCE;

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String now() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(YYYYMMDDHHMMSS);
        return formatter.format(now);
    }
}
