package com.tchoko.springboot.apitest.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Item {

	@Id
	@GeneratedValue
	private Long id;
	private String description;
	private String location;
	private Date itemDate;

	private Item(Builder builder) {
		this.id = builder.id;
		this.description = builder.description;
		this.location = builder.location;
		this.itemDate = builder.itemDate;
	}

	public Item() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getItemDate() {
		return itemDate;
	}

	public void setItemDate(Date itemDate) {
		this.itemDate = itemDate;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", description=" + description + ", location=" + location + ", itemDate=" + itemDate + "]";
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private Long id;
		private String description;
		private String location;
		private Date itemDate;

		private Builder() {
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}

		public Builder withLocation(String location) {
			this.location = location;
			return this;
		}

		public Builder withItemDate(Date itemDate) {
			this.itemDate = itemDate;
			return this;
		}

		public Item build() {
			return new Item(this);
		}
	}

}
