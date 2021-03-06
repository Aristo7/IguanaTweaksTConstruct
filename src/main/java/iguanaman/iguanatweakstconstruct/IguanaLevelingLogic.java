package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.configs.IguanaConfig;
import iguanaman.iguanatweakstconstruct.configs.LevelsConfig;
import iguanaman.iguanatweakstconstruct.configs.ModifierConfig;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModAntiSpider;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModAttack;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModBlaze;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModLapis;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModPiston;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModRedstone;
import iguanaman.iguanatweakstconstruct.modifiers.IguanaModSmite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import tconstruct.items.tools.BattleSign;
import tconstruct.items.tools.Battleaxe;
import tconstruct.items.tools.Broadsword;
import tconstruct.items.tools.Cleaver;
import tconstruct.items.tools.Cutlass;
import tconstruct.items.tools.Dagger;
import tconstruct.items.tools.Excavator;
import tconstruct.items.tools.FryingPan;
import tconstruct.items.tools.Hammer;
import tconstruct.items.tools.Hatchet;
import tconstruct.items.tools.Longsword;
import tconstruct.items.tools.LumberAxe;
import tconstruct.items.tools.Mattock;
import tconstruct.items.tools.Pickaxe;
import tconstruct.items.tools.Rapier;
import tconstruct.items.tools.Scythe;
import tconstruct.items.tools.Shortbow;
import tconstruct.items.tools.Shovel;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.HarvestTool;
import tconstruct.library.tools.ToolMod;
import tconstruct.library.tools.Weapon;
import tconstruct.modifiers.tools.ModAntiSpider;
import tconstruct.modifiers.tools.ModInteger;
import tconstruct.modifiers.tools.ModReinforced;
import tconstruct.modifiers.tools.ModSmite;

public class IguanaLevelingLogic {

	public static String getXpString(ItemStack tool, boolean debug) {
		return getXpString(tool, debug, null);
	}

	public static String getXpString(ItemStack tool, boolean debug, boolean pick) {
		return getXpString(tool, debug, null, pick);
	}

	public static String getXpString(ItemStack tool, boolean debug,
			NBTTagCompound tags) {
		return getXpString(tool, debug, tags, false);
	}

	public static String getXpString(ItemStack tool, boolean debug,
			NBTTagCompound tags, boolean pick) {
		if (tags == null)
			tags = tool.getTagCompound().getCompoundTag("InfiTool");

		int requiredXp = getRequiredXp(tool, tags, pick);
		long currentXp = pick ? tags.getLong("HeadEXP") : tags
				.getLong("ToolEXP");
		float xpPercentage = (float) currentXp / (float) requiredXp * 100f;
		String xpPercentageString = String.format("%.2f", xpPercentage) + "%";

		String prefix = pick ? "Boost XP: " : "XP: ";

		if (LevelsConfig.detailedXpTooltip)
			return prefix + Long.toString(currentXp) + " / "
					+ Integer.toString(requiredXp) + " (" + xpPercentageString
					+ ")";
		else
			return prefix + xpPercentageString;
	}

	public static String getLevelTooltip(int level) {
		switch (level) {
		case 1:
			return "Skill Level: \u00a74" + LevelsConfig.level1name;
		case 2:
			return "Skill Level: \u00a76" + LevelsConfig.level2name;
		case 3:
			return "Skill Level: \u00a7e" + LevelsConfig.level3name;
		case 4:
			return "Skill Level: \u00a72" + LevelsConfig.level4name;
		case 5:
			return "Skill Level: \u00a73" + LevelsConfig.level5name;
		case 6:
			return "Skill Level: \u00a7d" + LevelsConfig.level6name;
		case 7:
			return "Skill Level: \u00a7d" + LevelsConfig.level7name;
		case 8:
			return "Skill Level: \u00a7d" + LevelsConfig.level8name;
		case 9:
			return "Skill Level: \u00a7d" + LevelsConfig.level9name;
		case 10:
			return "Skill Level: \u00a7d" + LevelsConfig.level10name;
		default:
			return "Skill Level: \u00a7d" + LevelsConfig.level10name;
		}
	}

