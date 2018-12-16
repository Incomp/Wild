package gxplugins.wild;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.level.Location;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import gxplugins.wild.random.Utils;

/**
 * @author PikyCZ
 */
public class Wild extends PluginBase implements Listener {

	private Set<UUID> cache = new HashSet<>();

	// TODO: Add config for customizing boundaries

	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		// Logging should be automatic
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch (cmd.getName()) {
			case "wild":
				if (sender.hasPermission("gxwild.command.wild")) {
					if (sender instanceof Player) {
						int x = Utils.rand(1, 35000);
						int z = Utils.rand(1, 35000);
						Player p = (Player) sender;
						int y = p.getLevel().getHighestBlockAt(x, z);

						p.teleport(new Location(x, y, z));

						if (this.cache.contains(p.getUniqueId())) this.cache.remove(p.getUniqueId()); // Reset the invincible timer

						this.cache.add(p.getUniqueId());
						this.getServer().getScheduler().scheduleDelayedTask(() -> {
							this.cache.remove(p.getUniqueId());
						}, 100); // Should be 100 ticks, or 5 seconds

						sender.sendMessage(TextFormat.colorize("&7Poof."));

					} else {
						sender.sendMessage(TextFormat.colorize("&4Only players can use that command."));
					}
				} else {
					sender.sendMessage(TextFormat.colorize("&4No access."));
				}
				return true;
		}
		return false;
	}

	public void onDisable() {
		// Logging should be automatic
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player p = (Player) event.getEntity();
		if (this.cache.contains(p.getUniqueId())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			if (this.cache.contains(p.getUniqueId())) {
				event.setCancelled(true);
			}
		} else {
			if (event.getDamager() instanceof Player) {
				Player d = (Player) event.getDamager();
				if (this.cache.contains(d.getUniqueId())) {
					event.setCancelled(true);
				}
			}
		}
	}
}
