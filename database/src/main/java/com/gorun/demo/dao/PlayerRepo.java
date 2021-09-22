package com.gorun.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gorun.demo.model.Player;

public interface PlayerRepo extends JpaRepository<Player, Integer> {
	
	@Query("from Player where club.clubid=?1")
	List<Player> findByClubid(int clubid);
	
	@Query("from Player where club.clubid=?1 ORDER BY goals desc")
	List<Player> bestGoalscorers(int clubid);
	
	@Query("from Player where name like %?1% or surname like %?2% or position=?3 or foot=?4 or (height between ?5 and ?6)")
	List<Player> searchPlayers(String name, String surname, String position, String foot, int heightDownRange, int heightUpRange);
}