	public static void updateXP(ItemStack tool, long toolXP, long headXP) {

		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

		if (!tags.hasKey("ToolLevel"))
			return;

		int level = tags.getInteger("ToolLevel");
		int hLevel = tags.hasKey("HarvestLevel") ? hLevel = tags
				.getInteger("HarvestLevel") : -1;

		boolean leveled = false;
		boolean pickLeveled = false;

		if (tags.hasKey("ToolEXP") && level >= 1
				&& level < LevelsConfig.maxlevel && toolXP >= 0) {
			tags.setLong("ToolEXP", toolXP);

			// CHECK FOR LEVEL UP
			if (toolXP >= getRequiredXp(tool, tags)) {
				LevelUpTool(tool);
				leveled = true;
			}
		}

		// Recheck level
		level = tags.getInteger("ToolLevel");
		if (tags.hasKey("HarvestLevel"))
			hLevel = tags.getInteger("HarvestLevel");

		// tooltip lists
		List<String> tips = new ArrayList<String>();
		List<String> modifierTips = new ArrayList<String>();

		// add mining level tooltip
		if (tool.getItem() instanceof Pickaxe
				|| tool.getItem() instanceof Hammer) {
			tips.add("Mining Level: "
					+ IguanaTweaksTConstruct.getHarvestLevelName(hLevel));
			modifierTips.add("");
		}

		tips.add(getLevelTooltip(level));
		modifierTips.add("");

		if (LevelsConfig.showTooltipXP) {
			if (level < LevelsConfig.maxlevel) {
				tips.add(getXpString(tool, false, false));
				modifierTips.add("");
			}

			if (ModifierConfig.levelingPickaxeBoost)
				if (hLevel >= 1
						&& hLevel < 16
						&& !tags.hasKey("HarvestLevelModified")
						&& (tool.getItem() instanceof Pickaxe || tool.getItem() instanceof Hammer)) {
					tips.add(getXpString(tool, false, true));
					modifierTips.add("");
				}
		}

		// get and remove tooltips
		int tipNum = 0;
		while (true) {
			String tip = "Tooltip" + ++tipNum;
			if (tags.hasKey(tip)) {
				String tipString = tags.getString(tip);
				if (!tipString.startsWith("XP:")
						&& !tipString.startsWith("Head XP:")
						&& !tipString.startsWith("Boost XP:")
						&& !tipString.startsWith("Skill Level:")
						&& !tipString.startsWith("Mining Level:")
						&& !tipString.contains("Requires boost")) {
					tips.add(tipString);
					modifierTips.add(tags.getString("ModifierTip" + tipNum));
				}
				tags.removeTag(tip);
				tags.removeTag("ModifierTip" + tipNum);
			} else
				break;
		}

		if (pickLeveled) {
			tips.add("\u00a76Boosted");
			modifierTips.add("");
		}

		// write tips
		for (int i = 1; i <= tips.size(); ++i)
			if (tips.get(i - 1) != null) {
				tags.setString("Tooltip" + i, tips.get(i - 1));
				if (modifierTips.get(i - 1) != null)
					tags.setString("ModifierTip" + i, modifierTips.get(i - 1));
				else
					tags.setString("ModifierTip" + i, "");
			}
	}

