package gxplugins.wild;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import gxplugins.wild.random.Utils;

/**
 * @author PikyCZ
 */
public class Wild extends PluginBase {

	int x;
	int y;
	int z;

	public void onEnable() {
		// Logging should be automatic
	}

	/**
	 * @param sender
	 * @param cmd
	 * @param label
	 * @param args
	 * @return
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch (cmd.getName()) {
			case "wild":
				if (sender.hasPermission("gxwild.command.wild")) {
					if (sender instanceof Player) {
						x = Utils.rand(1, 35000);
						y = Utils.rand(1, 123);
						z = Utils.rand(1, 35000);

						Player p = (Player) sender;

						p.teleport(p.getLevel().getSafeSpawn(new Vector3(x, y, z)));
						sender.sendMessage(TextFormat.colorize("&7Poof."));
					} else {
						sender.sendMessage(TextFormat.colorize("&4Only players can use that command."));
					}
				} else {
					sender.sendMessage(TextFormat.colorize("&7Poof."));
				}
				return true;
		}
		return false;
	}

	public void onDisable() {
		// Logging should be automatic
	}

}
