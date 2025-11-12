package net.potato.eat.item;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public class ReviveTotemEvents {
    public static void register() {
        UseItemCallback.EVENT.register((player, world, hand) -> {

            ItemStack stack = player.getStackInHand(hand);

            if (stack.getItem() != ModItem.REVIVE_TOTEM) {

                return ActionResult.PASS;
            }



            return ActionResult.PASS;
        });
    }
}
