package top.mty.converter;

import org.bukkit.entity.Player;
import top.mty.entity.MyPlayer;
import top.mty.enums.BoolEnum;

import java.util.Date;

public class Player2MyPlayer implements Converter<Player, MyPlayer> {

  private static final Player2MyPlayer instance = new Player2MyPlayer();

  private Player2MyPlayer() {
  }

  public static Player2MyPlayer getInstance() {
    return instance;
  }

  @Override
  public MyPlayer convert(Player player) {
    if (player == null) {
      return null;
    }
    MyPlayer myPlayer = new MyPlayer();
    myPlayer.setName(player.getDisplayName());
    myPlayer.setGameMode(player.getGameMode());
    myPlayer.setIsOp(BoolEnum.getBoolEnum(player.isOp()));
    myPlayer.setIsNewPlayer(BoolEnum.getBoolEnum(!player.hasPlayedBefore()));
    myPlayer.setCreated(new Date());
    myPlayer.setModified(new Date());
    return myPlayer;
  }
}
