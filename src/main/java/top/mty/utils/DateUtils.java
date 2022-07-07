package top.mty.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public enum DateUtils {

  INSTANCE;

  public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

  public static String now() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS);
    return formatter.format(LocalDateTime.now());
  }
}
