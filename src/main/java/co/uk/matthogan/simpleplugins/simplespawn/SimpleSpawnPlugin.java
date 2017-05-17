/* MIT License
Copyright (c) 2017 SimplePlugins

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

Written by Matthew Hogan <matt@matthogan.co.uk>, May 2017
*/
package co.uk.matthogan.simpleplugins.simplespawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * <p>Simple and lightweight Spigot plugin that teleports a player
 * to spawn when they login</p>
 *
 * @author Matthew Hogan
 */
public class SimpleSpawnPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            if (event.getPlayer() == null) {
                return;
            }

            event.getPlayer().teleport(this.getSpawnLocation());
        }, 1);
    }

    /**
     * <p>In vanilla Minecraft you can set a world's spawn point with the
     * <code>/setworldspawn</code> command. We then get that {@link Location}
     * based on the world specified in <code>SimpleSpawn/config.yml</code></p>
     *
     * @return The world's spawn point as {@link Location}
     */
    private Location getSpawnLocation() {
        World world = Bukkit.getWorld(this.getConfig().getString("spawn-world"));
        return world.getSpawnLocation();
    }
}