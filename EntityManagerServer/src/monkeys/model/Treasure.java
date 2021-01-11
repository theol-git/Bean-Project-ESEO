package monkeys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Treasure implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Island map;
	public int x;
	public int y;
	public boolean isVisible;

	public Treasure(Island map, int x, int y, boolean isVisible) {
		this.setMap(map);
		this.x = x;
		this.y = y;
		this.isVisible = isVisible;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	@Column(name="ID")
	public int getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	@OneToOne
	@JoinColumn(name="MAP")
	public Island getMap() {
		return this.map;
	}
	
	public void setMap(Island map) {
		this.map = map;
	}
	
	@Column(name="POSX")
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	@Column(name="POSY")
	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Column(name="VISIBLE")
	public boolean isVisible() {
		return this.isVisible;
	}
	
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}