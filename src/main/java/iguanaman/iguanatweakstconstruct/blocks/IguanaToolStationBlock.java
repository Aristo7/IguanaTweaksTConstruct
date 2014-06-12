package iguanaman.iguanatweakstconstruct.blocks;

import iguanaman.iguanatweakstconstruct.IguanaTweaksTConstruct;
import net.minecraft.block.material.Material;
import tconstruct.blocks.ToolStationBlock;

public class IguanaToolStationBlock extends ToolStationBlock {

	public IguanaToolStationBlock(Material material) {
		super(material);
	}

	@Override
	public Object getModInstance ()
	{
		return IguanaTweaksTConstruct.instance;
	}

}
