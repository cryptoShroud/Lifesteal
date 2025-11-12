package net.potato.eat;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.potato.eat.item.HeartItemEvents;
import net.potato.eat.item.ModBlock;
import net.potato.eat.item.ModItem;
import net.potato.eat.item.WithdrawHeartCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Learningfabric implements ModInitializer {
	public static final String MOD_ID = "lifesteal";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
        ModBlock.register();
        ModItem.registerModItems();
        HeartItemEvents.register();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            boolean dedicated = environment == CommandManager.RegistrationEnvironment.DEDICATED;
            WithdrawHeartCommand.register(dispatcher, dedicated);
        });

        LOGGER.info("Hello Fabric world!");
	}
//    private static void registerCommands(){
//        CommandRegistrationCallback.EVENT.register(WithdrawHeartCommand::register);
//    }
}