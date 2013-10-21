package com.prodyna.pac.mmonshausen.conference.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Entity
@Table(name="conference")
public class Conference implements Serializable {
	private static final long serialVersionUID = 19599280434654539L;

	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@Size(min=1)
	private String name;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="location_id", referencedColumnName="id")
	private Location location;
	
	@NotNull
	@Size(min=1)
	private String description;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@OneToMany(mappedBy="conference")
	private List<Talk> talks;

	public Conference(final String name, final Location location, final String description,
			final Date startDate, final Date endDate) {
		super();
		this.name = name;
		this.location = location;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Conference() {
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
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
		final Conference other = (Conference) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Conference [id=" + id + ", name=" + name + ", location="
				+ location + ", description=" + description + ", startDate="
				+ startDate + ", endDate=" + endDate + "]";
	}
}