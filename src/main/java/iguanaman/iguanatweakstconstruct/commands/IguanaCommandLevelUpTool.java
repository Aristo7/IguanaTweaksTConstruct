package iguanaman.iguanatweakstconstruct.commands;

import iguanaman.iguanatweakstconstruct.IguanaLevelingLogic;
import iguanaman.iguanatweakstconstruct.configs.LevelsConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolCore;

public class IguanaCommandLevelUpTool extends CommandBase {

	@Override
	public String getCommandName() {
		return "leveluptool";
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel()
	{
		return 2;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		EntityPlayerMP entityplayermp = astring.length >= 1 ? getPlayer(icommandsender, astring[0]) : getCommandSenderAsPlayer(icommandsender);
		ItemStack equipped = entityplayermp.getCurrentEquippedItem();
		if (equipped != null && equipped.getItem() instanceof ToolCore)
		{
			NBTTagCompound tags = equipped.getTagCompound().getCompoundTag("InfiTool");
			if (tags.hasKey("ToolLevel"))
			{
				int level = tags.getInteger("ToolLevel");
				int hLevel = tags.hasKey("HarvestLevel") ? hLevel = tags.getInteger("HarvestLevel") : -1;

				if (level >= 1 && level < LevelsConfig.maxlevel || hLevel >= TConstructRegistry.getMaterial("Copper").harvestLevel() || hLevel < 16)
				{
					Long toolXP = tags.hasKey("ToolEXP") ? tags.getLong("ToolEXP") : -1;
					Long headXP = tags.hasKey("HeadEXP") ? tags.getLong("HeadEXP") : -1;
					long requiredToolXP = IguanaLevelingLogic.getRequiredXp(equipped, tags) - toolXP;
					long requiredHeadXP = tags.hasKey("HeadEXP") && hLevel >= TConstructRegistry.getMaterial("Copper").harvestLevel() && hLevel < 16 ? IguanaLevelingLogic.getRequiredXp(equipped, tags) - headXP : -1;

					if (requiredHeadXP < requiredToolXP && requiredHeadXP > 0)
						IguanaLevelingLogic.updateXP(equipped, entityplayermp, toolXP + requiredHeadXP, headXP + requiredHeadXP);
					else
						IguanaLevelingLogic.updateXP(equipped, entityplayermp, toolXP + requiredToolXP, headXP + requiredToolXP);

					if (entityplayermp != icommandsender)
						notifyAdmins(icommandsender, 1, "Leveled up %s's tool", new Object[]{entityplayermp.getDisplayName()});
					else
						notifyAdmins(icommandsender, 1, "Leveled up their own tool", new Object[]{});
				} else
					throw new WrongUsageException("Players tool is already max level", new Object[0]);
			} else
				throw new WrongUsageException("Player must have a levelable Tinker's Construct tool in hand", new Object[0]);
		} else
			throw new WrongUsageException("Player must have a Tinker's Construct tool in hand", new Object[0]);
	}


	/**
	 * Parses an int from the given sring with a specified minimum.
	 */
	public static int parseIntWithMinMax(ICommandSender par0ICommandSender, String par1Str, int min, int max)
	{
		return parseIntBounded(par0ICommandSender, par1Str, min, max);
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return null;
	}

    public int compareTo(ICommand par1ICommand) {

        return this.getCommandName().compareTo(par1ICommand.getCommandName());

    }

    public int compareTo(Object par1Obj) {

        return this.compareTo((ICommand)par1Obj);

    }

}
