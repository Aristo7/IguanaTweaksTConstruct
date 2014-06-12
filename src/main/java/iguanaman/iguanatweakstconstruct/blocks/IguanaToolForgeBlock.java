package iguanaman.iguanatweakstconstruct.blocks;

import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import net.minecraft.block.material.Material;
import tconstruct.blocks.ToolForgeBlock;

public class IguanaToolForgeBlock extends ToolForgeBlock {

	public IguanaToolForgeBlock(Material material) {
		super(material);
	}

	@Override
	public Object getModInstance ()
	{
		return IguanaTweaksTConstruct.instance;
	}

}
