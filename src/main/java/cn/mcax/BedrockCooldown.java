package cn.mcax;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.geyser.api.GeyserApi;

public final class BedrockCooldown extends JavaPlugin implements Listener {

	private FileConfiguration config;

    @Override
    public void onEnable() {
		this.saveDefaultConfig();
		config = this.getConfig();
        getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("BedrockCooldown has successfully enabled.");
    }

    @Override
    public void onDisable() {
		getLogger().info("BedrockCooldown has successfully disabled.");
    }
	
    @EventHandler
    public void Playerjoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		AttributeInstance attackSpeed = p.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
		AttributeInstance blockRange = p.getAttribute(Attribute.PLAYER_BLOCK_INTERACTION_RANGE);
		AttributeInstance entityRange = p.getAttribute(Attribute.PLAYER_ENTITY_INTERACTION_RANGE);
		if (attackSpeed == null || blockRange == null || entityRange == null) {
			return;
		}
		if (GeyserApi.api().isBedrockPlayer(p.getUniqueId())) {
			attackSpeed.setBaseValue(4 * config.getDouble("attack-speed"));
			blockRange.setBaseValue(4.5 * config.getDouble("block-interaction-range"));
			entityRange.setBaseValue(3 * config.getDouble("entity-interaction-range"));
		} else {
			attackSpeed.setBaseValue(4);
			blockRange.setBaseValue(4.5);
			entityRange.setBaseValue(3);
		}
    }
}

