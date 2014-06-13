package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.blocks.IguanaBlockSkull;
import iguanaman.iguanatweakstconstruct.blocks.IguanaGravelOre;
import iguanaman.iguanatweakstconstruct.blocks.IguanaTileEntitySkull;
import iguanaman.iguanatweakstconstruct.blocks.IguanaToolForgeBlock;
import iguanaman.iguanatweakstconstruct.blocks.IguanaToolStationBlock;
import net.minecraft.init.Blocks;
import net.minecraft.block.material.Material;
import tconstruct.common.TContent;
import tconstruct.common.TRepo;
import tconstruct.util.config.PHConstruct;
import cpw.mods.fml.common.registry.GameRegistry;

public class IguanaBlocks {

	public static Block gravelNew;
	public static Block newSkullBlock;
	public static Block iguanaToolStationWood;

	public static void init()
	{

		//TOOL STATION + FORGE
		IguanaLog.log("Modifying GUIs");

		Block.blocksList[TRepo.toolStationWood.blockID] = null;
		TRepo.toolStationWood = new IguanaToolStationBlock(Material.wood).setUnlocalizedName("ToolStation");

		Block.blocksList[TRepo.toolForge.blockID] = null;
		TRepo.toolForge = new IguanaToolForgeBlock( Material.iron).setUnlocalizedName("ToolForge");

		Block.blocksList[TRepo.oreGravel.blockID] = null;
		TRepo.oreGravel = new IguanaGravelOre().setUnlocalizedName("GravelOre").setUnlocalizedName("tconstruct.gravelore");


		//SKULLS
		IguanaLog.log("Adding skull blocks");
		Block.blocksList[Block.skull.blockID] = null;
		newSkullBlock = new IguanaBlockSkull(144).setHardness(1.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("skull").setTextureName("skull");
		GameRegistry.registerBlock(newSkullBlock, "Skull");
		GameRegistry.registerTileEntity(IguanaTileEntitySkull.class, "SkullEntity");


	}

}
