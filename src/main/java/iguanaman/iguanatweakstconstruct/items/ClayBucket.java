package iguanaman.iguanatweakstconstruct.items;

import iguanaman.iguanatweakstconstruct.IguanaItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class ClayBucket extends ItemBucket {

	Block Contents = null;
	public ClayBucket(Block par1) {
		super(par1);
		Contents = par1;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		float f = 1.0F;
		boolean flag = Contents == null;
		MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, flag);

		if (movingobjectposition == null)
			return par1ItemStack;
		else
		{
			FillBucketEvent event = new FillBucketEvent(par3EntityPlayer, par1ItemStack, par2World, movingobjectposition);
			if (MinecraftForge.EVENT_BUS.post(event))
				return par1ItemStack;

			if (event.getResult() == cpw.mods.fml.common.eventhandler.Event.Result.ALLOW)
			{
				if (par3EntityPlayer.capabilities.isCreativeMode)
					return par1ItemStack;

				if (--par1ItemStack.stackSize <= 0)
					return event.result;

				if (!par3EntityPlayer.inventory.addItemStackToInventory(event.result))
					par3EntityPlayer.dropItem(event.result.getItem(), 1);

				return par1ItemStack;
			}

			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY)
			{
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;

				if (!par2World.canMineBlock(par3EntityPlayer, i, j, k))
					return par1ItemStack;

				if (flag)
				{
					if (!par3EntityPlayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, par1ItemStack))
						return par1ItemStack;

					if (par2World.getBlock(i, j, k).getMaterial() == Material.water && par2World.getBlockMetadata(i, j, k) == 0)
					{
						par2World.setBlockToAir(i, j, k);

						if (par3EntityPlayer.capabilities.isCreativeMode)
							return par1ItemStack;

						if (--par1ItemStack.stackSize <= 0)
							return new ItemStack(IguanaItems.clayBucketWater);

						if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(IguanaItems.clayBucketWater)))
							par3EntityPlayer.dropItem(IguanaItems.clayBucketWater, 1);

						return par1ItemStack;
					}

					if (par2World.getBlock(i, j, k).getMaterial() == Material.lava && par2World.getBlockMetadata(i, j, k) == 0)
					{
						par2World.setBlockToAir(i, j, k);

						if (par3EntityPlayer.capabilities.isCreativeMode)
							return par1ItemStack;

						if (--par1ItemStack.stackSize <= 0)
							return new ItemStack(IguanaItems.clayBucketLava);

						if (!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(IguanaItems.clayBucketLava)))
							par3EntityPlayer.dropItem(IguanaItems.clayBucketLava, 1);

						return par1ItemStack;
					}
				}
				else
				{
//					if (Contents < 0)
//						return new ItemStack(IguanaItems.clayBucketFired);

					if (movingobjectposition.sideHit == 0)
						--j;

					if (movingobjectposition.sideHit == 1)
						++j;

					if (movingobjectposition.sideHit == 2)
						--k;

					if (movingobjectposition.sideHit == 3)
						++k;

					if (movingobjectposition.sideHit == 4)
						--i;

					if (movingobjectposition.sideHit == 5)
						++i;

					if (!par3EntityPlayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, par1ItemStack))
						return par1ItemStack;

					if (tryPlaceContainedLiquid(par2World, i, j, k) && !par3EntityPlayer.capabilities.isCreativeMode)
						if (Contents == Blocks.lava) {
							--par1ItemStack.stackSize;
							return par1ItemStack;
						} else
							return new ItemStack(IguanaItems.clayBucketFired);
				}
			}
			else if (Contents == null && movingobjectposition.entityHit instanceof EntityCow)
				return new ItemStack(IguanaItems.clayBucketMilk);

			return par1ItemStack;
		}
	}

}
