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
		ItemStack result = super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
		
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
