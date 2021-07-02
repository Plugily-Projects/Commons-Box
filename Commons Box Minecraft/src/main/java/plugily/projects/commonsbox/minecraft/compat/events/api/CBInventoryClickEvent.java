package plugily.projects.commonsbox.minecraft.compat.events.api;

import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class CBInventoryClickEvent extends VersionEvent {

  private static final HandlerList handlers = new HandlerList();
  private final ClickType clickType;
  private final ItemStack itemStack;
  private final Inventory clickedInventory;
  private final ItemStack cursor;
  private final int hotbarButton;
  private final InventoryAction action;
  private final int rawSlot;
  private final int slot;
  private final InventoryType.SlotType slotType;
  private final boolean leftClick;
  private final boolean rightClick;
  private final boolean shiftClick;
  private final InventoryView inventoryView;

  public CBInventoryClickEvent(ClickType clickType, ItemStack itemStack, Inventory clickedInventory, ItemStack cursor, int hotbarButton, InventoryAction action, int rawSlot, int slot, InventoryType.SlotType slotType, boolean leftClick, boolean rightClick, boolean shiftClick, InventoryView inventoryView) {
    super(false);
    this.clickType = clickType;
    this.itemStack = itemStack;
    this.clickedInventory = clickedInventory;
    this.cursor = cursor;
    this.hotbarButton = hotbarButton;
    this.action = action;
    this.rawSlot = rawSlot;
    this.slot = slot;
    this.slotType = slotType;
    this.leftClick = leftClick;
    this.rightClick = rightClick;
    this.shiftClick = shiftClick;
    this.inventoryView = inventoryView;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public ClickType getClick() {
    return clickType;
  }

  public ItemStack getItemStack() {
    return itemStack;
  }

  public Inventory getClickedInventory() {
    return clickedInventory;
  }

  public ItemStack getCursor() {
    return cursor;
  }

  public int getHotbarButton() {
    return hotbarButton;
  }

  public InventoryAction getAction() {
    return action;
  }

  public int getRawSlot() {
    return rawSlot;
  }

  public int getSlot() {
    return slot;
  }

  public InventoryType.SlotType getSlotType() {
    return slotType;
  }

  public boolean isLeftClick() {
    return leftClick;
  }

  public boolean isRightClick() {
    return rightClick;
  }

  public boolean isShiftClick() {
    return shiftClick;
  }

  public InventoryView getInventoryView() {
    return inventoryView;
  }
}
