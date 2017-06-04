package gxplugins.wild;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import gxplugins.random.Utils;

/**
 *
 * @author PikyCZ
 */
public class Wild extends PluginBase {

    int x;
    int y;
    int z;

    public void onEnable() {
        this.getLogger().info(TextFormat.GREEN + "[Wild] enable");
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
                        x = Utils.rand(1, 350000);
                        y = Utils.rand(1, 256);
                        z = Utils.rand(1, 350000);
                        ((Player) sender).teleport(((Player) sender).getLevel().getSafeSpawn(new Vector3(x, y, z)));
                        ((Player) sender).sendTip(TextFormat.GREEN + "[Wild] You've been teleported somewhere wild!");
                        sender.sendMessage(TextFormat.RED + "[Wild] teleporting");
                    } else {
                        sender.sendMessage(TextFormat.RED + "[Wild] Only in-game!");
                    }
                } else {
                    sender.sendMessage(TextFormat.RED + "[Wild] You have no permission to use this command!");
                }
                return true;
        }
        return false;
    }

    public void onDisable() {
        this.getLogger().info(TextFormat.RED + "[Wild] has been disabled!");
    }

}
