package com.prodyna.pac.mmonshausen.conference.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * this class holds information about rooms
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Entity
@Table(name="room")
public class Room implements Serializable {
	private static final long serialVersionUID = 2802045871467759931L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min=1)
	private String name;

	@Min(value=1)
	private int capacity;

	@NotNull
	@OneToMany(mappedBy="room")
	private List<Talk> talks;

	public Room(final String name, final int capacity, final List<Talk> talks) {
		super();
		this.name = name;
		this.capacity = capacity;
		this.talks = talks;
	}

	public Room() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(final int capacity) {
		this.capacity = capacity;
	}

	public List<Talk> getTalks() {
		return talks;
	}

	public void setTalks(final List<Talk> talks) {
		this.talks = talks;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacity;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((talks == null) ? 0 : talks.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Room other = (Room) obj;
		if (capacity != other.capacity)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (talks == null) {
			if (other.talks != null)
				return false;
		} else if (!talks.equals(other.talks))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", name=" + name + ", capacity=" + capacity
				+ ", talks=" + talks + "]";
	}
}