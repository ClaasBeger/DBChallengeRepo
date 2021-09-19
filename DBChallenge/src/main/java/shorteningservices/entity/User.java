package shorteningservices.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@Entity
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;

	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	// @Email
	private String username;
	@NotBlank
	private String password;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties({ "owner" })
	private List<URL> urls;

	protected UserRole userRole = UserRole.ROLE_USER;

	public User() {
	}

	public User(String firstName, String lastName, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.urls = new LinkedList<URL>();
	}

	public int getId() {
		return ID;
	}

	public void setId(int id) {
		this.ID = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getusername() {
		return username;
	}

	public void setusername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<URL> getURLs() {
		return this.urls;
	}

	public void setURLs(List<URL> newURLs) {
		newURLs.forEach(n -> n.setUser(this));
		this.urls = newURLs;
	}

	public void addURL(URL newURL) {
		newURL.setUser(this);
		this.urls.add(newURL);
	}

	public void removeURL(URL remURL) {
		remURL.setUser(null);
		this.urls.remove(remURL);
	}

	@Override
	public String toString() {
		return "User [id=" + ID + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password;
	}

	public String signature() {
		return firstName + " " + lastName + " (ID: " + ID + ")";
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userRole.name());
		return Collections.singletonList(simpleGrantedAuthority);
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return this.getusername();
	}

	@JsonIgnore
	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

}
