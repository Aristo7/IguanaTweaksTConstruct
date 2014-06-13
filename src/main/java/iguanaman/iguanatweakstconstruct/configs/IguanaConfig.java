package iguanaman.iguanatweakstconstruct.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class IguanaConfig {

	// Item Ids
	public static int clayBucketLavaId;
	public static int clayBucketMilkId;
	public static int clayBucketWaterId;
	public static int clayBucketFiredId;
	public static int clayBucketUnfiredId;
	public static int clayBucketsId;

	// repairs
	public static boolean repairCostScaling;
	public static boolean repairLimitActive;
	public static int repairScalingModifier;
	public static int repairLimit;
	public static int repairCostPercentage;

	// heads
	public static int baseHeadDropChance;
	public static int beheadingHeadDropChance;

	//crafting
	public static boolean easyBlankPatternRecipe;
	public static boolean easyPartCrafting;
	public static boolean easyPatternCrafting;
	public static boolean easyToolModification;
	public static boolean easyToolCreation;


	//debug
	public static boolean logHarvestLevelChanges;
	public static boolean logMiningLevelChanges;

	//other
	public static boolean toolsNeverDespawn;
	public static boolean partTooltips;
	public static boolean partReplacement;
	public static boolean cobaltArmor;
	public static boolean removeStoneTorchRecipe;
	public static boolean removeFlintDrop;
	public static boolean addFlintRecipe;
	public static int durabilityPercentage;
	public static int miningSpeedPercentage;

	// Pickaxe mining level overrides
	public static HashMap<Integer, Integer> pickaxeOverrides = new HashMap<Integer, Integer>();

	//Restrictions
	public static boolean allowStoneTools;
	public static boolean keepRestrictedTools;
	public static List<Integer> restrictedWoodParts = new ArrayList<Integer>();
	public static List<Integer> restrictedStoneParts = new ArrayList<Integer>();
	public static List<Integer> restrictedFlintParts = new ArrayList<Integer>();
	public static List<Integer> restrictedBoneParts = new ArrayList<Integer>();
	public static List<Integer> restrictedPaperParts = new ArrayList<Integer>();
	public static List<Integer> restrictedSlimeParts = new ArrayList<Integer>();
	public static List<Integer> restrictedCactusParts = new ArrayList<Integer>();

	//Remove parts from chest 
	public static boolean removeIronParts;
	public static boolean removeObsidianParts;
	public static boolean removeCobaltParts;
	public static boolean removeArditeParts;
	public static boolean removeManyullumParts;
	public static boolean removeBronzeParts;
	public static boolean removeAlumiteParts;
	public static boolean removeSteelParts;
	
	public static void init(File file)
	{
		Configuration config = new Configuration(file);
		config.load();

		// items
		Property clayBucketUnfiredIdProperty = config.getItem("clayBucketUnfiredId", 25710);
		clayBucketUnfiredIdProperty.comment = "Item ID for the unfired clay bucket";
		clayBucketUnfiredId = clayBucketUnfiredIdProperty.getInt(25710);

		Property clayBucketFiredIdProperty = config.getItem("clayBucketFiredId", 25711);
		clayBucketFiredIdProperty.comment = "Item ID for the fired clay bucket";
		clayBucketFiredId = clayBucketFiredIdProperty.getInt(25711);

		Property clayBucketWaterIdProperty = config.getItem("clayBucketWaterId", 25712);
		clayBucketWaterIdProperty.comment = "Item ID for the water filled clay bucket";
		clayBucketWaterId = clayBucketWaterIdProperty.getInt(25712);

		Property clayBucketLavaIdProperty = config.getItem("clayBucketLavaId", 25713);
		clayBucketLavaIdProperty.comment = "Item ID for the lava filled clay bucket";
		clayBucketLavaId = clayBucketLavaIdProperty.getInt(25713);

		Property clayBucketsIdProperty = config.getItem("clayBucketsId", 25714);
		clayBucketsIdProperty.comment = "Item ID for the filled clay buckets";
		clayBucketsId = clayBucketsIdProperty.getInt(25714);

		Property clayBucketMilkIdProperty = config.getItem("clayBucketMilkId", 25715);
		clayBucketMilkIdProperty.comment = "Item ID for the milk filled clay bucket";
		clayBucketMilkId = clayBucketMilkIdProperty.getInt(25715);

		// repairs
		ConfigCategory repairsCategory = config.getCategory("repairs");
		repairsCategory.setComment("Changes to tool repairing");

		Property repairCostScalingProperty = config.get("repairs", "repairCostScaling", false);
		repairCostScalingProperty.comment = "Repairs are less effective the more a tool is repaired";
		repairCostScaling = repairCostScalingProperty.getBoolean(false);

		Property repairLimitActiveProperty = config.get("repairs", "repairLimitActive", false);
		repairLimitActiveProperty.comment = "Number of times TC tools can be repaired is limited";
		repairLimitActive = repairLimitActiveProperty.getBoolean(false);

		Property repairLimitProperty = config.get("repairs", "repairLimit", 25);
		repairLimitProperty.comment = "Number of times TC tools can be repaired (only if 'repairLimitActive' is true) (set to 0 to disable repairs)";
		repairLimit = Math.max(repairLimitProperty.getInt(25), 0);
		repairLimitProperty.set(repairLimit);

		Property repairScalingModifierProperty = config.get("repairs", "repairScalingModifier", 5);
		repairScalingModifierProperty.comment = "Repair cost doubles after this many repairs (only if 'repairCostScaling' is true)";
		repairScalingModifier = Math.max(repairScalingModifierProperty.getInt(5), 1);
		repairScalingModifierProperty.set(repairScalingModifier);

		Property repairCostPercentageProperty = config.get("repairs", "repairCostPercentage", 100);
		repairCostPercentageProperty.comment = "Increase or decrease repair costs (higher = more expensive)";
		repairCostPercentage = Math.max(repairCostPercentageProperty.getInt(100), 1);
		repairCostPercentageProperty.set(repairCostPercentage);


		// heads
		ConfigCategory headsCategory = config.getCategory("heads");
		headsCategory.setComment("Configure the mob head modifiers and drops here");

		Property baseHeadDropChanceProperty = config.get("heads", "baseHeadDropChance", 5);
		baseHeadDropChanceProperty.comment = "Base percentage chance for a head to drop (only if 'pickaxeHeads' is true)";
		baseHeadDropChance = Math.max(baseHeadDropChanceProperty.getInt(5), 0);
		baseHeadDropChanceProperty.set(baseHeadDropChance);

		Property beheadingHeadDropChanceProperty = config.get("heads", "beheadingHeadDropChance", 2);
		beheadingHeadDropChanceProperty.comment = "Percentage chance for a head to drop for each level of beheading (only if 'pickaxeHeads' is true)";
		beheadingHeadDropChance = Math.max(beheadingHeadDropChanceProperty.getInt(2), 0);
		beheadingHeadDropChanceProperty.set(beheadingHeadDropChance);


		// crafting
		ConfigCategory craftingCategory = config.getCategory("crafting");
		craftingCategory.setComment("Allow Tinkers crafting to be done in a normal crafting window");

		Property easyBlankPatternRecipeProperty = config.get("crafting", "easyBlankPatternRecipe", true);
		easyBlankPatternRecipeProperty.comment = "Allows blank patterns to be crafted with 4 sticks in a square";
		easyBlankPatternRecipe = easyBlankPatternRecipeProperty.getBoolean(true);

		Property easyPartCraftingProperty = config.get("crafting", "easyPartCrafting", true);
		easyPartCraftingProperty.comment = "Allows you to make tool parts in a normal crafting window";
		easyPartCrafting = easyPartCraftingProperty.getBoolean(true);

		Property easyPatternCraftingProperty = config.get("crafting", "easyPatternCrafting", true);
		easyPatternCraftingProperty.comment = "Allows you to rotate the the tier 1 patterns in a normal crafting window";
		easyPatternCrafting = easyPatternCraftingProperty.getBoolean(true);

		Property easyToolCreationProperty = config.get("crafting", "easyToolCreation", true);
		easyToolCreationProperty.comment = "Allows you create tinkers tools in a normal crafting window";
		easyToolCreation = easyToolCreationProperty.getBoolean(true);

		Property easyToolModificationProperty = config.get("crafting", "easyToolModification", true);
		easyToolModificationProperty.comment = "Allows you add modifications to tools in a normal crafting window";
		easyToolModification = easyToolModificationProperty.getBoolean(true);


		// debug
		ConfigCategory debugCategory = config.getCategory("debug");
		debugCategory.setComment("Debugging options");

		Property logHarvestLevelChangesProperty = config.get("debug", "logHarvestLevelChanges", false);
		logHarvestLevelChangesProperty.comment = "Outputs to the log when the harvest level of a block is changed";
		logHarvestLevelChanges = logHarvestLevelChangesProperty.getBoolean(false);

		Property logMiningLevelChangesProperty = config.get("debug", "logMiningLevelChanges", false);
		logMiningLevelChangesProperty.comment = "Outputs to the log when the mining level of a tool is changed";
		logMiningLevelChanges = logMiningLevelChangesProperty.getBoolean(false);


		// other
		ConfigCategory otherCategory = config.getCategory("other");
		otherCategory.setComment("Random stuff to configure here");

		Property cobaltArmorProperty = config.get("other", "cobaltArmor", false);
		cobaltArmorProperty.comment = "Changes diamond armor to cobalt armor (more expensive recipe)";
		cobaltArmor = cobaltArmorProperty.getBoolean(false);

		Property partTooltipsProperty = config.get("other", "partTooltips", true);
		partTooltipsProperty.comment = "Shows information about tool parts in the mouseover tooltip";
		partTooltips = partTooltipsProperty.getBoolean(true);

		Property partReplacementProperty = config.get("other", "partReplacement", true);
		partReplacementProperty.comment = "Can you replace parts of existing tools?";
		partReplacement = partReplacementProperty.getBoolean(true);

		Property removeFlintDropProperty = config.get("other", "removeFlintDrop", true);
		removeFlintDropProperty.comment = "Removes the random chance of getting flint from gravel";
		removeFlintDrop = removeFlintDropProperty.getBoolean(true);

		Property addFlintRecipeProperty = config.get("other", "addFlintRecipe", true);
		addFlintRecipeProperty.comment = "Adds a shapeless recipe to get flint from 4 gravel blocks";
		addFlintRecipe = addFlintRecipeProperty.getBoolean(true);

		Property removeStoneTorchRecipeProperty = config.get("other", "removeStoneTorchRecipe", true);
		removeStoneTorchRecipeProperty.comment = "Removes the recipe for Tinker's Construct's stone torch";
		removeStoneTorchRecipe = removeStoneTorchRecipeProperty.getBoolean(true);

		Property durabilityPercentageProperty = config.get("other", "durabilityPercentage", 50);
		durabilityPercentageProperty.comment = "Change durability of all materials here (higher = tougher)";
		durabilityPercentage = Math.max(durabilityPercentageProperty.getInt(50), 1);
		durabilityPercentageProperty.set(durabilityPercentage);

		Property miningSpeedPercentageProperty = config.get("other", "miningSpeedPercentage", 100);
		miningSpeedPercentageProperty.comment = "Change mining speed of all materials here (higher = faster)";
		miningSpeedPercentage = Math.max(miningSpeedPercentageProperty.getInt(100), 1);
		miningSpeedPercentageProperty.set(miningSpeedPercentage);

		Property toolsNeverDespawnProperty = config.get("other", "toolsNeverDespawn", true);
		toolsNeverDespawnProperty.comment = "Do Tinker's tools on the ground never despawn?";
		toolsNeverDespawn = toolsNeverDespawnProperty.getBoolean(true);


		//restrictions
		ConfigCategory restrictionsCategory = config.getCategory("restrictions");
		restrictionsCategory.setComment("See config section of mod thread for list of pattern ids. Restrictions on wood tool rods (1) blocks all vanilla wood and stone tools");

		Property allowStoneToolsProperty = config.get("restrictions", "allowStoneTools", false);
		allowStoneToolsProperty.comment = "Allow certain stone tools to be built (if equivalent flint tool can also be made, the stone version is allowed)";
		allowStoneTools = allowStoneToolsProperty.getBoolean(false);

		Property keepRestrictedToolsProperty = config.get("restrictions", "allowAllTools", false);
		keepRestrictedToolsProperty.comment = "Allow all vanilla tools to be built";
		keepRestrictedTools = keepRestrictedToolsProperty.getBoolean(false);

		Property restrictedWoodPartsProperty = config.get("restrictions", "restrictedWoodParts", new int[] {2,4,5,6,7,10,13,14,15,16,17,18,19,20,21,22,23,24});
		restrictedWoodPartsProperty.comment = "Pattern ids to restrict for wood parts. Restrictions on wood tool rods (1) will block vanilla wood and stone tools.";
		for (int i : restrictedWoodPartsProperty.getIntList()) restrictedWoodParts.add(i);

		Property restrictedStonePartsProperty = config.get("restrictions", "restrictedStoneParts", new int[] {});
		restrictedStonePartsProperty.comment = "Pattern ids to restrict for stone parts.";
		for (int i : restrictedStonePartsProperty.getIntList()) restrictedStoneParts.add(i);

		Property restrictedFlintPartsProperty = config.get("restrictions", "restrictedFlintParts", new int[] {1,5,6,7,8,9,10,11,14,15,16,17,18,19,20,21,22,23,24});
		restrictedFlintPartsProperty.comment = "Pattern ids to restrict for flint parts";
		for (int i : restrictedFlintPartsProperty.getIntList()) restrictedFlintParts.add(i);

		Property restrictedBonePartsProperty = config.get("restrictions", "restrictedBoneParts", new int[] {2,5,6,7,9,10,11,13,14,15,16,17,18,19,20,21,22,23,24});
		restrictedBonePartsProperty.comment = "Pattern ids to restrict for bone parts";
		for (int i : restrictedBonePartsProperty.getIntList()) restrictedBoneParts.add(i);

		Property restrictedPaperPartsProperty = config.get("restrictions", "restrictedPaperParts", new int[] {2,3,4,5,6,7,8,10,11,12,13,14,15,16,17,18,19,20,21,22,23,25});
		restrictedPaperPartsProperty.comment = "Pattern ids to restrict for paper parts";
		for (int i : restrictedPaperPartsProperty.getIntList()) restrictedPaperParts.add(i);

		Property restrictedSlimePartsProperty = config.get("restrictions", "restrictedSlimeParts", new int[] {2,3,4,5,6,7,8,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25});
		restrictedSlimePartsProperty.comment = "Pattern ids to restrict for slime parts";
		for (int i : restrictedSlimePartsProperty.getIntList()) restrictedSlimeParts.add(i);

		Property restrictedCactusPartsProperty = config.get("restrictions", "restrictedCactusParts", new int[] {2,3,4,5,6,7,8,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25});
		restrictedCactusPartsProperty.comment = "Pattern ids to restrict for cactus parts";
		for (int i : restrictedCactusPartsProperty.getIntList()) restrictedCactusParts.add(i);

		//Remove parts from chest 
		ConfigCategory removeCategory = config.getCategory("chestremove");
		removeCategory.setComment("Removes all parts of type from a chest");

		removeIronParts = config.get("chestremove", "removeIronParts", true, "Remove iron parts from tinker house chest").getBoolean(true);
		removeObsidianParts = config.get("chestremove", "removeObsidianParts", true, "Remove obsidian parts from tinker house chest").getBoolean(true);
		removeCobaltParts = config.get("chestremove", "removeCobaltParts", true, "Remove cobalt parts from tinker house chest").getBoolean(true);
		removeArditeParts = config.get("chestremove", "removeArditeParts", true, "Remove ardite parts from tinker house chest").getBoolean(true);
		removeManyullumParts = config.get("chestremove", "removeManyullumParts", true, "Remove manyullum parts from tinker house chest").getBoolean(true);
		removeBronzeParts = config.get("chestremove", "removeBronzeParts", true, "Remove bronze parts from tinker house chest").getBoolean(true);
		removeAlumiteParts = config.get("chestremove", "removeAlumiteParts", true, "Remove alumite parts from tinker house chest").getBoolean(true);
		removeSteelParts = config.get("chestremove", "removeSteelParts", true, "Remove steel parts from tinker house chest").getBoolean(true);

		// pickaxe mining level overrides
		ConfigCategory pickaxeoverridesCategory = config.getCategory("pickaxeoverrides");
		pickaxeoverridesCategory.setComment("Normally the mod changes the mining levels of all pickaxes to be in line with the new system, override that for specific picks here");

		Property pickaxeOverridesProperty = config.get("pickaxeoverrides", "pickaxeOverrides", new String[] {});
		pickaxeOverridesProperty.comment = "Format <itemID>:<miningLevel> (Each on a separate line)";
		for (String i : pickaxeOverridesProperty.getStringList())
		{
			String[] splt = i.split(":");
			pickaxeOverrides.put(Integer.parseInt(splt[0]), Integer.parseInt(splt[1]));
		}


		config.save();
		
	}

}
