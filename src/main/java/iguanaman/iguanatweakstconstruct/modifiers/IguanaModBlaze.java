package iguanaman.iguanatweakstconstruct.modifiers;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tconstruct.library.tools.ToolCore;
import tconstruct.library.tools.ToolMod;
import tconstruct.modifiers.tools.ModBlaze;

public class IguanaModBlaze extends ToolMod {

    String tooltipName;
	int increase;
    int max;

	public IguanaModBlaze(ItemStack[] items, int effect, int inc) {
		super(items, effect, "Blaze");
        tooltipName = "\u00a76Fiery";
		increase = inc;
		max = 25;
	}

	public boolean canModify (ItemStack tool, ItemStack[] input, boolean auto)
    {
        ToolCore toolItem = (ToolCore) tool.getItem();
        if (!validType(toolItem))
            return false;

		if (auto) return true;

		NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        if (!tags.hasKey(key))
            return tags.getInteger("Modifiers") > 0 && increase <= max;

        int keyPair[] = tags.getIntArray(key);
        if (keyPair[0] + increase <= keyPair[1])
            return true;

        else if (keyPair[0] == keyPair[1])
            return tags.getInteger("Modifiers") > 0;

        else
            return false;
    }

	
	@Override
	protected boolean canModify (ItemStack tool, ItemStack[] input)
	{
		return canModify(tool, input, false);
	}

    @Override
    public void modify (ItemStack[] input, ItemStack tool)
    {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        if (tags.hasKey(key))
        {
            int[] keyPair = tags.getIntArray(key);
            if (keyPair[0] == max)
            {
                keyPair[0] += increase;
                keyPair[1] += max;
                tags.setIntArray(key, keyPair);

                int modifiers = tags.getInteger("Modifiers");
                modifiers -= 1;
                tags.setInteger("Modifiers", modifiers);
            }
            else
            {
                keyPair[0] += increase;
                tags.setIntArray(key, keyPair);
            }
            updateModTag(tool, keyPair);
        }
        else
        {
            int modifiers = tags.getInteger("Modifiers");
            modifiers -= 1;
            tags.setInteger("Modifiers", modifiers);
            String modName = "\u00a76Blaze (" + increase + "/" + max + ")";
            int tooltipIndex = addToolTip(tool, tooltipName, modName);
            int[] keyPair = new int[] { increase, max, tooltipIndex };
            tags.setIntArray(key, keyPair);
        }

        int fiery = tags.getInteger("Fiery");
        fiery += (increase);
        tags.setInteger("Fiery", fiery);

    }

    void updateModTag (ItemStack tool, int[] keys)
    {
        NBTTagCompound tags = tool.getTagCompound().getCompoundTag("InfiTool");
        String tip = "ModifierTip" + keys[2];
        String modName = "\u00a76Blaze (" + keys[0] + "/" + keys[1] + ")";
        tags.setString(tip, modName);
    }

    public boolean validType (ToolCore tool)
    {
        List list = Arrays.asList(tool.toolCategories());
        return list.contains("melee") || list.contains("ammo");
    }

}
