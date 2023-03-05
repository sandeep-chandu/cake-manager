package com.waracle.cakemgr.service;

import java.util.List;

import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.dto.PatchCakeDTO;
import com.waracle.cakemgr.dto.CakeDTO;

public interface CakeService {
	
	Cake save(CakeDTO createCakeDto);
	
	Cake update(CakeDTO cake);
	
	List<Cake> getAll();
	
	Cake getCakeById(Long id);
	
	Cake partialUpdateCake(PatchCakeDTO cakeDto);
	
	void deleteCakeById(Long id);

}