	public static void updateXP(ItemStack tool, EntityPlayer player,
			long toolXP, long headXP) {

		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

		if (!tags.hasKey("ToolLevel"))
			return;

		int level = tags.getInteger("ToolLevel");
		int hLevel = tags.hasKey("HarvestLevel") ? hLevel = tags
				.getInteger("HarvestLevel") : -1;

		boolean leveled = false;
		boolean pickLeveled = false;

		if (tags.hasKey("ToolEXP") && level >= 1
				&& level < LevelsConfig.maxlevel && toolXP >= 0) {
			tags.setLong("ToolEXP", toolXP);

			// CHECK FOR LEVEL UP
			if (toolXP >= getRequiredXp(tool, tags)) {
				LevelUpTool(tool, player);
				leveled = true;
			}
		}

		if (ModifierConfig.levelingPickaxeBoost && tags.hasKey("HeadEXP")
				&& !tags.hasKey("HarvestLevelModified"))
			if (hLevel >= 1
					&& (!ModifierConfig.pickaxeBoostRequired && hLevel < 15 || ModifierConfig.pickaxeBoostRequired
							&& hLevel < 16)) {
				tags.setLong("HeadEXP", headXP);

				// CHECK FOR PICK LEVEL UP
				if (headXP >= getRequiredXp(tool, true)) {
					LevelUpPick(tool, player, leveled);
					pickLeveled = true;
				}
			}

		if ((leveled || pickLeveled) && !player.worldObj.isRemote)
			player.worldObj.playSoundAtEntity(player,
					"iguanatweakstconstruct:chime", 1.0F, 1.0F);

		// Recheck level
		level = tags.getInteger("ToolLevel");
		if (tags.hasKey("HarvestLevel"))
			hLevel = tags.getInteger("HarvestLevel");

		// tooltip lists
		List<String> tips = new ArrayList<String>();
		List<String> modifierTips = new ArrayList<String>();

		// add mining level tooltip
		if (tool.getItem() instanceof Pickaxe
				|| tool.getItem() instanceof Hammer) {
			tips.add("Mining Level: "
					+ IguanaTweaksTConstruct.getHarvestLevelName(hLevel));
			modifierTips.add("");
		}

		tips.add(getLevelTooltip(level));
		modifierTips.add("");

		if (LevelsConfig.showTooltipXP) {
			if (level < LevelsConfig.maxlevel) {
				tips.add(getXpString(tool, false, false));
				modifierTips.add("");
			}

			if (ModifierConfig.levelingPickaxeBoost)
				if (hLevel >= 1
						&& hLevel < 16
						&& !tags.hasKey("HarvestLevelModified")
						&& (tool.getItem() instanceof Pickaxe || tool.getItem() instanceof Hammer)) {
					tips.add(getXpString(tool, false, true));
					modifierTips.add("");
				}
		}

		// get and remove tooltips
		int tipNum = 0;
		while (true) {
			String tip = "Tooltip" + ++tipNum;
			if (tags.hasKey(tip)) {
				String tipString = tags.getString(tip);
				if (!tipString.startsWith("XP:")
						&& !tipString.startsWith("Head XP:")
						&& !tipString.startsWith("Boost XP:")
						&& !tipString.startsWith("Skill Level:")
						&& !tipString.startsWith("Mining Level:")
						&& !tipString.contains("Requires boost")) {
					tips.add(tipString);
					modifierTips.add(tags.getString("ModifierTip" + tipNum));
				}
				tags.removeTag(tip);
				tags.removeTag("ModifierTip" + tipNum);
			} else
				break;
		}

		if (pickLeveled) {
			tips.add("\u00a76Boosted");
			modifierTips.add("");
		}

		// write tips
		for (int i = 1; i <= tips.size(); ++i)
			if (tips.get(i - 1) != null) {
				tags.setString("Tooltip" + i, tips.get(i - 1));
				if (modifierTips.get(i - 1) != null)
					tags.setString("ModifierTip" + i, modifierTips.get(i - 1));
				else
					tags.setString("ModifierTip" + i, "");
			}
	}

	public static void addXP(ItemStack tool, long xp) {
		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

		Long toolXP = tags.hasKey("ToolEXP") ? tags.getLong("ToolEXP") + xp
				: -1;
		Long headXP = tags.hasKey("HeadEXP") ? tags.getLong("HeadEXP") + xp
				: -1;

		updateXP(tool, toolXP, headXP);
	}

	public static void addXP(ItemStack tool, EntityPlayer player, long xp) {
		if (player.capabilities.isCreativeMode)
			return;

		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");

		Long toolXP = tags.hasKey("ToolEXP") ? tags.getLong("ToolEXP") + xp
				: -1;
		Long headXP = tags.hasKey("HeadEXP") ? tags.getLong("HeadEXP") + xp
				: -1;

		updateXP(tool, player, toolXP, headXP);
	}

	public static int getRequiredXp(ItemStack tool) {
		return getRequiredXp(tool, null);
	}

	public static int getRequiredXp(ItemStack tool, boolean pick) {
		return getRequiredXp(tool, null, pick);
	}

	public static int getRequiredXp(ItemStack tool, NBTTagCompound tags) {
		return getRequiredXp(tool, tags, false);
	}

