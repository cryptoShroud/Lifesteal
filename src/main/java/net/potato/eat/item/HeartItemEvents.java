package net.potato.eat.item;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class HeartItemEvents  {
    public static void register() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getStackInHand(hand);
            double currentMaxHealth = player.getAttributeInstance(EntityAttributes.MAX_HEALTH).getBaseValue();
            double newMaxHealth = Math.min(currentMaxHealth+2, 40);
            if (world.isClient && stack.getItem() == ModItem.HEART) {

                if (currentMaxHealth >= 40) {
                    player.playSound(SoundEvents.ENTITY_VILLAGER_HURT, 1.0f, 1.0f);
                    player.sendMessage(Text.literal("You have exceeded the heart limit").formatted(Formatting.RED), true);

                }
                else {
                    player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                }


            }
            if (!world.isClient() && stack.getItem() == ModItem.HEART) {
                if (currentMaxHealth >=40){
                    return ActionResult.SUCCESS;
                }
                player.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(newMaxHealth);
                stack.decrement(1);

                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });

        ServerLivingEntityEvents.AFTER_DEATH.register((e, damageSource) ->
        {

           if (!(e instanceof ServerPlayerEntity p)){
               return;
           }
           if (damageSource.getAttacker() instanceof ServerPlayerEntity){
               return;
           }
           p.dropItem(new ItemStack(ModItem.HEART), false);

           EntityAttributeInstance attr = p.getAttributeInstance(EntityAttributes.MAX_HEALTH);

           if (attr == null) {
               return;
           }
           double current = attr.getValue();
           double newHealth = current-2;
           attr.setBaseValue(newHealth);
           if (attr.getBaseValue() <= 0) {
               p.networkHandler.disconnect(Text.literal("You do not have any hearts left.").formatted(Formatting.RED));

           }



        });
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((sw, e, le) -> {

            if (!(e instanceof ServerPlayerEntity killer) || !(le instanceof ServerPlayerEntity sp)) {
                return;
            }
            if (e == le){
                sp.dropItem(new ItemStack(ModItem.HEART), false);
                EntityAttributeInstance attr = sp.getAttributeInstance(EntityAttributes.MAX_HEALTH);
                if (attr == null) {
                    return;
                }
                double current = attr.getValue();
                double newHealth = current-2;
                attr.setBaseValue(newHealth);
                if (attr.getBaseValue() <= 0) {
                    sp.networkHandler.disconnect(Text.literal("You do not have any hearts left.").formatted(Formatting.RED));

                }
                return;
            }
            EntityAttributeInstance kattr = killer.getAttributeInstance(EntityAttributes.MAX_HEALTH);
            EntityAttributeInstance attr = sp.getAttributeInstance(EntityAttributes.MAX_HEALTH);

            double currentMax = sp.getAttributeInstance(EntityAttributes.MAX_HEALTH).getBaseValue();
            double newMax = currentMax-2;
            attr.setBaseValue(newMax);
            if (kattr.getBaseValue() >= 40) {
                sp.dropItem(new ItemStack(ModItem.HEART), false);
                return;
            }
            double kurrentMax = kattr.getBaseValue();
            double knewMax = Math.min(40, kurrentMax+2);

        });
        ServerPlayConnectionEvents.JOIN.register((handler, packetSender, server) -> {
            ServerPlayerEntity p = handler.getPlayer();

            EntityAttributeInstance attr = p.getAttributeInstance(EntityAttributes.MAX_HEALTH);
            double current = attr.getBaseValue();
            if (current < 2) {
                p.networkHandler.disconnect(Text.literal("You do not have any hearts left (skill issue).").formatted(Formatting.RED));
                return;
            }







        });
    }
}
