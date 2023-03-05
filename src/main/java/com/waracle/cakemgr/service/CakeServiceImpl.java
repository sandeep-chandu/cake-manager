package com.waracle.cakemgr.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.dto.PatchCakeDTO;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.repository.CakeRepo;

@Service
public class CakeServiceImpl implements CakeService {

	@Autowired
	CakeRepo cakeRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public Cake save(CakeDTO createCakeDto) {
		TypeMap<CakeDTO, Cake> propertyMapper = modelMapper.getTypeMap(CakeDTO.class, Cake.class);
		Cake cake= new Cake();
		propertyMapper.map(createCakeDto, cake);
		return cakeRepo.save(cake);
	}

	@Override
	public List<Cake> getAll() {
		return cakeRepo.findAll();
	}

	@Override
	public Cake getCakeById(Long id) {
		Optional<Cake> cakeOptional = cakeRepo.findById(id);
		if(cakeOptional.isEmpty()) {
			throw new EntityNotFoundException();
		}else {
			return cakeOptional.get();
		}
	}

	@Override
	public Cake partialUpdateCake(PatchCakeDTO cakeDto) {
		// TODO Auto-generated method stub
		Optional<Cake> cakeOptional = cakeRepo.findById(cakeDto.getId());
		if(cakeOptional.isEmpty()) {
			throw new EntityNotFoundException();
		}else {
			Cake cake = cakeOptional.get();
			TypeMap<PatchCakeDTO, Cake> propertyMapper = modelMapper.getTypeMap(PatchCakeDTO.class, Cake.class);
		    propertyMapper.addMappings(mapper -> mapper.when(Conditions.isNull()).skip(PatchCakeDTO::getImageUrl, Cake::setImageUrl));
		    propertyMapper.addMappings(mapper -> mapper.when(Conditions.isNull()).skip(PatchCakeDTO::getDescription, Cake::setDescription));
		    propertyMapper.addMappings(mapper -> mapper.when(Conditions.isNull()).skip(PatchCakeDTO::getTitle, Cake::setTitle));
		    
		    propertyMapper.map(cakeDto, cake);
		    return cakeRepo.save(cake);
		}
	}

	@Override
	public void deleteCakeById(Long id) {
		cakeRepo.deleteById(id);
	}

	@Override
	public Cake update(CakeDTO cakeDto) {
		Optional<Cake> cakeOptional = cakeRepo.findById(cakeDto.getId());
		if(cakeOptional.isEmpty()) {
			throw new EntityNotFoundException();
		}else {
			TypeMap<CakeDTO, Cake> propertyMapper = modelMapper.getTypeMap(CakeDTO.class, Cake.class);
			Cake cake= new Cake();
			propertyMapper.map(cakeDto, cake);
			return cakeRepo.save(cake);
		}
	}

}