	public static int getRequiredXp(ItemStack tool, NBTTagCompound tags,
			boolean pick) {
		if (tags == null)
			tags = tool.getTagCompound().getCompoundTag("InfiTool");

		float base = 400;

		if (tool.getItem() instanceof Weapon
				|| tool.getItem() instanceof Shortbow) {
			if (tool.getItem() instanceof Scythe)
				base *= LevelsConfig.Scythe;
			else if (tool.getItem() instanceof BattleSign)
				base *= LevelsConfig.BattleSign;
			else if (tool.getItem() instanceof Shortbow)
				base *= LevelsConfig.Shortbow;
			else if (tool.getItem() instanceof Broadsword)
				base *= LevelsConfig.Broadsword;
			else if (tool.getItem() instanceof Cleaver)
				base *= LevelsConfig.Cleaver;
			else if (tool.getItem() instanceof Cutlass)
				base *= LevelsConfig.Cutlass;
			else if (tool.getItem() instanceof Dagger)
				base *= LevelsConfig.Dagger;
			else if (tool.getItem() instanceof FryingPan)
				base *= LevelsConfig.FryingPan;
			else if (tool.getItem() instanceof Longsword)
				base *= LevelsConfig.Longsword;
			else if (tool.getItem() instanceof Rapier)
				base *= LevelsConfig.Rapier;
			base *= LevelsConfig.xpRequiredWeaponsPercentage / 100f;
		} else {
			int miningSpeed = tags.getInteger("MiningSpeed");
			int divider = 1;
			if (tags.hasKey("MiningSpeed2")) {
				miningSpeed += tags.getInteger("MiningSpeed2");
				divider += 1;
			}
			if (tags.hasKey("MiningSpeedHandle")) {
				miningSpeed += tags.getInteger("MiningSpeedHandle");
				divider += 1;
			}
			if (tags.hasKey("MiningSpeedExtra")) {
				miningSpeed += tags.getInteger("MiningSpeedExtra");
				divider += 1;
			}

			base = 100f;
			base += (float) miningSpeed / (float) divider / 2f;

			if (tool.getItem() instanceof Hatchet)
				base *= LevelsConfig.Hatchet;
			else if (tool.getItem() instanceof Shovel)
				base *= LevelsConfig.Shovel;
			else if (tool.getItem() instanceof Mattock)
				base *= LevelsConfig.Mattock;
			else if (tool.getItem() instanceof LumberAxe)
				base *= LevelsConfig.LumberAxe;
			else if (tool.getItem() instanceof Battleaxe)
				base *= LevelsConfig.Battleaxe;
			else if (tool.getItem() instanceof Pickaxe)
				base *= LevelsConfig.Pickaxe;
			else if (tool.getItem() instanceof Hammer)
				base *= LevelsConfig.Hammer;
			else if (tool.getItem() instanceof Excavator)
				base *= LevelsConfig.Excavator;

			base *= LevelsConfig.xpRequiredToolsPercentage / 100f;
		}

		if (pick) {
			int harvestLevel = TConstructRegistry.getMaterial(
					tags.getInteger("Head")).harvestLevel();
			if (harvestLevel >= 1)
				base *= Math.pow(LevelsConfig.xpPerLevelMultiplier,
						harvestLevel - 1);
			base *= ModifierConfig.levelingPickaxeBoostXpPercentage / 100f;
		} else {
			int level = tags.getInteger("ToolLevel");
			if (level >= 1)
				base *= Math.pow(LevelsConfig.xpPerLevelMultiplier, level - 1);
		}

		return Math.round(base);
	}

