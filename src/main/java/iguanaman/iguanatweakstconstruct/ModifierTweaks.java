package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.configs.IguanaConfig;
import iguanaman.iguanatweakstconstruct.configs.LevelsConfig;
import iguanaman.iguanatweakstconstruct.configs.ModifierConfig;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaActiveToolMod;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModAttack;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModClean;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModHeads;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModLapis;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModRedstone;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModRepair;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModUpgrade;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.common.TRepo;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMod;
import tconstruct.modifiers.tools.ModDurability;
import tconstruct.modifiers.tools.ModInteger;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModifierTweaks {

	public static void init()
	{

		// REMOVE OLD MODIFIERS
		IguanaLog.log("Removing old modifiers");
		Iterator<ToolMod> i = ToolBuilder.instance.toolMods.iterator();
		while (i.hasNext()) {
			ToolMod mod = i.next();
			if (mod.key == "Emerald" || mod.key == "Diamond" ||
					mod.key == "Tier1Free" && LevelsConfig.toolLeveling && !ModifierConfig.ticExtraModifier ||
					mod.key == "Tier2Free" && !ModifierConfig.ticExtraModifier ||
					mod.key == "Moss" || mod.key == "Lapis" || mod.key == "ModAttack" || mod.key == "Redstone"
					|| mod.key == "")
				//IguanaLog.log("Removing old " + mod.key + " modifier");
				i.remove();
		}


		// Change recipes
		if (ModifierConfig.moreExpensiveSilkyCloth)
		{
			RecipeRemover.removeAnyRecipe(new ItemStack(TRepo.materials, 1, 25));
			GameRegistry.addRecipe(new ItemStack(TRepo.materials, 1, 25), "sss", "sns", "sss", 'n', new ItemStack(TRepo.materials, 1, 14), 's', new ItemStack(Items.silk)); //Silky Cloth
			GameRegistry.addRecipe(new ItemStack(TRepo.materials, 1, 25), "sss", "sns", "sss", 'n', new ItemStack(Items.gold_ingot), 's', new ItemStack(Item.silk)); //Silky Cloth
		}

		if (ModifierConfig.moreExpensiveSilkyJewel)
		{
			RecipeRemover.removeAnyRecipe(new ItemStack(TRepo.materials, 1, 26));
			GameRegistry.addRecipe(new ItemStack(TRepo.materials, 1, 26), " c ", "cec", " c ", 'c', new ItemStack(TRepo.materials, 1, 25), 'e', new ItemStack(Blocks.emerald_block)); //Silky Jewel
		}


		// REPLACE OLD MODIFIERS
		IguanaLog.log("Replacing old modifiers");

		if (IguanaConfig.partReplacement) ToolBuilder.registerToolMod(new IguanaModUpgrade());
		ToolBuilder.registerToolMod(new IguanaModRepair());
		ToolBuilder.registerToolMod(new ModInteger(new ItemStack[] { new ItemStack(TRepo.materials, 1, 6) }, 4, "Moss", ModifierConfig.mossRepairSpeed, "\u00a72", "Auto-Repair"));
		ToolBuilder.registerToolMod(new ModDurability(new ItemStack[] { new ItemStack(Item.emerald) }, 1, 0, 0.5f, ModifierConfig.emeraldboost, "Emerald", "\u00a72Durability +50%", "\u00a72"));
		if (ModifierConfig.diamondPickaxeBoost)
		{
			ToolBuilder.registerToolMod(new ModDurability(new ItemStack[] { new ItemStack(Items.diamond) }, 0, 500, 0f, MinecraftForge.getBlockHarvestLevel(Blocks.obsidian, 0, "pickaxe"), "Diamond", "\u00a7bDurability +500", "\u00a7b"));
		} else {
			ToolBuilder.registerToolMod(new ModDurability(new ItemStack[] { new ItemStack(Items.diamond) }, 0, 500, 0f, 0, "Diamond", "\u00a7bDurability +500", "\u00a7b"));
		}

		ItemStack lapisItem = new ItemStack(Items.dye, 1, 4);
		ItemStack lapisBlock = new ItemStack(Blocks.lapis_block);
//	    ToolBuilder.registerToolMod(new IguanaModLapis(new ItemStack[] { lapisBlock, lapisItem }, 10, new int[] { 9, 1 }));
		ToolBuilder.registerToolMod(new IguanaModLapis(new ItemStack[] { lapisItem }, 10, 1));
		ToolBuilder.registerToolMod(new IguanaModLapis(new ItemStack[] { lapisItem, lapisItem }, 10, 2));
		ToolBuilder.registerToolMod(new IguanaModLapis(new ItemStack[] { lapisBlock }, 10, 9));
		ToolBuilder.registerToolMod(new IguanaModLapis(new ItemStack[] { lapisItem, lapisBlock }, 10, 10));
		ToolBuilder.registerToolMod(new IguanaModLapis(new ItemStack[] { lapisBlock, lapisBlock }, 10, 18));
		ToolBuilder.registerToolMod(new IguanaModLapis(new ItemStack[] { lapisBlock, lapisBlock, lapisBlock }, 10, 27));

		ItemStack quartzItem = new ItemStack(Items.quartz);
		ItemStack quartzBlock = new ItemStack(Blocks.quartz_block, 1, Short.MAX_VALUE);
		ToolBuilder.registerToolMod(new IguanaModAttack("Quartz", new ItemStack[] { quartzItem }, 11, 1));
		ToolBuilder.registerToolMod(new IguanaModAttack("Quartz", new ItemStack[] { quartzItem, quartzItem }, 11, 2));
		ToolBuilder.registerToolMod(new IguanaModAttack("Quartz", new ItemStack[] { quartzBlock }, 11, 4));
		ToolBuilder.registerToolMod(new IguanaModAttack("Quartz", new ItemStack[] { quartzItem, quartzBlock }, 11, 5));
		ToolBuilder.registerToolMod(new IguanaModAttack("Quartz", new ItemStack[] { quartzBlock, quartzBlock }, 11, 8));

		ItemStack redstoneItem = new ItemStack(Items.redstone);
		ItemStack redstoneBlock = new ItemStack(Blocks.redstone_block);
		ToolBuilder.registerToolMod(new IguanaModRedstone(new ItemStack[] { redstoneItem }, 2, 1));
		ToolBuilder.registerToolMod(new IguanaModRedstone(new ItemStack[] { redstoneItem, redstoneItem }, 2, 2));
		ToolBuilder.registerToolMod(new IguanaModRedstone(new ItemStack[] { redstoneBlock }, 2, 9));
		ToolBuilder.registerToolMod(new IguanaModRedstone(new ItemStack[] { redstoneItem, redstoneBlock }, 2, 10));
		ToolBuilder.registerToolMod(new IguanaModRedstone(new ItemStack[] { redstoneBlock, redstoneBlock }, 2, 18));

		// CLEAN MODIFIER
		if (ModifierConfig.addCleanModifier) ToolBuilder.registerToolMod(new IguanaModClean());

		// MINING BOOST MODIFIERS
		if (ModifierConfig.mobHeadPickaxeBoost)
		{
			IguanaLog.log("Adding mob head modifiers");

			// add modifers
			ToolBuilder.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Items.skull, 1, 0) }, 20, ModifierConfig.skeletonboost, "Skeleton Skull", "\u00a7fBoosted", "\u00a7f"));
			ToolBuilder.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Items.skull, 1, 2) }, 21, ModifierConfig.zombieboost, "Zombie Head", "\u00a72Boosted", "\u00a72"));
			ToolBuilder.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Items.skull, 1, 4) }, 22, ModifierConfig.creeperboost, "Creeper Head", "\u00a7aBoosted", "\u00a7a"));
			ToolBuilder.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Items.skull, 1, 5) }, 23, ModifierConfig.endermanboost, "Enderman Head", "\u00a78Boosted", "\u00a78"));
			ToolBuilder.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Items.skull, 1, 1) }, 24, ModifierConfig.witherskeletonboost, "Wither Skeleton Skull", "\u00a78Boosted", "\u00a78"));
			ToolBuilder.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Items.skull, 1, 6) }, 26, ModifierConfig.zombiepigmanboost, "Zombie Pigman Skull", "\u00a78Boosted", "\u00a78"));
			ToolBuilder.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Items.skull, 1, 7) }, 27, ModifierConfig.blazeboost, "Blaze Skull", "\u00a78Boosted", "\u00a78"));
			ToolBuilder.registerToolMod(new IguanaModHeads(new ItemStack[] { new ItemStack(Items.nether_star) }, 25, ModifierConfig.netherstarboost, "Nether Star", "\u00a73Boosted", "\u00a73"));

			// rendering code
			ToolCore[] tools = new ToolCore[] { TRepo.pickaxe, TRepo.hammer };
			int[] modifierIds = new int[] { 20, 21, 22, 23, 24, 25, 26, 27 };
			String[] renderNames = new String[] { "skeletonskull", "zombiehead", "creeperhead", "endermanhead", "witherskeletonskull", "netherstar", "zombiepigmanhead", "blazehead" };

			for (ToolCore tool : tools)
				for (int index = 0; index < modifierIds.length; ++index)
					TConstructClientRegistry.addEffectRenderMapping(tool, modifierIds[index], "iguanatweakstconstruct", renderNames[index], true);
		}


		// LEVELING MODIFIER
		if (LevelsConfig.toolLeveling)
		{
			IguanaLog.log("Adding leveling active modifier");
			TConstructRegistry.activeModifiers.add(0, new IguanaActiveToolMod());
		}
	}

}
