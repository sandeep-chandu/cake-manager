package com.waracle.cakemgr;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.dto.PatchCakeDTO;
import com.waracle.cakemgr.dto.CakeDTO;

@SpringBootApplication
public class CakeManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CakeManagerApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.createTypeMap(CakeDTO.class, Cake.class);
		modelMapper.createTypeMap(PatchCakeDTO.class, Cake.class);
	    return modelMapper;
	}
}
