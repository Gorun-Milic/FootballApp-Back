package com.gorun.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gorun.demo.dto.ClubDTO;
import com.gorun.demo.model.Club;

public interface ClubRepo extends JpaRepository<Club, Integer> {
	
	public List<Club> findByName(String name);
	
	public List<Club> findByPassword(String password);
	
	@Query("from Club where name=?1 and password=?2")
	public List<Club> login(String name, String password);
	
	@Query("from Club where name like %?1% or (value between ?2 and ?3) or league.leagueid=?4")
	public List<Club> searchClubs(String name, int downRange, int upRange, int leagueid);
}
