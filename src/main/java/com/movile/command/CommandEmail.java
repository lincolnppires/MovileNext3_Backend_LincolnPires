package com.movile.command;

public class CommandEmail implements Command {

	@Override
	public void execute() {
		System.out.println("send data to email");
	}

}
