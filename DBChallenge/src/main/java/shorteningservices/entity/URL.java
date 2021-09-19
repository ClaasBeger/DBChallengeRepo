package shorteningservices.entity;

import java.time.LocalDateTime;
import java.util.LinkedList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "URLmapping")
public class URL {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;

	@NotBlank
	private String original;

	private String alias;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties({ "urls" })
	private User owner;

	@OneToOne(cascade = CascadeType.PERSIST)
	private CallStatistics stats;

	public URL() {
	};

	public URL(String original, String alias) {
		this.original = original;
		this.alias = alias;
	}

	public URL(String original, String alias, User user) {
		this.original = original;
		this.alias = alias;
		this.owner = user;
	}

	public int getID() {
		return this.ID;
	}

	public void setID(int newId) {
		this.ID = newId;
	}

	public String getOriginal() {
		return this.original;
	}

	public void setOriginal(String newOriginal) {
		this.original = newOriginal;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String newAlias) {
		this.alias = newAlias;
	}

	public User getOwner() {
		return this.owner;
	}

	public void setUser(User newUser) {
		this.owner = newUser;
	}

	public CallStatistics getStats() {
		return this.stats;
	}

	public void setStats(CallStatistics newStats) {
		this.stats = newStats;
	}

	@Override
	public String toString() {
		if (getOwner() == null) {
			return "URL with the original value of " + getOriginal() + " and an alias value of " + getAlias()
					+ " with the ID " + getID();
		} else {
			return "URL with the original value of " + getOriginal() + " and an alias value of " + getAlias()
					+ " with the ID " + getID() + " and the Owner " + this.owner.signature();
		}
	}

}
