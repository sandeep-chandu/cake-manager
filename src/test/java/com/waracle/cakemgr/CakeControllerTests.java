package com.waracle.cakemgr;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.dto.PatchCakeDTO;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.repository.CakeRepo;
import com.waracle.cakemgr.service.CakeService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@WebMvcTest
public class CakeControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CakeService cakeService;

	@MockBean
	private CakeRepo cakeRepo;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void givenCakeObject_whenCreateCake_thenReturnSavedCake() throws Exception {

		Cake cake = Cake.builder().id((long) 1)
				.description("A cheesecake made of lemon")
				.title("Lemon cheesecake")
				.imageUrl(
						"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
				.build();

		given(cakeService.save(any(CakeDTO.class))).willReturn(cake);

		ResultActions response = mockMvc.perform(post("/cakes")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(cake)));

		response.andDo(print()).
	                andExpect(status().isCreated())
	                .andExpect(jsonPath("$.title",
	                        is(cake.getTitle())))
	                .andExpect(jsonPath("$.description",
	                        is(cake.getDescription())))
	                .andExpect(jsonPath("$.imageUrl",
	                        is(cake.getImageUrl())));

	}
	
	@Test
	public void givenListOfCakes_WhenGetAllCakes_thenReturnCakeList() throws Exception {
		
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
		
		given(cakeService.getAll()).willReturn(listOfCakes);
		
		ResultActions response = mockMvc.perform(get("/cakes"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(listOfCakes.size())));
		
	}
	
	@Test
	public void givenCakeId_whenGetCakeById_thenReturnCakeObject() throws Exception{
		
		long cakeId = 1L;
		Cake cake = Cake.builder().description("A cheesecake made of lemon")
				.title("Lemon cheesecake")
				.imageUrl(
						"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
				.build();
		given(cakeService.getCakeById(cakeId)).willReturn(cake);
		
		ResultActions response = mockMvc.perform(get("/cakes/{id}", cakeId));

		response.andExpect(status().isOk()).andDo(print())
			.andExpect(jsonPath("$.title", is(cake.getTitle())))
			.andExpect(jsonPath("$.description", is(cake.getDescription())))
			.andExpect(jsonPath("$.imageUrl", is(cake.getImageUrl())));
	}
	
	@Test
	public void giveCakeDTOAndCakeId_whenPatchCakeById_thenReturnCakeObject() throws Exception{
		
		long cakeId = 1L;
		PatchCakeDTO cakeDto = PatchCakeDTO.builder().title("update cake title").build();
		Cake cake = Cake.builder().description("A cheesecake made of lemon")
				.title("update cake title")
				.imageUrl(
						"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
				.build();
		given(cakeRepo.findById(cakeId)).willReturn(Optional.empty());
		given(cakeService.partialUpdateCake(any(PatchCakeDTO.class))).willReturn(cake);
		
		ResultActions response = mockMvc.perform(patch("/cakes/{id}", cakeId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cakeDto)));
		
		response.andExpect(status().isOk()).andDo(print())
		.andExpect(jsonPath("$.title", is(cake.getTitle())))
		.andExpect(jsonPath("$.description", is(cake.getDescription())))
		.andExpect(jsonPath("$.imageUrl", is(cake.getImageUrl())));
		
	}
	
	@Test
	public void givenCakeObject_whenUpdateCake_thenReturnSavedCake() throws Exception {

		CakeDTO updateCakeDto = CakeDTO.builder().description("A cheesecake made of lemon")
				.title("Lemon cheesecake")
				.imageUrl(
						"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
				.build();
		
		Cake cake = Cake.builder().id((long) 1)
				.description("A cheesecake made of lemon")
				.title("Lemon cheesecake")
				.imageUrl(
						"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg")
				.build();

		given(cakeService.save(any(CakeDTO.class))).willReturn(cake);

		ResultActions response = mockMvc.perform(put("/cakes/{id}", (long)1)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(updateCakeDto)));

		response.andDo(print()).
	                andExpect(status().isOk()).andExpect(jsonPath("$.id", not(null)))
	                .andExpect(jsonPath("$.title",
	                        is(cake.getTitle())))
	                .andExpect(jsonPath("$.description",
	                        is(cake.getDescription())))
	                .andExpect(jsonPath("$.imageUrl",
	                        is(cake.getImageUrl())));

	}
	
	
	@Test
	public void giveCakeId_whenDeleteCakeById_thenReturnNothing() throws Exception{
		
		long cakeId = 1L;
		
		willDoNothing().given(cakeService).deleteCakeById(cakeId);
		
		ResultActions response = mockMvc.perform(delete("/cakes/{id}", cakeId));
		 response.andExpect(status().isOk());
	}
	
}
