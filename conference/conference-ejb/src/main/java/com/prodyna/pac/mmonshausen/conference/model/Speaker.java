package com.prodyna.pac.mmonshausen.conference.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * this class holds information about speakers
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Entity
@Table(name="speaker")
public class Speaker implements Serializable {
	private static final long serialVersionUID = 4998488952671917440L;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@Size(min=1)
	private String name;
	
	@NotNull
	@Size(min=1)
	private String description;
	
	@ManyToMany (mappedBy="speakers")
	private List<Talk> talks;

	public Speaker(final String name, final String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	public Speaker() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
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
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
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
		final Speaker other = (Speaker) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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
		return "Speaker [id=" + id + ", name=" + name + ", description="
				+ description + ", talks=" + talks + "]";
	}
}