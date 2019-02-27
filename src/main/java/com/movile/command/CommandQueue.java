package com.movile.command;

public class CommandQueue implements Command{

	@Override
	public void execute() {
		System.out.println("send data to queue");
	}

}