	public static void LevelUpTool(ItemStack stack, EntityPlayer player) {
		NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");

		World world = player.worldObj;
		int level = tags.getInteger("ToolLevel");
		tags.setInteger("ToolLevel", ++level);

		boolean isTool = stack.getItem() instanceof HarvestTool ? true : false;

		if (isTool)
			isTool = stack.getItem() instanceof Battleaxe ? false : true;

		updateXP(stack, player, 0l, -1l);

		if (!world.isRemote) {
			switch (level) {
			case 2:
				player.addChatMessage(new ChatComponentText("\u00a73"
						+ LevelsConfig.level1finish + stack.getDisplayName()
						+ "\u00a73" + LevelsConfig.level1finisha));
				break;
			case 3:
				player.addChatMessage(new ChatComponentText("\u00a73"
						+ LevelsConfig.level2finish + stack.getDisplayName()
						+ "\u00a73" + LevelsConfig.level2finisha));
				break;
			case 4:
				player.addChatMessage(new ChatComponentText("\u00a73"
						+ LevelsConfig.level3finish + stack.getDisplayName()
						+ "\u00a73" + LevelsConfig.level3finisha));
				break;
			case 5:
				player.addChatMessage(new ChatComponentText("\u00a73"
						+ LevelsConfig.level4finish + stack.getDisplayName()
						+ "\u00a73" + LevelsConfig.level4finisha));
				break;
			case 6:
				player.addChatMessage(new ChatComponentText("\u00a73"
						+ LevelsConfig.level5finish + stack.getDisplayName()
						+ "\u00a73" + LevelsConfig.level5finisha));
				break;
			case 7:
				player.addChatMessage(new ChatComponentText("\u00a73"
						+ LevelsConfig.level6finish + stack.getDisplayName()
						+ "\u00a73" + LevelsConfig.level6finisha));
				break;
			case 8:
				player.addChatMessage(new ChatComponentText("\u00a73"
						+ LevelsConfig.level7finish + stack.getDisplayName()
						+ "\u00a73" + LevelsConfig.level7finisha));
				break;
			case 9:
				player.addChatMessage(new ChatComponentText("\u00a73"
						+ LevelsConfig.level8finish + stack.getDisplayName()
						+ "\u00a73" + LevelsConfig.level8finisha));
				break;
			case 10:
				player.addChatMessage(new ChatComponentText("\u00a73"
						+ LevelsConfig.level9finish + stack.getDisplayName()
						+ "\u00a73" + LevelsConfig.level9finisha));
				break;
			default:
				player.addChatMessage(new ChatComponentText("\u00a73"
						+ LevelsConfig.levelxfinish + stack.getDisplayName()
						+ "\u00a73" + LevelsConfig.levelxfinisha));
				break;
			}

			if (!LevelsConfig.toolLevelingRandomBonuses || level % 2 == 0
					&& LevelsConfig.toolLevelingExtraModifiers)
				player.addChatMessage(new ChatComponentText(
						"\u00a79You notice room for improvement (+1 modifier)."));
		}

		int currentModifiers = tags.getInteger("Modifiers");
		if (!LevelsConfig.toolLevelingRandomBonuses || level % 2 == 0
				&& LevelsConfig.toolLevelingExtraModifiers)
			tags.setInteger("Modifiers", ++currentModifiers);

		if (LevelsConfig.toolLevelingRandomBonuses) {
			tags.setInteger("Modifiers", currentModifiers + 1);
			for (int i = 1; i <= 10; ++i)
				if (tryModify(player, stack, world.rand.nextInt(10), isTool))
					break;
			tags.setInteger("Modifiers", currentModifiers);
		}

	}

	public static void LevelUpTool(ItemStack stack) {
		NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");

		// World world = player.worldObj;
		int level = tags.getInteger("ToolLevel");
		tags.setInteger("ToolLevel", ++level);

		boolean isTool = stack.getItem() instanceof HarvestTool ? true : false;

		updateXP(stack, 0l, -1l);

		int currentModifiers = tags.getInteger("Modifiers");
		if (!LevelsConfig.toolLevelingRandomBonuses || level % 2 == 0
				&& LevelsConfig.toolLevelingExtraModifiers)
			tags.setInteger("Modifiers", ++currentModifiers);

		Random rand = new Random();
		int temp = rand.nextInt(10);

		if (LevelsConfig.toolLevelingRandomBonuses) {
			tags.setInteger("Modifiers", currentModifiers + 1);
			for (int i = 1; i <= 10; ++i)
				if (tryModify(stack, temp, isTool))
					break;
			tags.setInteger("Modifiers", currentModifiers);
		}

	}

	public static void LevelUpPick(ItemStack stack, EntityPlayer player,
			boolean leveled) {
		NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");

		World world = player.worldObj;
		tags.getInteger("ToolLevel");

		updateXP(stack, player, -1L, 0L);

		if (!world.isRemote)
			if (leveled)
				player.addChatMessage(new ChatComponentText("\u00a79Suddenly, a flash of light shines from the tip of the pickaxe (+1 mining level)"));
			else
				player.addChatMessage(new ChatComponentText("\u00a73Suddenly, a flash of light shines from the tip of your "
						+ stack.getDisplayName() + "\u00a73 (+1 mining level)"));

		tags.setBoolean("HarvestLevelModified", true);
		tags.setInteger("HarvestLevel", tags.getInteger("HarvestLevel") + 1);
	}

