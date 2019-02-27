package com.movile.command;

import java.util.ArrayList;
import java.util.List;

public class Commands {
	public static List<Command> getCommands(){
		List<Command> commands = new ArrayList<Command>();
		
		commands.add(new CommandEmail());
		commands.add(new CommandPublisher());
		commands.add(new CommandQueue());
		
		return commands; 		
	}
}
