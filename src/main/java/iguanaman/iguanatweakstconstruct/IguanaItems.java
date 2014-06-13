package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.configs.IguanaConfig;
import iguanaman.iguanatweakstconstruct.items.ClayBucket;
import iguanaman.iguanatweakstconstruct.items.ClayBucketFilled;
import iguanaman.iguanatweakstconstruct.items.ClayBucketMilk;
import iguanaman.iguanatweakstconstruct.items.IguanaItemSkull;
import iguanaman.iguanatweakstconstruct.items.IguanaPattern;
import iguanaman.iguanatweakstconstruct.items.IguanaToolPart;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tconstruct.common.BowRecipe;
import tconstruct.common.TContent;
import tconstruct.common.TRepo;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.util.IPattern;
import tconstruct.util.config.PHConstruct;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class IguanaItems {

	public static Item clayBucketLava;
	public static Item clayBucketWater;
	public static Item clayBucketMilk;
	public static Item clayBucketFired;
	public static Item clayBucketUnfired;
	public static Item clayBuckets;

	public static void init() {

		// CLAY BUCKETS
		IguanaLog.log("Adding clay buckets");
		clayBucketUnfired = new Item(IguanaConfig.clayBucketUnfiredId)
				.setUnlocalizedName("iguanatweakstconstruct:clayBucketUnfired")
				.setTextureName("iguanatweakstconstruct:clayBucketUnfired")
				.setMaxStackSize(16).setCreativeTab(CreativeTabs.tabMisc);
		clayBucketFired = new ClayBucket(IguanaConfig.clayBucketFiredId, 0)
				.setUnlocalizedName("iguanatweakstconstruct:clayBucketFired")
				.setTextureName("iguanatweakstconstruct:clayBucketFired")
				.setMaxStackSize(16);
		clayBucketWater = new ClayBucket(IguanaConfig.clayBucketWaterId,
				Blocks.water)
				.setUnlocalizedName("iguanatweakstconstruct:clayBucketWater")
				.setTextureName("iguanatweakstconstruct:clayBucketWater")
				.setContainerItem(clayBucketFired);
		clayBucketLava = new ClayBucket(IguanaConfig.clayBucketLavaId,
				Block.lavaMoving.blockID).setUnlocalizedName(
				"iguanatweakstconstruct:clayBucketLava").setTextureName(
				"iguanatweakstconstruct:clayBucketLava");
		clayBucketMilk = new ClayBucketMilk(IguanaConfig.clayBucketMilkId)
				.setUnlocalizedName("iguanatweakstconstruct:clayBucketMilk")
				.setTextureName("iguanatweakstconstruct:clayBucketMilk")
				.setContainerItem(clayBucketFired);
		// clayBuckets = new ClayBucketFilled(IguanaConfig.clayBucketsId);

		LanguageRegistry.addName(clayBucketUnfired, "Unfired Clay Bucket");
		LanguageRegistry.addName(clayBucketFired, "Clay Bucket");
		LanguageRegistry.addName(clayBucketWater, "Clay Bucket (Water)");
		LanguageRegistry.addName(clayBucketLava, "Clay Bucket (Lava)");
		LanguageRegistry.addName(clayBucketMilk, "Clay Bucket (Milk)");
		GameRegistry
				.addRecipe(new ShapedOreRecipe(
						new ItemStack(clayBucketUnfired), "c c", " c ", 'c',
						new ItemStack(Items.clay_ball)));
		GameRegistry.addSmelting(clayBucketUnfired, new ItemStack(
				clayBucketFired), 0.0F);
		OreDictionary.registerOre("listAllmilk", clayBucketMilk);

		ItemStack emptyClayBucket = new ItemStack(clayBucketFired);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER,
				new ItemStack(clayBucketWater), emptyClayBucket);
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.LAVA,
				new ItemStack(clayBucketLava), emptyClayBucket);

		// TCON PARTS
		TRepo.toolRod = new IguanaToolPart("_rod", "ToolRod")
				.setUnlocalizedName("tconstruct.ToolRod");
		TRepo.pickaxeHead = new IguanaToolPart("_pickaxe_head", "PickHead")
				.setUnlocalizedName("tconstruct.PickaxeHead");
		TRepo.shovelHead = new IguanaToolPart("_shovel_head", "ShovelHead")
				.setUnlocalizedName("tconstruct.ShovelHead");
		TRepo.hatchetHead = new IguanaToolPart("_axe_head", "AxeHead")
				.setUnlocalizedName("tconstruct.AxeHead");
		TRepo.binding = new IguanaToolPart("_binding", "Binding")
				.setUnlocalizedName("tconstruct.Binding");
		TRepo.toughBinding = new IguanaToolPart("_toughbind", "ToughBind")
				.setUnlocalizedName("tconstruct.ThickBinding");
		TRepo.toughRod = new IguanaToolPart("_toughrod", "ToughRod")
				.setUnlocalizedName("tconstruct.ThickRod");
		TRepo.largePlate = new IguanaToolPart("_largeplate", "LargePlate")
				.setUnlocalizedName("tconstruct.LargePlate");

		TRepo.swordBlade = new IguanaToolPart("_sword_blade", "SwordBlade")
				.setUnlocalizedName("tconstruct.SwordBlade");
		TRepo.wideGuard = new IguanaToolPart("_large_guard", "LargeGuard")
				.setUnlocalizedName("tconstruct.LargeGuard");
		TRepo.handGuard = new IguanaToolPart("_medium_guard", "MediumGuard")
				.setUnlocalizedName("tconstruct.MediumGuard");
		TRepo.crossbar = new IguanaToolPart("_crossbar", "Crossbar")
				.setUnlocalizedName("tconstruct.Crossbar");
		TRepo.knifeBlade = new IguanaToolPart("_knife_blade", "KnifeBlade")
				.setUnlocalizedName("tconstruct.KnifeBlade");
		TRepo.fullGuard = new IguanaToolPart("_full_guard", "FullGuard")
				.setUnlocalizedName("tconstruct.FullGuard");

		TRepo.frypanHead = new IguanaToolPart("_frypan_head", "FrypanHead")
				.setUnlocalizedName("tconstruct.FrypanHead");
		TRepo.signHead = new IguanaToolPart("_battlesign_head", "SignHead")
				.setUnlocalizedName("tconstruct.SignHead");
		TRepo.chiselHead = new IguanaToolPart("_chisel_head", "ChiselHead")
				.setUnlocalizedName("tconstruct.ChiselHead");

		TRepo.scytheBlade = new IguanaToolPart("_scythe_head", "ScytheHead")
				.setUnlocalizedName("tconstruct.ScytheBlade");
		TRepo.broadAxeHead = new IguanaToolPart("_lumberaxe_head", "LumberHead")
				.setUnlocalizedName("tconstruct.LumberHead");
		TRepo.excavatorHead = new IguanaToolPart("_excavator_head",
				"ExcavatorHead").setUnlocalizedName("tconstruct.ExcavatorHead");
		TRepo.largeSwordBlade = new IguanaToolPart("_large_sword_blade",
				"LargeSwordBlade")
				.setUnlocalizedName("tconstruct.LargeSwordBlade");
		TRepo.hammerHead = new IguanaToolPart("_hammer_head", "HammerHead")
				.setUnlocalizedName("tconstruct.HammerHead");

		TRepo.arrowhead = new IguanaToolPart("_arrowhead", "ArrowHead")
				.setUnlocalizedName("tconstruct.Arrowhead");

		// RE-ADD TOOL RECIPES
		ToolBuilder tb = ToolBuilder.instance;
		ToolBuilder.addNormalToolRecipe(TRepo.pickaxe, TRepo.pickaxeHead,
				TRepo.toolRod, TRepo.binding);
		ToolBuilder.addNormalToolRecipe(TRepo.broadsword, TRepo.swordBlade,
				TRepo.toolRod, TRepo.wideGuard);
		ToolBuilder.addNormalToolRecipe(TRepo.hatchet, TRepo.hatchetHead,
				TRepo.toolRod);
		ToolBuilder.addNormalToolRecipe(TRepo.shovel, TRepo.shovelHead,
				TRepo.toolRod);
		ToolBuilder.addNormalToolRecipe(TRepo.longsword, TRepo.swordBlade,
				TRepo.toolRod, TRepo.handGuard);
		ToolBuilder.addNormalToolRecipe(TRepo.rapier, TRepo.swordBlade,
				TRepo.toolRod, TRepo.crossbar);
		ToolBuilder.addNormalToolRecipe(TRepo.frypan, TRepo.frypanHead,
				TRepo.toolRod);
		ToolBuilder.addNormalToolRecipe(TRepo.battlesign, TRepo.signHead,
				TRepo.toolRod);
		ToolBuilder.addNormalToolRecipe(TRepo.mattock, TRepo.hatchetHead,
				TRepo.toolRod, TRepo.shovelHead);
		ToolBuilder.addNormalToolRecipe(TRepo.dagger, TRepo.knifeBlade,
				TRepo.toolRod, TRepo.crossbar);
		ToolBuilder.addNormalToolRecipe(TRepo.cutlass, TRepo.swordBlade,
				TRepo.toolRod, TRepo.fullGuard);
		ToolBuilder.addNormalToolRecipe(TRepo.chisel, TRepo.chiselHead,
				TRepo.toolRod);

		ToolBuilder.addNormalToolRecipe(TRepo.scythe, TRepo.scytheBlade,
				TRepo.toughRod, TRepo.toughBinding, TRepo.toughRod);
		ToolBuilder.addNormalToolRecipe(TRepo.lumberaxe, TRepo.broadAxeHead,
				TRepo.toughRod, TRepo.largePlate, TRepo.toughBinding);
		ToolBuilder.addNormalToolRecipe(TRepo.cleaver, TRepo.largeSwordBlade,
				TRepo.toughRod, TRepo.largePlate, TRepo.toughRod);
		ToolBuilder.addNormalToolRecipe(TRepo.excavator, TRepo.excavatorHead,
				TRepo.toughRod, TRepo.largePlate, TRepo.toughBinding);
		ToolBuilder.addNormalToolRecipe(TRepo.hammer, TRepo.hammerHead,
				TRepo.toughRod, TRepo.largePlate, TRepo.largePlate);
		ToolBuilder.addNormalToolRecipe(TRepo.battleaxe, TRepo.broadAxeHead,
				TRepo.toughRod, TRepo.broadAxeHead, TRepo.toughBinding);

		ToolBuilder.addNormalToolRecipe(TRepo.arrow, TRepo.arrowhead,
				TRepo.toolRod, TRepo.fletching);
		ToolBuilder.addCustomToolRecipe(new BowRecipe(TRepo.toolRod,
				TRepo.bowstring, TRepo.toolRod, TRepo.shortbow));

		// SKULL ITEM
		IguanaLog.log("Adding skull item");
		//Item.itemsList[Items.skull] = null;
		Items.skull = new IguanaItemSkull(141).setUnlocalizedName("skull")
				.setTextureName("skull");
		LanguageRegistry.addName(new ItemStack(Items.skull, 1, 5),
				"Enderman Head");
		LanguageRegistry.addName(new ItemStack(Items.skull, 1, 6),
				"Zombie Pigman Head");
		LanguageRegistry
				.addName(new ItemStack(Items.skull, 1, 7), "Blaze Head");

		// COBALT ARMOR
		if (IguanaConfig.cobaltArmor) {
			IguanaLog.log("Changing diamond armor to cobalt armor");

			LanguageRegistry.addName(new ItemStack(Items.diamond_helmet),
					"Cobalt Helmet");
			LanguageRegistry.addName(new ItemStack(Items.diamond_chestplate),
					"Cobalt Chestplate");
			LanguageRegistry.addName(new ItemStack(Items.diamond_boots),
					"Cobalt Boots");
			LanguageRegistry.addName(new ItemStack(Items.diamond_leggings),
					"Cobalt Leggings");
			RecipeRemover.removeAnyRecipe(new ItemStack(Items.diamond_helmet));
			RecipeRemover.removeAnyRecipe(new ItemStack(
					Items.diamond_chestplate));
			RecipeRemover.removeAnyRecipe(new ItemStack(Items.diamond_boots));
			RecipeRemover
					.removeAnyRecipe(new ItemStack(Items.diamond_leggings));
			String[][] recipePatterns = new String[][] { { "XXX", "X X" },
					{ "X X", "XXX", "XXX" }, { "XXX", "X X", "X X" },
					{ "X X", "X X" } };
			Object[] recipeItems = new Object[] { Items.diamond_helmet,
					Items.diamond_chestplate, Items.diamond_leggings,
					Items.diamond_boots };

			for (int j = 0; j < recipeItems.length; ++j) {
				Item item = (Item) recipeItems[j];
				CraftingManager.getInstance().addRecipe(
						new ItemStack(item),
						new Object[] { recipePatterns[j], 'X',
								new ItemStack(TRepo.materials, 1, 3) });
			}
		}

		// NEW TCON PATTERNS
		//Item.itemsList[TRepo.woodPattern] = null;
		// Item.itemsList[TContent.metalPattern.itemID] = null;
		TRepo.woodPattern = new IguanaPattern("pattern_", "materials/")
				.setUnlocalizedName("tconstruct.Pattern");
		// TContent.metalPattern = new
		// IguanaMetalPattern(PHConstruct.metalPattern, "cast_",
		// "materials/").setUnlocalizedName("tconstruct.MetalPattern");
		PatternBuilder.instance.addToolPattern((IPattern) TRepo.woodPattern);

	}

}
