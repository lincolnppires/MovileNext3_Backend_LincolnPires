package com.movile.statemachine;

import java.util.EnumSet;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import com.movile.command.Command;
import com.movile.command.Commands;

@Configuration
@EnableStateMachine
public class OrderStateMachine extends EnumStateMachineConfigurerAdapter<States, Events> {

	@Override
	public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
		states.withStates().initial(States.OPEN).end(States.CLOSED).states(EnumSet.allOf(States.class));
	}

	@Override
	public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
		transitions
			.withExternal()
				.source(States.OPEN).target(States.OPEN).event(Events.OPEN)
			.and()
			.withExternal()
				.source(States.OPEN).target(States.CLOSED).event(Events.PAYMENT_RECEIVED)
			.and()
			.withExternal()
				.source(States.OPEN).target(States.CANCELED).event(Events.CANCEL)
			.and()
			.withExternal()
				.source(States.OPEN).target(States.EVALUATION).event(Events.IN_EVALUATION)
			.and()
			.withExternal()
				.source(States.EVALUATION).target(States.CLOSED).event(Events.PAYMENT_RECEIVED)
			.and()
			.withExternal()
				.source(States.EVALUATION).target(States.CANCELED).event(Events.CANCEL)
			.and()
			.withExternal()
				.source(States.CANCELED).target(States.OPEN).event(Events.OPEN);
	}

	@Override
	public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
		config
			.withConfiguration()
			.autoStartup(true)
			.listener(new StateMachineListener());
	}
	
	private static final class StateMachineListener extends StateMachineListenerAdapter<States, Events> { 
		  @Override 
		  public void stateChanged(State<States, Events> from, State<States, Events> to) {
		    System.out.println("Order state changed from "  + to.getId());
		    List<Command> commands = Commands.getCommands();	        
	        commands.forEach(c -> c.execute());
		  }
		  
	}

}