	private static boolean tryModify(EntityPlayer player, ItemStack stack,
			int rnd, boolean isTool) {
		ToolMod mod = null;
		Item item = stack.getItem();

		ItemStack[] nullItemStack = new ItemStack[] {};
		if (rnd < 1 && LevelsConfig.moss) {
			mod = new ModInteger(nullItemStack, 4, "Moss",
					ModifierConfig.mossRepairSpeed, "\u00a72", "Auto-Repair");
			if (!player.worldObj.isRemote)
				player.addChatMessage(new ChatComponentText("\u00a79It seems to have accumulated a patch of moss (+1 repair)"));
		} else if (rnd < 2
				&& LevelsConfig.luck
				&& (!isTool && !(item instanceof Shortbow) || isTool
						&& (item instanceof Pickaxe || item instanceof Hammer))) {
			mod = new IguanaModLapis(nullItemStack, 10, 100);
			if (((IguanaModLapis) mod).canModify(stack, nullItemStack, true)) {
				if (!player.worldObj.isRemote)
					player.addChatMessage(new ChatComponentText("\u00a79Perhaps holding on to it will bring you luck? (+100 luck)"));
			} else
				return false;
		} else if (rnd < 6 && LevelsConfig.haste
				&& (isTool || item instanceof Shortbow)) {
			mod = new IguanaModRedstone(nullItemStack, 2, 50);
			if (((IguanaModRedstone) mod).canModify(stack, nullItemStack, true)) {
				if (!player.worldObj.isRemote)
					player.addChatMessage(new ChatComponentText("\u00a79You spin it around with a flourish (+1 haste)"));
			} else
				return false;
		} else if (rnd < 3 && LevelsConfig.attack && !isTool
				&& !(item instanceof Shortbow)) {
			mod = new IguanaModAttack("Quartz", nullItemStack, 11, 30);
			if (((IguanaModAttack) mod).canModify(stack, nullItemStack, true)) {
				if (!player.worldObj.isRemote)
					player.addChatMessage(new ChatComponentText("\u00a79You take the time to sharpen the dull edges of the blade (+1 attack)"));
			} else
				return false;
		} else if (rnd < 4 && LevelsConfig.beheading && !isTool
				&& !(item instanceof Shortbow)) {
			mod = new ModInteger(nullItemStack, 13, "Beheading", 1, "\u00a7d",
					"Beheading");
			if (!player.worldObj.isRemote)
				player.addChatMessage(new ChatComponentText("\u00a79You could take someones head off with that! (+1 beheading)"));
		} else if (rnd < 5 && LevelsConfig.fireaspect && !isTool
				&& !(item instanceof Shortbow)) {
			mod = new IguanaModBlaze(nullItemStack, 7, 25);
			if (((IguanaModBlaze) mod).canModify(stack, nullItemStack, true)) {
				if (!player.worldObj.isRemote)
					player.addChatMessage(new ChatComponentText("\u00a79It starts to feels more hot to the touch (+1 fire aspect)"));
			} else
				return false;
		} else if (rnd < 6 && LevelsConfig.lifesteal && !isTool
				&& !(item instanceof Shortbow)) {
			mod = new ModInteger(nullItemStack, 8, "Necrotic", 1, "\u00a78",
					"Life Steal");
			if (!player.worldObj.isRemote)
				player.addChatMessage(new ChatComponentText("\u00a79It shudders with a strange energy (+1 life steal)"));
		} else if (rnd < 7 && LevelsConfig.smite && !isTool
				&& !(item instanceof Shortbow)) {
			mod = new IguanaModSmite(nullItemStack, 14, 36);
			if (!player.worldObj.isRemote)
				player.addChatMessage(new ChatComponentText("\u00a79It begins to radiate a slight glow (+1 smite)"));
		} else if (rnd < 8 && LevelsConfig.baneofarthropods && !isTool
				&& !(item instanceof Shortbow)) {
			mod = new IguanaModAntiSpider(nullItemStack, 15, 4);
			if (!player.worldObj.isRemote)
				player.addChatMessage(new ChatComponentText("\u00a79A strange odor emanates from the weapon (+1 bane of arthropods)"));
		} else if (rnd < 9 && LevelsConfig.knockback && !isTool) {
			mod = new IguanaModPiston(nullItemStack, 3, 10);
			if (((IguanaModPiston) mod).canModify(stack, nullItemStack, true)) {
				if (!player.worldObj.isRemote)
					player.addChatMessage(new ChatComponentText("\u00a79Feeling more confident, you can more easily keep your assailants at bay (+1 knockback)"));
			} else
				return false;
		} else if (rnd < 10) {
			mod = new ModReinforced(nullItemStack, 16, 1);
			if (!player.worldObj.isRemote)
				player.addChatMessage(new ChatComponentText("\u00a79Fixing up the wear and tear should make it last a little longer (+1 reinforced)"));
		}

		if (mod == null)
			return false;

		mod.addMatchingEffect(stack);
		mod.modify(nullItemStack, stack);
		return true;
	}

