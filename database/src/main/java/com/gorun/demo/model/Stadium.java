package com.gorun.demo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the stadium database table.
 * 
 */
@Entity
@NamedQuery(name="Stadium.findAll", query="SELECT s FROM Stadium s")
public class Stadium implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int stadiumid;

	private int capacity;

	@JsonIgnore
	@Lob
	private byte[] img;

	private String imgurl;

	private String name;

	//ovde stavljam nove anotacije
	@JsonIgnore
	@OneToOne(mappedBy = "stadium")
    private Club club;

	public Stadium() {
	}

	public int getStadiumid() {
		return this.stadiumid;
	}

	public void setStadiumid(int stadiumid) {
		this.stadiumid = stadiumid;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public byte[] getImg() {
		return this.img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getImgurl() {
		return this.imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}
	

}