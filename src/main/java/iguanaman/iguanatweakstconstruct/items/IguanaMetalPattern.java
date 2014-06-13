package iguanaman.iguanatweakstconstruct.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IguanaMetalPattern extends IguanaPattern {

	public IguanaMetalPattern(String patternType, String folder)
	{
		super(patternName, getPatternNames(patternType), folder);
	}

	protected static String[] getPatternNames (String partType)
	{
		String[] names = new String[patternName.length];
		for (int i = 0; i < patternName.length; i++)
			if (!patternName[i].equals(""))
				names[i] = partType + patternName[i];
			else
				names[i] = "";
		return names;
	}

	private static final String[] patternName = new String[] { "ingot", "rod", "pickaxe", "shovel", "axe", "swordblade", "largeguard", "mediumguard", "crossbar", "binding", "frypan", "sign",
		"knifeblade", "chisel", "largerod", "toughbinding", "largeplate", "broadaxe", "scythe", "excavator", "largeblade", "hammerhead", "fullguard", "", "", "arrowhead", "gem" };

	@Override
	public void getSubItems (Item b, CreativeTabs tab, List list)
	{
		for (int i = 0; i < patternName.length; i++)
			if (!patternName[i].equals(""))
				list.add(new ItemStack(b, 1, i));
	}

}
