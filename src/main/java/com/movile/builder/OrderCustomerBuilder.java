package com.movile.builder;

import java.math.BigDecimal;

import com.movile.entity.OrderCustomer;
import com.movile.entity.StatusOrder;

public class OrderCustomerBuilder {

	private OrderCustomerBuilder() {
	}

	public static DescriptionStep newBuilder() {
		return new Steps();
	}

	public static interface DescriptionStep {
		ValueStep description(String description);
	}

	public static interface ValueStep {
		StatusStep value(BigDecimal value);
	}

	public static interface StatusStep {
		BuildStep status(StatusOrder status);
	}

	public static interface BuildStep {
		public OrderCustomer build();
	}

	private static class Steps implements DescriptionStep, ValueStep, StatusStep, BuildStep {

		private String description;
		private BigDecimal value;
		private StatusOrder status;
		private String customer;

		@Override
		public ValueStep description(String description) {
			this.description = description;
			return this;
		}

		@Override
		public StatusStep value(BigDecimal value) {
			this.value = value;
			return this;
		}

		@Override
		public BuildStep status(StatusOrder status) {
			this.status = status;
			return this;
		}

		@Override
		public OrderCustomer build() {
			OrderCustomer orderCustomer = new OrderCustomer();
			orderCustomer.setDescription(description);
			orderCustomer.setValue(value);
			orderCustomer.setStatus(status);
			return orderCustomer;
		}
	}

}
