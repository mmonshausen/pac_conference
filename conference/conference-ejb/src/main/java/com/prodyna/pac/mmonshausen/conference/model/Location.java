package com.prodyna.pac.mmonshausen.conference.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * this class holds information about conferences location
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Entity
@Table(name="location")
public class Location implements Serializable {

	private static final long serialVersionUID = -8697948682589642253L;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@Size(min=1)
	private String name;
	
	@NotNull
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id")
	private Address adress;
	
	@NotNull
	@OneToMany(mappedBy="location")
	private List<Room> rooms;

	public Location(final String name, final Address adress) {
		super();
		this.name = name;
		this.adress = adress;
	}
	
	public Location() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Address getAdress() {
		return adress;
	}

	public void setAdress(final Address adress) {
		this.adress = adress;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(final List<Room> rooms) {
		this.rooms = rooms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adress == null) ? 0 : adress.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rooms == null) ? 0 : rooms.hashCode());
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
		final Location other = (Location) obj;
		if (adress == null) {
			if (other.adress != null)
				return false;
		} else if (!adress.equals(other.adress))
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
		if (rooms == null) {
			if (other.rooms != null)
				return false;
		} else if (!rooms.equals(other.rooms))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", name=" + name + ", adress=" + adress
				+ ", rooms=" + rooms + "]";
	}
}
