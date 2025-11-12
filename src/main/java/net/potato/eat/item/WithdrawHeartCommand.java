package net.potato.eat.item;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class WithdrawHeartCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("withdraw").then(CommandManager.argument("amount", DoubleArgumentType.doubleArg(0)).executes(WithdrawHeartCommand::withdraw)).executes(ctx -> {
            ServerPlayerEntity player = ctx.getSource().getPlayer();
            EntityAttributeInstance attr = player.getAttributeInstance(EntityAttributes.MAX_HEALTH);
            double current = attr.getBaseValue();
            if (current > 2) {
                attr.setBaseValue(current - 2);
                player.getInventory().insertStack(new ItemStack(ModItem.HEART));
            }
            else {
                player.sendMessage(Text.literal("<Romanise> Dont do it! Dont be like me!"));
            }
            return 1;
        }));

    }
    private static int withdraw(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {

        double rawAmount = DoubleArgumentType.getDouble(ctx, "amount");

        ServerPlayerEntity p = ctx.getSource().getPlayer();

        int amount = (int) rawAmount;

        if (amount <= 0) {
            p.sendMessage(Text.literal("silly billy you broke you do not have that amount of hearts").formatted(Formatting.RED), false);
            return 1;
        }


        EntityAttributeInstance attr = p.getAttributeInstance(EntityAttributes.MAX_HEALTH);
        double currentMax = attr.getValue();
        double healthAmount = currentMax - (amount * 2);
        if (currentMax <= 2) {
            p.sendMessage(Text.literal("<Romanise> Dont do it! Dont be like me!"));
            return 1;
        }
        if (healthAmount < 2) {

            amount = (int) ((currentMax - 2) / 2);
            attr.setBaseValue(2);
        } else {
            attr.setBaseValue(healthAmount);
        }
        for (int i = 0; i < amount; i++) {
            p.getInventory().insertStack(new ItemStack(ModItem.HEART));
        }

        return 1;


    }
}

