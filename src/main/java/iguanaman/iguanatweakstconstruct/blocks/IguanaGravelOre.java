package iguanaman.iguanatweakstconstruct.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import tconstruct.blocks.GravelOre;

public class IguanaGravelOre extends GravelOre {

	public IguanaGravelOre() {
		super();
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (!par1World.isRemote)
			if ( func_149831_e(par1World, par2, par3 - 1, par4) && par3 >= 0)
			{
				byte b0 = 32;

				if (!fallInstantly && par1World.checkChunksExist(par2 - b0, par3 - b0, par4 - b0, par2 + b0, par3 + b0, par4 + b0))
				{
					if (!par1World.isRemote)
					{
						EntityFallingBlock entityfallingsand = new EntityFallingBlock(par1World, par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, this, par1World.getBlockMetadata(par2, par3, par4));
						entityfallingsand.field_145813_c = false;
						func_149829_a(entityfallingsand);
						par1World.spawnEntityInWorld(entityfallingsand);
					}
				}
				else
				{
					par1World.setBlockToAir(par2, par3, par4);

					while (func_149831_e(par1World, par2, par3 - 1, par4) && par3 > 0)
						--par3;

					if (par3 > 0)
						par1World.setBlock(par2, par3, par4, this);
				}
			}
	}


}
