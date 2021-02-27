package pl.plajerlair.commonsbox.minecraft.misc.stuff;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.meta.ItemMeta;

public interface Complement {

	String getTitle(InventoryView view);

	String getDisplayName(ItemMeta meta);

	String getDisplayName(Player player);

	String getLine(SignChangeEvent event, int line);

	String getLine(Sign sign, int line);

	void setLine(SignChangeEvent event, int line, String text);

	void setLine(Sign sign, int line, String text);

	org.bukkit.inventory.Inventory createInventory(InventoryHolder owner, int size, String title);

	void setLore(ItemMeta meta, List<String> lore);

	List<String> getLore(ItemMeta meta);

	void setDisplayName(ItemMeta meta, String name);

	void setDeathMessage(PlayerDeathEvent event, String message);

	void setJoinMessage(PlayerJoinEvent event, String message);

	void setQuitMessage(PlayerQuitEvent event, String message);

	void setMotd(ServerListPingEvent event, String motd);

	default void broadcastMessage(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(message);
		}
	}

	default void broadcastMessage(List<String> messages) {
		for (String msg : messages) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.sendMessage(msg);
			}
		}
	}
}
