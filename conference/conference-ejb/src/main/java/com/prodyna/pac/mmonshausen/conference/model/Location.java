package com.prodyna.pac.mmonshausen.conference.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * entity holding information about locations
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
	@Size(min=1)
	private String street;
	
	@NotNull
	private String zipCode;
	
	@NotNull
	@Size(min=1)
	private String city;
	
	@NotNull
	@Size(min=1)
	private String country;
	
	@OneToMany(mappedBy="location")
	private List<Room> rooms;
	
	@OneToMany(mappedBy="location")
	private List<Conference> conferences;
	
	public Location(String name, String street, String zipCode, String city,
			String country) {
		super();
		this.name = name;
		this.street = street;
		this.zipCode = zipCode;
		this.city = city;
		this.country = country;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public List<Room> getRooms() {
		return rooms;
	}

	public List<Conference> getConferences() {
		return conferences;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
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
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", name=" + name + ", street=" + street
				+ ", zipCode=" + zipCode + ", city=" + city + ", country="
				+ country + "]";
	}
}