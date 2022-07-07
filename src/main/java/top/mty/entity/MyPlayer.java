package top.mty.entity;

import lombok.Data;
import org.bukkit.GameMode;
import top.mty.enums.BoolEnum;

import java.util.Date;

@Data
public class MyPlayer {
  private String name;
  private GameMode gameMode;
  private BoolEnum isOp;
  private BoolEnum isNewPlayer;
  private Date created;
  private Date modified;
}
