package com.waracle.cakemgr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.dto.PatchCakeDTO;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.repository.CakeRepo;
import com.waracle.cakemgr.service.CakeServiceImpl;


@ExtendWith(MockitoExtension.class)
@Configuration
public class CakeServiceTests {

	@Mock
	private CakeRepo cakeRepo;
	
	@Spy
	ModelMapper modelMapper = new ModelMapper();
	
	@InjectMocks
	private CakeServiceImpl cakeService;
	
	
	@Test
	public void getAllCakesTest() {
		
		List<Cake> listOfCakes = new ArrayList<>();
		listOfCakes.add(Cake.builder().id((long) 1).title("Lemon cheesecake")
				.description("A cheesecake made of lemon")
				.imageUrl("https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
				.build());
		listOfCakes.add(Cake.builder().id((long) 2).title("victoria sponge")
				.description("sponge with jam")
				.imageUrl("http://www.bbcgoodfood.com/sites/bbcgoodfood.com/files/recipe_images/recipe-image-legacy-id--1001468_10.jpg")
				.build());
		listOfCakes.add(Cake.builder().id((long) 3).title("Carrot cake")
				.description("Bugs bunnys favourite")
				.imageUrl("http://www.villageinn.com/i/pies/profile/carrotcake_main1.jpg")
				.build());
		
		when(cakeRepo.findAll()).thenReturn(listOfCakes);
		
		List<Cake> cakeList = cakeService.getAll();
		
		assertEquals(cakeList.size(), listOfCakes.size());
		verify(cakeRepo, times(1)).findAll();
		
	}
	
	@Test
	public void getCakeByIdTest() {
		
		long cakeId = 1L;
		Cake cake = Cake.builder().description("A cheesecake made of lemon")
		.title("Lemon cheesecake")
		.imageUrl(
				"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
		.build();
		Optional<Cake> cakeOptional = Optional.of(cake);
		
		when(cakeRepo.findById(cakeId)).thenReturn(cakeOptional);
		
		Cake cakeRes = cakeService.getCakeById(cakeId);
		
		assertEquals(cakeRes.getDescription(), cake.getDescription());
		assertEquals(cake, cakeRes);
	}
	
	@Test
	public void patchUpdateCakeByIdTest() {
		
		long cakeId = 1L;
		PatchCakeDTO cakeDto = PatchCakeDTO.builder().id(cakeId).title("update cake title").build();
		Cake cake = Cake.builder().description("A cheesecake made of lemon")
				.title("update cake title")
				.imageUrl(
						"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
				.build();
		this.modelMapper.createTypeMap(PatchCakeDTO.class, Cake.class);
		
		when(cakeRepo.findById(cakeId)).thenReturn(Optional.of(cake));
		when(cakeRepo.save(any(Cake.class))).thenAnswer((invocation) -> invocation.getArgument(0));
		
		Cake cakeRes = cakeService.partialUpdateCake(cakeDto);
		
		assertEquals(cakeDto.getTitle(), cakeRes.getTitle());
		assertNotEquals(cakeDto.getDescription(), cakeRes.getDescription());
		assertNotEquals(cakeDto.getImageUrl(), cakeRes.getImageUrl());
		assertEquals(cake.getDescription(), cakeRes.getDescription());
		assertEquals(cake.getImageUrl(), cakeRes.getImageUrl());
	}
	
	@Test
	public void deleteCakeById() {
		
		long cakeId = 1L;
		
		doNothing().when(cakeRepo).deleteById(cakeId);
		
		cakeService.deleteCakeById(cakeId);
		
		verify(cakeRepo, times(1)).deleteById(cakeId);
	}
	
	@Test
	public void saveCake() {
		CakeDTO createCakeDTO = CakeDTO.builder().description("A cheesecake made of lemon")
				.title("Lemon cheesecake")
				.imageUrl(
						"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
				.build();
		Cake cake = Cake.builder().id((long) 1L).description("A cheesecake made of lemon")
				.title("Lemon cheesecake")
				.imageUrl(
						"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
				.build();
		this.modelMapper.createTypeMap(CakeDTO.class, Cake.class);
		
		
		when(cakeRepo.save(any(Cake.class))).thenReturn(cake);
		
		Cake cakeRes = cakeService.save(createCakeDTO);
		
		assertNotNull(cakeRes.getId());
		assertEquals(createCakeDTO.getDescription(), cakeRes.getDescription());
		assertEquals(createCakeDTO.getImageUrl(), cakeRes.getImageUrl());
		assertEquals(createCakeDTO.getTitle(), cakeRes.getTitle());
		
	}
	
	@Test
	public void updateCake() {
		CakeDTO updateCakeDTO = CakeDTO.builder()
				.id((long)1L)
				.description("A cheesecake with lemon flavour")
				.title("Lemon cheesecake")
				.imageUrl(
						"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
				.build();
		Cake cake = Cake.builder().id((long) 1L).description("A cheesecake made of lemon")
				.title("Lemon cheesecake")
				.imageUrl(
						"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
				.build();
		this.modelMapper.createTypeMap(CakeDTO.class, Cake.class);
		
		when(cakeRepo.findById(updateCakeDTO.getId())).thenReturn(Optional.of(cake));
		when(cakeRepo.save(any(Cake.class))).thenAnswer((invocation) -> invocation.getArgument(0));
		
		Cake cakeRes = cakeService.update(updateCakeDTO);
		
		assertNotNull(cakeRes.getId());
		assertEquals(updateCakeDTO.getDescription(), cakeRes.getDescription());
		assertEquals(cake.getImageUrl(), cakeRes.getImageUrl());
		assertEquals(cake.getTitle(), cakeRes.getTitle());
		
	}
}
