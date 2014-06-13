package iguanaman.iguanatweakstconstruct.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import tconstruct.common.TRepo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class IguanaPatternCraftingHandler { // implements ICraftingHandler {

	@SubscribeEvent
	public void onCrafting (ItemCraftedEvent event) {
	//public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) {

		if (new ItemStack(event.crafting.getItem()).isItemEqual(new ItemStack(TRepo.woodPattern)))
			for (int i = 0; i < event.craftMatrix.getSizeInventory(); i++)
			{
				ItemStack inSlot = event.craftMatrix.getStackInSlot(i);
				if (inSlot != null && inSlot.isItemEqual(new ItemStack(TRepo.woodPattern)))
					if (inSlot.stackSize > 1) --inSlot.stackSize;
					else event.craftMatrix.setInventorySlotContents(i, null);
			}

	}
}
