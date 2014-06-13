package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.configs.IguanaConfig;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ChestGenHooks;

public class RemoveVanillaTools
{

	public static void init()
	{
		// Remove vanilla tools
		IguanaLog.log("Removing vanilla tool recipes");

		if (!IguanaConfig.keepRestrictedTools)
		{
			// Wood
			if (IguanaConfig.restrictedWoodParts.contains(1) || IguanaConfig.restrictedWoodParts.contains(2) || IguanaConfig.restrictedWoodParts.contains(9))
				RemoveVanillaTool(Items.wooden_pickaxe);

			if (IguanaConfig.restrictedWoodParts.contains(1) || IguanaConfig.restrictedWoodParts.contains(3))
				RemoveVanillaTool(Items.wooden_shovel);

			if (IguanaConfig.restrictedWoodParts.contains(1) || IguanaConfig.restrictedWoodParts.contains(4))
				RemoveVanillaTool(Items.wooden_axe);

			if (IguanaConfig.restrictedWoodParts.contains(1) || IguanaConfig.restrictedWoodParts.contains(5) || IguanaConfig.restrictedWoodParts.contains(6))
				RemoveVanillaTool(Items.wooden_sword);

			// Stone
			if (!IguanaConfig.allowStoneTools || IguanaConfig.restrictedWoodParts.contains(1)
					|| (IguanaConfig.restrictedFlintParts.contains(2) && IguanaConfig.restrictedStoneParts.contains(2))
					|| (IguanaConfig.restrictedWoodParts.contains(9) && IguanaConfig.restrictedStoneParts.contains(9) && IguanaConfig.restrictedFlintParts.contains(9)))
				RemoveVanillaTool(Items.stone_pickaxe);

			if (!IguanaConfig.allowStoneTools || IguanaConfig.restrictedWoodParts.contains(1)
					|| (IguanaConfig.restrictedFlintParts.contains(3) && IguanaConfig.restrictedStoneParts.contains(3)))
				RemoveVanillaTool(Items.stone_shovel);

			if (!IguanaConfig.allowStoneTools || IguanaConfig.restrictedWoodParts.contains(1)
					|| (IguanaConfig.restrictedFlintParts.contains(4) && IguanaConfig.restrictedStoneParts.contains(4)))
				RemoveVanillaTool(Items.stone_axe);

			if (!IguanaConfig.allowStoneTools || IguanaConfig.restrictedWoodParts.contains(1)
					|| (IguanaConfig.restrictedFlintParts.contains(5) && IguanaConfig.restrictedStoneParts.contains(5))
					|| (IguanaConfig.restrictedWoodParts.contains(6) && IguanaConfig.restrictedFlintParts.contains(6) && IguanaConfig.restrictedStoneParts.contains(6)))
				RemoveVanillaTool(Items.stone_sword);
		}
	}

	public static void RemoveVanillaTool(Item item)
	{
		RecipeRemover.removeAnyRecipe(new ItemStack(item));
		ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).removeItem(new ItemStack(item));
	}
}
