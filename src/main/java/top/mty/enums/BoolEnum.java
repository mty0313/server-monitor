package top.mty.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BoolEnum {
  TRUE(true, "是"), FALSE(false, "否");

  private final boolean boolValue;

  private final String desc;

  public static BoolEnum getBoolEnum(boolean boolValue) {
    return boolValue ? TRUE : FALSE;
  }

  public String getDesc() {
    return this.desc;
  }

}
