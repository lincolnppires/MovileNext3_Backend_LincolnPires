package com.movile.statemachine;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachine
public class OrderStateMachine extends EnumStateMachineConfigurerAdapter<States, Events> {

	@Override
	public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
		states.withStates().initial(States.OPEN).end(States.CLOSED).states(EnumSet.allOf(States.class));
	}

	@Override
	public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
		transitions.withExternal()
				.source(States.OPEN).target(States.CLOSED).event(Events.payment_received)
			.and()
			.withExternal()
				.source(States.OPEN).target(States.CANCELED).event(Events.cancel)
			.and()
			.withExternal()
				.source(States.OPEN).target(States.EVALUATION).event(Events.in_evaluation)
			.and()
			.withExternal()
				.source(States.EVALUATION).target(States.CLOSED).event(Events.payment_received)
			.and()
			.withExternal()
				.source(States.EVALUATION).target(States.CANCELED).event(Events.cancel)
			.and()
			.withExternal()
				.source(States.CANCELED).target(States.OPEN).event(Events.open);
	}

	@Override
	public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
		config
			.withConfiguration()
			.autoStartup(true)
			.listener(new StateMachineListener());
	}

}
