package com.movile.command;

public class CommandPublisher implements Command {

	@Override
	public void execute() {
		System.out.println("send data to publisher");
	}

}
