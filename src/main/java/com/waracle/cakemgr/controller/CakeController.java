package com.waracle.cakemgr.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.dto.PatchCakeDTO;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.service.CakeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/cakes")
@CrossOrigin(origins = "*")
public class CakeController {
	
	@Autowired
	CakeService cakeService;
	
	@GetMapping
	@Operation(summary = "Get all cakes", description = "Get list of all the cakes avaialable")
	@SecurityRequirement(name = "Bearer Authentication")
	ResponseEntity<List<Cake>> getAllCakes(){
		return new ResponseEntity<>(cakeService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get Cake details", description = "Get details of a specific cake by id")
	@SecurityRequirement(name = "Bearer Authentication")
	ResponseEntity<Cake> getCakeById(@PathVariable("id") Long id){
		return new ResponseEntity<>(cakeService.getCakeById(id), HttpStatus.OK);
	}
	
	@PostMapping
	@Operation(summary = "Create Cake", description = "Create or register a new cake")
	@SecurityRequirement(name = "Bearer Authentication")
	ResponseEntity<Cake> createCake(@RequestBody @Valid CakeDTO createCake){
		return new ResponseEntity<>(cakeService.save(createCake), HttpStatus.CREATED);
	}
	
	@PatchMapping("/{id}")
	@Operation(summary = "Update Cake details", description = "Update details of a specific cake of by just passing details you want to update")
	@SecurityRequirement(name = "Bearer Authentication")
	ResponseEntity<Cake> partialUpdateCake(@PathVariable Long id, @RequestBody @Valid PatchCakeDTO updateCakeDto){
		updateCakeDto.setId(id);
		return new ResponseEntity<>(cakeService.partialUpdateCake(updateCakeDto), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Update Cake", description = "Create or register a new cake")
	@SecurityRequirement(name = "Bearer Authentication")
	ResponseEntity<Cake> updateCake(@PathVariable Long id, @RequestBody @Valid CakeDTO updateCake){
		updateCake.setId(id);
		return new ResponseEntity<>(cakeService.update(updateCake), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Cake", description = "Delete or remove an existing cake")
	@SecurityRequirement(name = "Bearer Authentication")
	 public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
		cakeService.deleteCakeById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
