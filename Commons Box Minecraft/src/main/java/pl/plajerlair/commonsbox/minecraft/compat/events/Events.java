package pl.plajerlair.commonsbox.minecraft.compat.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.plugin.java.JavaPlugin;

import pl.plajerlair.commonsbox.minecraft.compat.VersionUtils;
import pl.plajerlair.commonsbox.minecraft.compat.events.api.CBEntityPickupItemEvent;
import pl.plajerlair.commonsbox.minecraft.compat.events.api.CBInventoryClickEvent;
import pl.plajerlair.commonsbox.minecraft.compat.events.api.CBPlayerInteractEntityEvent;
import pl.plajerlair.commonsbox.minecraft.compat.events.api.CBPlayerInteractEvent;
import pl.plajerlair.commonsbox.minecraft.compat.events.api.CBPlayerPickupArrow;
import pl.plajerlair.commonsbox.minecraft.compat.events.api.CBPlayerSwapHandItemsEvent;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Tigerpanzer_02
 * <p>
 * Created at 10.02.2021
 */
public class Events implements Listener {

  public Events(JavaPlugin plugin) {
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler
  public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
    CBPlayerSwapHandItemsEvent cbEvent = new CBPlayerSwapHandItemsEvent(event.getPlayer(), event.getMainHandItem(), event.getOffHandItem());
    Bukkit.getPluginManager().callEvent(cbEvent);
    if(cbEvent.isCancelled()) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onEntityPickupItem(EntityPickupItemEvent event) {
    CBEntityPickupItemEvent cbEvent = new CBEntityPickupItemEvent(event.getEntity(), event.getItem(), event.getRemaining());
    Bukkit.getPluginManager().callEvent(cbEvent);
    if(cbEvent.isCancelled()) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerPickupArrow(PlayerPickupArrowEvent event) {
    Projectile projectile;
    try {
      projectile = (Projectile) event.getClass().getDeclaredMethod("getArrow").invoke(event);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
      return;
    }
    CBPlayerPickupArrow cbEvent = new CBPlayerPickupArrow(event.getPlayer(), event.getItem(), projectile, event.getRemaining(), VersionUtils.isPaper() && event.getFlyAtPlayer());
    Bukkit.getPluginManager().callEvent(cbEvent);
    if(cbEvent.isCancelled()) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerInteractEvent(PlayerInteractEvent event) {
    CBPlayerInteractEvent cbEvent = new CBPlayerInteractEvent(event.getPlayer(), event.getItem(), event.getHand(), event.getAction(), event.getBlockFace(), event.getClickedBlock(), event.getMaterial(), event.hasItem(), event.hasBlock());
    Bukkit.getPluginManager().callEvent(cbEvent);
    if(cbEvent.isCancelled()) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerInteractEvent(PlayerInteractEntityEvent event) {
    CBPlayerInteractEntityEvent cbEvent = new CBPlayerInteractEntityEvent(event.getPlayer(), event.getHand(), event.getRightClicked());
    Bukkit.getPluginManager().callEvent(cbEvent);
    if(cbEvent.isCancelled()) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onInventoryClickEvent(InventoryClickEvent event) {
    CBInventoryClickEvent cbEvent = new CBInventoryClickEvent(event.getClick(), event.getCurrentItem(), event.getClickedInventory(), event.getCursor(), event.getHotbarButton(), event.getAction(), event.getRawSlot(), event.getSlot(), event.getSlotType(), event.isLeftClick(), event.isRightClick(), event.isShiftClick(), event.getView());
    Bukkit.getPluginManager().callEvent(cbEvent);
    if(cbEvent.isCancelled()) {
      event.setCancelled(true);
    }
  }
}
