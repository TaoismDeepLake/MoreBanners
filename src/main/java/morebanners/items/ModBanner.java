package morebanners.items;

import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.util.GameUtils;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.buffs.staticBuffs.Buff;
import necesse.entity.mobs.friendly.human.HumanMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.SettlerWeaponItem;
import necesse.inventory.item.miscItem.BannerItem;
import necesse.level.maps.Level;

import java.util.function.Function;
import java.util.stream.Stream;

public class ModBanner extends BannerItem implements SettlerWeaponItem {
    public ModBanner(Rarity rarity, int range, Function<Mob, Buff> buff, boolean buffsMobs) {
        super(rarity, range, buff);
        this.buffsMobs = buffsMobs;
    }

//    public GameMessage getSettlerCanUseError(HumanMob mob, InventoryItem item) {
//        return null;
//    }

    @Override
    public InventoryItem onSettlerAttack(Level level, HumanMob mob, Mob target, int i, int i1, InventoryItem item) {
        mob.attackItem(target.getX(), target.getY(), item);
        return item;
    }

    public void tickHolding(InventoryItem item, PlayerMob player) {
        super.tickHolding(item, player);
        if (this.buffsPlayers) {
            GameUtils.streamNetworkClients(player.getLevel()).filter((c) -> {
                return c.playerMob.getDistance(player.x, player.y) <= (float)this.range;
            }).filter((c) -> {
                return this.shouldBuffPlayer(item, player, c.playerMob);
            }).forEach((c) -> {
                this.applyBuffs(c.playerMob);
            });
        }

        if (this.buffsMobs) {
            Stream<Mob> mobsStream = player.getLevel().entityManager.mobs.streamInRegionsShape(GameUtils.rangeBounds(player.x, player.y, this.range), 0);
            mobsStream.filter((m) -> {
                return !m.removed();
            }).filter((m) -> {
                return m.getDistance(player.x, player.y) <= (float)this.range;
            }).filter((m) -> {
                return this.shouldBuffMob(item, player, m);
            }).forEach(this::applyBuffs);
        }

    }

    public boolean shouldBuffMob(InventoryItem item, PlayerMob player, Mob target) {
        return player.isSameTeam(target);
    }
}
