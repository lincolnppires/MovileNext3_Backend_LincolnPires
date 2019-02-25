package com.movile.statemachine;

import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

public final class StateMachineListener extends StateMachineListenerAdapter<States, Events> {
	
	@Override 
	  public void stateChanged(State<States, Events> from, State<States, Events> to) {
	    System.out.println("Listener from: " + from.getId()  + " to " + to.getId()); 
	  }
}
