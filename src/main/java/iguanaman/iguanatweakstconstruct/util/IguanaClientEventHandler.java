package iguanaman.iguanatweakstconstruct.util;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod.EventHandler;

public class IguanaClientEventHandler {


	/* Sounds */
	@EventHandler
	public void onSound (SoundLoadEvent event)
	{
		try
		{
			//event.manager.addSound("iguanatweakstconstruct:chime.ogg");
			FMLLog.warning("IguanaTweaksTConstruct: Loaded chime sound");
		}
		catch (Exception e)
		{
			FMLLog.warning("IguanaTweaksTConstruct: Failed to load chime sound");
		}
	}

}
