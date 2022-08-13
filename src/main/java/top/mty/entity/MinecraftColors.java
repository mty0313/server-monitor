package top.mty.entity;

import java.util.ArrayList;
import java.util.List;

public class MinecraftColors {
  public static List<String> colorCodes = new ArrayList<>();
  static {
    colorCodes.add("§0");
    colorCodes.add("§1");
    colorCodes.add("§2");
    colorCodes.add("§3");
    colorCodes.add("§4");
    colorCodes.add("§5");
    colorCodes.add("§6");
    colorCodes.add("§7");
    colorCodes.add("§8");
    colorCodes.add("§9");
    colorCodes.add("§a");
    colorCodes.add("§b");
    colorCodes.add("§c");
    colorCodes.add("§d");
    colorCodes.add("§e");
    colorCodes.add("§f");
    colorCodes.add("§g");
    colorCodes.add("§r");
  }

  public static String filterColorCode(String text) {
    for (String colorCode : colorCodes) {
      text = text.replace(colorCode, "");
    }
    return text;
  }
}
