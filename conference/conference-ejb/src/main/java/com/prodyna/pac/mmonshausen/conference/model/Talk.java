package com.prodyna.pac.mmonshausen.conference.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * this class holds information about talks
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@Entity
@Table(name="talk")
public class Talk implements Serializable {
	private static final long serialVersionUID = 5264845036726171532L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min=1)
	private String name;

	@NotNull
	@Size(min=1)
	private String description;

	@NotNull
	private Date startDateTime;

	@NotNull
	private Date endDateTime;

	@Min(value=1)
	private int durationInDays;

	@NotNull
	@ManyToOne
	@JoinColumn(name="conference_id", referencedColumnName="id")
	private Conference conference;

	@NotNull
	@ManyToOne
	@JoinColumn(name="room_id", referencedColumnName="id")
	private Room room;

	@NotNull
	@ManyToMany
	@JoinTable(name="TalkSpeakerMapping",
	joinColumns={@JoinColumn(name="talk_id", referencedColumnName="id")},
	inverseJoinColumns={@JoinColumn(name="speaker_id", referencedColumnName="id")})
	private List<Speaker> speakers;

	public Talk(final String name, final String description, final Date startDateTime,
			final Date endDateTime, final int durationInDays, final Conference conference,
			final Room room, final List<Speaker> speakers) {
		super();
		this.name = name;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.durationInDays = durationInDays;
		this.conference = conference;
		this.room = room;
		this.speakers = speakers;
	}

	public Talk() {
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

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(final Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(final Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public int getDurationInDays() {
		return durationInDays;
	}

	public void setDurationInDays(final int durationInDays) {
		this.durationInDays = durationInDays;
	}

	public Conference getConference() {
		return conference;
	}

	public void setConference(final Conference conference) {
		this.conference = conference;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(final Room room) {
		this.room = room;
	}

	public List<Speaker> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(final List<Speaker> speakers) {
		this.speakers = speakers;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((conference == null) ? 0 : conference.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + durationInDays;
		result = prime * result
				+ ((endDateTime == null) ? 0 : endDateTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result
				+ ((speakers == null) ? 0 : speakers.hashCode());
		result = prime * result
				+ ((startDateTime == null) ? 0 : startDateTime.hashCode());
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
		final Talk other = (Talk) obj;
		if (conference == null) {
			if (other.conference != null)
				return false;
		} else if (!conference.equals(other.conference))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (durationInDays != other.durationInDays)
			return false;
		if (endDateTime == null) {
			if (other.endDateTime != null)
				return false;
		} else if (!endDateTime.equals(other.endDateTime))
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
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (speakers == null) {
			if (other.speakers != null)
				return false;
		} else if (!speakers.equals(other.speakers))
			return false;
		if (startDateTime == null) {
			if (other.startDateTime != null)
				return false;
		} else if (!startDateTime.equals(other.startDateTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Talk [id=" + id + ", name=" + name + ", description="
				+ description + ", startDateTime=" + startDateTime
				+ ", endDateTime=" + endDateTime + ", durationInDays="
				+ durationInDays + ", conference=" + conference + ", room="
				+ room + ", speakers=" + speakers + "]";
	}
}