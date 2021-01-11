package monkeys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="ISLAND")
public class Island implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private int[][] cells;
	
	public Island() {}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	@Column(name="ID")
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	@Lob
	@Column(length = 100000)
	public int[][] getCells() {
		return cells;
	}
	
	public void setCells(int[][] cells) {
		this.cells = cells;
	}
}