package shorteningservices.entity;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "URLmapping")
public class URL {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
	
	@NotBlank
	String original;
	
	@NotBlank
	String alias;
	
	public URL() {};
	
	public URL(String original, String alias) {
		this.original = original;
		this.alias = alias;
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
	
	@Override
	public String toString() {
		return "URL with the original value of "+ getOriginal() + "and an alias value of "+ getAlias()+" with the ID "+ getID();
	}

}
