package iguanaman.iguanatweakstconstruct;

import iguanaman.iguanatweakstconstruct.blocks.IguanaBlockSkull;
import iguanaman.iguanatweakstconstruct.blocks.IguanaGravelOre;
import iguanaman.iguanatweakstconstruct.blocks.IguanaTileEntitySkull;
import iguanaman.iguanatweakstconstruct.blocks.IguanaToolForgeBlock;
import iguanaman.iguanatweakstconstruct.blocks.IguanaToolStationBlock;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import tconstruct.common.TContent;
import tconstruct.common.TRepo;
import tconstruct.util.config.PHConstruct;
import cpw.mods.fml.common.registry.GameRegistry;

public class IguanaBlocks {

	public static Block gravelNew;
	public static Block newSkullBlock;
	public static Block iguanaToolStationWood;

	public static void init() {

		// TOOL STATION + FORGE
		IguanaLog.log("Modifying GUIs");

		// Block.blocksList[TRepo.toolStationWood] = null;
		TRepo.toolStationWood = new IguanaToolStationBlock(Material.wood)
				.setBlockName("ToolStation");

		// Block.blocksList[TRepo.toolForge] = null;
		TRepo.toolForge = new IguanaToolForgeBlock(Material.iron)
				.setBlockName("ToolForge");

		// Block.blocksList[TRepo.oreGravel] = null;
		TRepo.oreGravel = new IguanaGravelOre().setBlockName("GravelOre")
				.setBlockName("tconstruct.gravelore");

		// SKULLS
		IguanaLog.log("Adding skull blocks");
		// Block.blocksList[Blocks.skull] = null;
		newSkullBlock = new IguanaBlockSkull().setHardness(1.0F)
				.setStepSound(Block.soundTypeStone).setBlockName("skull");// .setTextureName("skull");
		GameRegistry.registerBlock(newSkullBlock, "Skull");
		GameRegistry.registerTileEntity(IguanaTileEntitySkull.class,
				"SkullEntity");

	}

}
