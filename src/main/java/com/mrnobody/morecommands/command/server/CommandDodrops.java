package com.mrnobody.morecommands.command.server;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.mrnobody.morecommands.command.Command;
import com.mrnobody.morecommands.command.ServerCommand;
import com.mrnobody.morecommands.handler.EventHandler;
import com.mrnobody.morecommands.handler.Listeners.Listener;
import com.mrnobody.morecommands.util.GlobalSettings;
import com.mrnobody.morecommands.wrapper.CommandException;
import com.mrnobody.morecommands.wrapper.CommandSender;

@Command(
		name = "dodrops",
		description = "command.dodrops.description",
		example = "command.dodrops.example",
		syntax = "command.dodrops.syntax",
		videoURL = "command.dodrops.videoURL"
		)
public class CommandDodrops extends ServerCommand implements Listener<EntityJoinWorldEvent> {
	public CommandDodrops() {
		EventHandler.ENTITYJOIN.getHandler().register(this);
	}
	
	@Override
	public void onEvent(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityItem && !GlobalSettings.dodrops) event.setCanceled(true);
	}

	@Override
	public String getCommandName() {
		return "dodrops";
	}

	@Override
	public String getUsage() {
		return "command.dodrops.syntax";
	}

	@Override
	public void execute(CommandSender sender, String[] params) throws CommandException {
		GlobalSettings.dodrops = !GlobalSettings.dodrops;
		sender.sendLangfileMessage(GlobalSettings.dodrops ? "command.dodrops.enabled" : "command.dodrops.disabled");
	}
	
	@Override
	public Requirement[] getRequirements() {
		return new Requirement[0];
	}
	
	@Override
	public void unregisterFromHandler() {
		EventHandler.ENTITYJOIN.getHandler().unregister(this);
	}

	@Override
	public ServerType getAllowedServerType() {
		return ServerType.ALL;
	}
	
	@Override
	public int getPermissionLevel() {
		return 2;
	}
	
	@Override
	public boolean canSenderUse(ICommandSender sender) {
		return true;
	}
}