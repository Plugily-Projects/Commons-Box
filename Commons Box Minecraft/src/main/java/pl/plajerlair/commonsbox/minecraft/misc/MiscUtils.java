package pl.plajerlair.commonsbox.minecraft.misc;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import pl.plajerlair.commonsbox.minecraft.compat.ServerVersion.Version;

/**
 * @author Plajer
 * <p>
 * Created at 09.03.2019
 */
@SuppressWarnings("deprecation")
public class MiscUtils {

  private static Random random = new Random();

  private MiscUtils() {
  }

  public static String matchColorRegex(String s) {
    if (Version.isCurrentLower(Version.v1_16_R1)) {
      return s;
    }

      String regex = "&?#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})";
      Matcher matcher = Pattern.compile(regex).matcher(s);
      while (matcher.find()) {
        String group = matcher.group(0);
        String group2 = matcher.group(1);

        try {
          s = s.replace(group, net.md_5.bungee.api.ChatColor.of("#" + group2) + "");
        } catch (Exception e) {
          System.err.println("Bad hex color match: " + group);
        }
      }

      return s;
  }

  public static SkullMeta setPlayerHead(Player player, SkullMeta meta) {
      if (Version.isCurrentEqualOrHigher(Version.v1_12_R1) && Bukkit.getServer().getVersion().contains("Paper")
          && player.getPlayerProfile().hasTextures()) {
        return CompletableFuture.supplyAsync(() -> {
          meta.setPlayerProfile(player.getPlayerProfile());
          return meta;
        }).exceptionally(e -> {
          System.err.println("Retrieving player profile of " + player.getName() + " failed!");
          return meta;
        }).join();
      }

      if (Version.isCurrentHigher(Version.v1_12_R1)) {
        meta.setOwningPlayer(player);
      } else {
        meta.setOwner(player.getName());
      }
      return meta;
    }

  public static void sendActionBar(Player player, String message) {
    if (Version.isCurrentEqualOrHigher(Version.v1_16_R3)) {
      player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
        player.getUniqueId(), new net.md_5.bungee.api.chat.ComponentBuilder(message).create());
    } else {
      player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
        new net.md_5.bungee.api.chat.ComponentBuilder(message).create());
    }
  }

  // https://www.spigotmc.org/threads/comprehensive-particle-spawning-guide-1-13.343001/
  public static void spawnParticle(Particle particle, Location loc, int count, double offsetX, double offsetY, double offsetZ, double extra) {
    if (Version.isCurrentEqualOrHigher(Version.v1_13_R2) && particle == Particle.REDSTONE) {
      DustOptions dustOptions = new DustOptions(Color.RED, 2);
      loc.getWorld().spawnParticle(particle, loc, count, offsetX, offsetY, offsetZ, extra, dustOptions);
    } else if (particle == Particle.ITEM_CRACK) {
      ItemStack itemCrackData = new ItemStack(loc.getBlock().getType());
      loc.getWorld().spawnParticle(particle, loc, count, offsetX, offsetY, offsetZ, extra, itemCrackData);
    } else if (particle == Particle.BLOCK_CRACK || particle == Particle.BLOCK_DUST || particle == Particle.FALLING_DUST) {
      loc.getWorld().spawnParticle(particle, loc, count, offsetX, offsetY, offsetZ, extra, loc.getBlock().getType().createBlockData());
    } else {
      loc.getWorld().spawnParticle(particle, loc, count, offsetX, offsetY, offsetZ, extra);
    }
  }

  public static void setDurability(ItemStack item, short durability) {
    if (Version.isCurrentEqualOrHigher(Version.v1_13_R1)) {
      ItemMeta meta = item.getItemMeta();
      if (meta != null) {
        ((Damageable) meta).setDamage(durability);
      }
    } else {
      item.setDurability(durability);
    }
  }

  public static void hidePlayer(JavaPlugin plugin, Player to, Player p) {
    if (Version.isCurrentEqualOrHigher(Version.v1_13_R1)) {
      to.hidePlayer(plugin, p);
    } else {
      to.hidePlayer(p);
    }
  }

  public static void showPlayer(JavaPlugin plugin, Player to, Player p) {
    if (Version.isCurrentEqualOrHigher(Version.v1_13_R1)) {
      to.showPlayer(plugin, p);
    } else {
      to.showPlayer(p);
    }
  }

  public static void setPassenger(Entity to, Entity... passengers) {
    // setPassenger is for 1.9 and less versions

    for (Entity ps : passengers) {
      to.addPassenger(ps);
    }
  }

  public static Optional<AttributeInstance> getEntityAttribute(LivingEntity entity, Attribute attribute) {
    return Optional.ofNullable(entity.getAttribute(attribute));
  }

  /**
   * Spawns random firework at location
   *
   * @param location location to spawn firework there
   */
  public static void spawnRandomFirework(Location location) {
    Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
    FireworkMeta fwm = fw.getFireworkMeta();

    //Get the type
    int rt = random.nextInt(4) + 1;
    FireworkEffect.Type type;
    switch(rt) {
      case 1:
        type = FireworkEffect.Type.BALL;
        break;
      case 2:
        type = FireworkEffect.Type.BALL_LARGE;
        break;
      case 3:
        type = FireworkEffect.Type.BURST;
        break;
      case 4:
        type = FireworkEffect.Type.CREEPER;
        break;
      case 5:
        type = FireworkEffect.Type.STAR;
        break;
      default:
        type = FireworkEffect.Type.BALL;
        break;
    }

    //Get our random colours
    int r1i = random.nextInt(250) + 1;
    int r2i = random.nextInt(250) + 1;
    Color c1 = Color.fromBGR(r1i);
    Color c2 = Color.fromBGR(r2i);

    //Create our effect with this
    FireworkEffect effect = FireworkEffect.builder().flicker(random.nextBoolean()).withColor(c1).withFade(c2)
        .with(type).trail(random.nextBoolean()).build();

    //Then apply the effect to the meta
    fwm.addEffect(effect);

    //Generate some random power and set it
    int rp = random.nextInt(2) + 1;
    fwm.setPower(rp);
    fw.setFireworkMeta(fwm);
  }

  /**
   * Sends centered message in chat for player
   *
   * @param player message receiver
   * @param message message content to send
   */
  public static void sendCenteredMessage(Player player, String message) {
    if (message == null || message.isEmpty()) {
      player.sendMessage("");
      return;
    }
    message = ChatColor.translateAlternateColorCodes('&', message);

    int messagePxSize = 0;
    boolean previousCode = false;
    boolean isBold = false;

    for (char c : message.toCharArray()) {
      if (c == '§') {
        previousCode = true;
      } else if (previousCode) {
        previousCode = false;
        isBold = c == 'l' || c == 'L';
      } else {
        DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
        messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
        messagePxSize++;
      }
    }
    int halvedMessageSize = messagePxSize / 2, toCompensate = 154 - halvedMessageSize,
      spaceLength = DefaultFontInfo.SPACE.getLength() + 1, compensated = 0;
    StringBuilder sb = new StringBuilder();
    while (compensated < toCompensate) {
      sb.append(' ');
      compensated += spaceLength;
    }
    player.sendMessage(sb.toString() + message);
  }

}
