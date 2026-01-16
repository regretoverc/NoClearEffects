package eu.regret.nce;

import eu.regret.nce.utils.Hex;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import java.util.*;

public class Main extends JavaPlugin implements Listener {
    private final Map<UUID, PotionEffect[]> savedEffects = new HashMap<>();
    private static final String USE_PERMISSION = "nceffec.use";
    
    public void onEnable() {
        getLogger().info(Hex.format(""));
        getLogger().info(Hex.format("&e◜NoClearEffects &aвключен"));
        getLogger().info(Hex.format("&e◟Кодер: &6t.me/regretover"));
        getLogger().info(Hex.format(""));
        getServer().getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {
        getLogger().info(Hex.format(""));
        getLogger().info(Hex.format("&e◜NoClearEffects &4вылючен"));
        getLogger().info(Hex.format("&e◟Кодер: &6t.me/regretover"));
        getLogger().info(Hex.format(""));
    }

    @EventHandler
    public void onTotemActivate(EntityResurrectEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (!player.hasPermission(USE_PERMISSION)) return;
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        if (isTotem(mainHand) || isTotem(offHand)) {
            savedEffects.put(
                    player.getUniqueId(),
                    player.getActivePotionEffects().toArray(new PotionEffect[0])
            );

            getServer().getScheduler().runTaskLater(this, () -> {
                restoreEffects(player);
            }, 1L);
        }
    }

    private boolean isTotem(ItemStack item) {
        return item != null && item.getType().toString().equals("TOTEM_OF_UNDYING");
    }

    private void restoreEffects(Player player) {
        PotionEffect[] effects = savedEffects.get(player.getUniqueId());
        if (effects != null) {
            for (PotionEffect effect : effects) {
                player.addPotionEffect(effect, true);
            }
            savedEffects.remove(player.getUniqueId());
        }
    }
}