package com.sk89q.craftbook.mech;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.sk89q.craftbook.bukkit.MechanismsPlugin;

public class CustomDrops implements Listener {

    final MechanismsPlugin plugin;

    public CustomDrops(MechanismsPlugin plugin) {

        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void handleCustomBlockDrops(BlockBreakEvent event) {

        if (plugin.getLocalConfiguration().customDropSettings.requirePermissions &&
                !plugin.wrap(event.getPlayer()).hasPermission("craftbook.mech.drops")) return;

        int id = event.getBlock().getTypeId();
        byte data = event.getBlock().getData();

        CustomDropManager.CustomItemDrop drop = plugin.getLocalConfiguration().customDrops.getBlockDrops(id);

        if (drop != null) {
            CustomDropManager.DropDefinition[] drops = drop.getDrop(data);
            if (drops != null) {
                Location l = event.getBlock().getLocation();
                World w = event.getBlock().getWorld();
                // Add the custom drops
                for (CustomDropManager.DropDefinition dropDefinition : drops) {
                    w.dropItemNaturally(l, dropDefinition.getItemStack());
                }

                event.getBlock().breakNaturally(null);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void handleCustomMobDrops(EntityDeathEvent event) {

        EntityType entityType = event.getEntityType();
        if (entityType == null || !entityType.isAlive() || entityType.equals(EntityType.PLAYER)) return;
        CustomDropManager.DropDefinition[] drops =
                plugin.getLocalConfiguration().customDrops.getMobDrop(entityType.getName());
        if (drops != null) {
            event.getDrops().clear();
            // Add the custom drops
            for (CustomDropManager.DropDefinition dropDefinition : drops) {
                event.getDrops().add(dropDefinition.getItemStack());
            }
        }
    }
}
