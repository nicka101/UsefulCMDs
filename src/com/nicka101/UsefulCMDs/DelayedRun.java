package com.nicka101.UsefulCMDs;

import org.bukkit.command.CommandSender;

public class DelayedRun implements Runnable{
	
	private String commandToSend;
	private UsefulCMDs plugin;
	private CommandSender sender;
	
	public DelayedRun(UsefulCMDs p, String command, CommandSender send){
		commandToSend = command;
		plugin = p;
		sender = send;
	}
	
	public void run(){
		plugin.getServer().dispatchCommand(sender, commandToSend);
	}
}
