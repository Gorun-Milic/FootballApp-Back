package com.gorun.demo.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorun.demo.dao.ClubRepo;
import com.gorun.demo.dao.NationRepo;
import com.gorun.demo.dao.PlayerRepo;
import com.gorun.demo.dto.BuyPlayerDto;
import com.gorun.demo.dto.PlayerDTO;
import com.gorun.demo.dto.PlayerclubDTO;
import com.gorun.demo.dto.SearchplayerDTO;
import com.gorun.demo.dto.StatisticData;
import com.gorun.demo.model.Club;
import com.gorun.demo.model.Player;

@Controller
public class PlayerController {
	
	@Autowired
	PlayerRepo repo;
	
	@Autowired
	ClubRepo repoClub;
	
	@Autowired
	PlayerClubController pcc;
	
	@Autowired
	PlayerNationController pnc;
	
	@Autowired
	NationRepo repoNation;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping("/players")
	@ResponseBody
	public List<Player> getPlayer() {
		return repo.findAll();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping("/player/{playerId}")
	@ResponseBody
	public Optional<Player> getPlayer(@PathVariable("playerId") int playerId) {
		return repo.findById(playerId);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value = "/addplayer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public Player addPlayer(@RequestParam(value = "myFile",required = false) MultipartFile file, @RequestParam(value = "player",required = false) String player) {
		ObjectMapper om=new ObjectMapper();
	    PlayerDTO pdto =null;
	        try {
	            pdto=om.readValue(player,PlayerDTO.class);   //string st -> MyInput input
	            
	            Optional<Club> clubOptional = repoClub.findById(pdto.getClubid());
	    		Club club;
	    		if (!clubOptional.isEmpty()) {
	    			club = clubOptional.get();
	    		}else {
	    			club = null;
	    		}
	    		Player p = new Player();
	    		String pattern = "MM/dd/yyyy";
	    		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	    		try {
	    			System.out.println(pdto.getDateofbirth());
	    			p.setDateofbirth(simpleDateFormat.parse(pdto.getDateofbirth()));
	    		} catch (ParseException e) {
	    			p.setDateofbirth(null);
	    			e.printStackTrace();
	    		}
	    		try {
	    			if (file.getBytes()!=null) {
	    				try {
	    					p.setImg(file.getBytes());
	    				} catch (IOException e) {
	    					e.printStackTrace();
	    				}
	    			}
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	    		p.setName(pdto.getName());
	    		p.setSurname(pdto.getSurname());
//	    		p.setAge(pdto.getAge());
	    		p.setValue(pdto.getValue());
	    		p.setAssists(pdto.getAssists());
	    		p.setFoot(pdto.getFoot());
	    		p.setGoals(pdto.getGoals());
	    		p.setHeight(pdto.getHeight());
	    		p.setImgurl(pdto.getImgUrl());
	    		p.setPosition(pdto.getPosition());
	    		p.setClub(club);
	    		repo.save(p);
	    		return p;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
		
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/player/{playerId}")
	@ResponseBody
	public String deletePlayer(@PathVariable("playerId") int playerId) {
		pcc.deleteRememberedPlayer(playerId);
		pnc.deletePlayerWithNation(playerId);
		System.out.println("BRRRRRRRRRRRIIIIIIIIIIISSSSSSSSSEEEEEEEEEE");
		Optional<Player> p = repo.findById(playerId);
		if (p.isPresent()) {
			System.out.println("IIIIIIIMMMMMMMMMMAAAAA");
			System.out.println(p);
			repo.delete(p.get());
			System.out.println("Deleted!");
			return "Deleted";
		}else {
			System.out.println("Not Deleted!");
			return "Not Deleted!";
		}	
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/player")
	@ResponseBody
	public Player saveOrUpdatePlayer(@RequestBody PlayerDTO player) {
		System.out.println("UUUPPPDDDDAAAAAAAATTTTTTTTTTEEEE");
		System.out.println(player.getName());
		System.out.println(player.getDateofbirth());
		System.out.println(player.getClubid());
		
		Optional<Player> playerOptional = repo.findById(player.getPlayerid());
		Player p;
		if (!playerOptional.isEmpty()) {
			p = playerOptional.get();
		}else {
			p = new Player();
		}
		Optional<Club> clubOptional = repoClub.findById(player.getClubid());
		Club club;
		if (!clubOptional.isEmpty()) {
			club = clubOptional.get();
		}else {
			club = null;
		}
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try {
			p.setDateofbirth(simpleDateFormat.parse(player.getDateofbirth()));
		} catch (ParseException e) {
			p.setDateofbirth(null);
			e.printStackTrace();
		}
		p.setName(player.getName());
		p.setSurname(player.getSurname());
//		p.setAge(player.getAge());
		p.setAssists(player.getAssists());
		p.setFoot(player.getFoot());
		p.setGoals(player.getGoals());
		p.setHeight(player.getHeight());
		p.setImgurl(player.getImgUrl());
		p.setPosition(player.getPosition());
		p.setClub(club);
		repo.save(p);
		return p;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping("/playersByClub/{clubId}")
	@ResponseBody
	public List<Player> getPlayerByClub(@PathVariable("clubId") int clubId) {
		return repo.findByClubid(clubId);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping("/bestGoalscorers/{clubId}")
	@ResponseBody
	public StatisticData getBestGoalscorers(@PathVariable("clubId") int clubId) {
		int limit = 4;
		StatisticData sd = new StatisticData();
		String[] names;
		int[] goals;
		int[] assists;
		
		List<Player> players = repo.bestGoalscorers(clubId);
		
		if (players.size()>4) {
			limit = 4;
			names = new String[limit];
			goals = new int[limit];
			assists = new int[limit];
			for(int i=0; i<4; i++) {
				names[i] = players.get(i).getName();
				goals[i] = players.get(i).getGoals();
				assists[i] = players.get(i).getAssists();
			}
		}else {
			names = new String[players.size()];
			goals = new int[players.size()];
			assists = new int[players.size()];
			for(int i=0; i<players.size(); i++) {
				names[i] = players.get(i).getName();
				goals[i] = players.get(i).getGoals();
				assists[i] = players.get(i).getAssists();
			}
		}
		
		
		sd.setNames(names);
		sd.setGoals(goals);
		sd.setAssists(assists);
		
		return sd;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/searchplayers")
	@ResponseBody
	public List<Player> searchPlayers(@RequestBody SearchplayerDTO searchPlayer) {
		System.out.println(searchPlayer);
		return repo.searchPlayers(searchPlayer.getName(), searchPlayer.getSurname(), searchPlayer.getPosition(), searchPlayer.getFoot(), searchPlayer.getHeight()-8, searchPlayer.getHeight()+8);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/buyPlayer")
	@ResponseBody
	public boolean buyPlayer(@RequestBody BuyPlayerDto buyPlayerObject) {
		Optional<Player> playerOptional = repo.findById(buyPlayerObject.getPlayerId());
		Player player;
		Club otherClub;
		if (!playerOptional.isEmpty()) {
			player = playerOptional.get();
			otherClub = player.getClub();
		}else {
			System.out.println("Nema igraca!");
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "entity not found");
		}
		
		Optional<Club> ourClubOptional = repoClub.findById(buyPlayerObject.getOurClubId());
		Club ourClub;
		if (!ourClubOptional.isEmpty()) {
			ourClub = ourClubOptional.get();
		}else {
			System.out.println("Nema naseg kluba!");
			System.out.println("Nema igraca!");
			throw new ResponseStatusException(
					  HttpStatus.NOT_FOUND, "entity not found");
		}
		
		
		
		if (ourClub.getValue()>=player.getValue()) {
			System.out.println("IGRAC JE PROMENIO KLUB U " + ourClub.getName());
			ourClub.setValue(ourClub.getValue() - player.getValue());
			otherClub.setValue(otherClub.getValue() + player.getValue());
			player.setClub(ourClub);
			repo.save(player);
			return true;
		}
		
		System.out.println("Igrac je skup!");
		
		System.out.println("Nema igraca!");
		throw new ResponseStatusException(
				  HttpStatus.BAD_REQUEST, "you dont have enought money");
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/isPlayingForClub")
	@ResponseBody
	public boolean buyPlayer(@RequestBody PlayerclubDTO playerClub) {
		Optional<Player> playerOptional = repo.findById(playerClub.getPlayerid());
		Player player;
		if (!playerOptional.isEmpty()) {
			player = playerOptional.get();
			if (player.getClub().getClubid()==playerClub.getClubid()) {
				return true;
			}
		}
		return false;
	}

}