	private static boolean tryModify(ItemStack stack, int rnd, boolean isTool) {
		ToolMod mod = null;
		Item item = stack.getItem();

		ItemStack[] nullItemStack = new ItemStack[] {};
		if (rnd < 1 && LevelsConfig.moss) {
			mod = new ModInteger(nullItemStack, 4, "Moss",
					ModifierConfig.mossRepairSpeed, "\u00a72", "Auto-Repair");
		} else if (rnd < 2
				&& LevelsConfig.luck
				&& (!isTool && !(item instanceof Shortbow) || isTool
						&& (item instanceof Pickaxe || item instanceof Hammer))) {
			mod = new IguanaModLapis(nullItemStack, 10, 100);
			if (((IguanaModLapis) mod).canModify(stack, nullItemStack, true)) {
			} else
				return false;
		} else if (rnd < 6 && LevelsConfig.haste
				&& (isTool || item instanceof Shortbow)) {
			mod = new IguanaModRedstone(nullItemStack, 2, 50);
			if (((IguanaModRedstone) mod).canModify(stack, nullItemStack, true)) {
			} else
				return false;
		} else if (rnd < 3 && LevelsConfig.attack && !isTool
				&& !(item instanceof Shortbow)) {
			mod = new IguanaModAttack("Quartz", nullItemStack, 11, 30);
			if (((IguanaModAttack) mod).canModify(stack, nullItemStack, true)) {
			} else
				return false;
		} else if (rnd < 4 && LevelsConfig.beheading && !isTool
				&& !(item instanceof Shortbow)) {
			mod = new ModInteger(nullItemStack, 13, "Beheading", 1, "\u00a7d",
					"Beheading");
		} else if (rnd < 5 && LevelsConfig.fireaspect && !isTool
				&& !(item instanceof Shortbow)) {
			mod = new IguanaModBlaze(nullItemStack, 7, 25);
			if (((IguanaModBlaze) mod).canModify(stack, nullItemStack, true)) {
			} else
				return false;
		} else if (rnd < 6 && LevelsConfig.lifesteal && !isTool
				&& !(item instanceof Shortbow)) {
			mod = new ModInteger(nullItemStack, 8, "Necrotic", 1, "\u00a78",
					"Life Steal");
		} else if (rnd < 7 && LevelsConfig.smite && !isTool
				&& !(item instanceof Shortbow)) {
			mod = new IguanaModSmite(nullItemStack, 14, 36);
		} else if (rnd < 8 && LevelsConfig.baneofarthropods && !isTool
				&& !(item instanceof Shortbow)) {
			mod = new IguanaModAntiSpider(nullItemStack, 15, 4);
		} else if (rnd < 9 && LevelsConfig.knockback && !isTool) {
			mod = new IguanaModPiston(nullItemStack, 3, 10);
			if (((IguanaModPiston) mod).canModify(stack, nullItemStack, true)) {
			} else
				return false;
		} else if (rnd < 10) {
			mod = new ModReinforced(nullItemStack, 16, 1);
		}

		if (mod == null)
			return false;

		mod.addMatchingEffect(stack);
		mod.modify(nullItemStack, stack);
		return true;
	}
}
