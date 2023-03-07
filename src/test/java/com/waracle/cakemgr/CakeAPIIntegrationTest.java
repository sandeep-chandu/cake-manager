package com.waracle.cakemgr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.dto.CakeDTO;
import com.waracle.cakemgr.dto.PatchCakeDTO;

@Configuration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestSecurityConfig.class)
public class CakeAPIIntegrationTest {

	  @LocalServerPort
	  private int port;

	  @Autowired
	  private TestRestTemplate restTemplate;
	  
	  private static HttpHeaders headers;

	  private final ObjectMapper objectMapper = new ObjectMapper();
	  
	  private String createURLWithPort() {
	        return "http://localhost:" + port + "/cakes";
	  }
	  
	  @BeforeAll
	  public static void init() {
	     headers = new HttpHeaders();
	     headers.setContentType(MediaType.APPLICATION_JSON);
	  }
	  
	  @Test
	  public void testGetAllCakes() {
		  HttpEntity<String> entity = new HttpEntity<>(null, headers);
	        ResponseEntity<List<Cake>> response = restTemplate.exchange(
	                createURLWithPort(), HttpMethod.GET, entity, new ParameterizedTypeReference<List<Cake>>(){});
	        List<Cake> cakeList = response.getBody();
	        
	        assert cakeList != null;
	        assertEquals(response.getStatusCode(), HttpStatus.OK);
	  }
	  
	  
	  @Test
	  public void testGetCakeById() throws JsonProcessingException {
		  HttpEntity<String> entity = new HttpEntity<>(null, headers);
		  ResponseEntity<Cake> response = restTemplate.exchange(
	                createURLWithPort()+"/1", HttpMethod.GET, entity, new ParameterizedTypeReference<Cake>(){});
		  
		  Cake cakeRes = response.getBody();
		  
		  assert cakeRes != null;
		  assertEquals(response.getStatusCode(), HttpStatus.OK);
		  String expected = "{\"id\":1,\"title\":\"Lemon cheesecake\",\"description\":\"A cheesecake made of lemon\",\"imageUrl\":\"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg\"}";
		  assertEquals(expected, objectMapper.writeValueAsString(cakeRes));
	  }
	  
	  @Test
	  public void testCreateCake() throws JsonProcessingException {
		  CakeDTO cakeDto = CakeDTO.builder().title("Watermelon cake")
				  .description("A sponge cold cake with watermelon flavour")
				  .imageUrl("http://cakes.com/images/watermelon.jpg")
				  .build();
		  
		  HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(cakeDto), headers);
		  ResponseEntity<Cake> response = restTemplate.exchange(
	                createURLWithPort(), HttpMethod.POST, entity, Cake.class);
		  
		  Cake orderRes = response.getBody();
		  
		  assertEquals(response.getStatusCode(), HttpStatus.CREATED);
	      assert orderRes != null;
	      assert orderRes.getId() != null;
	      assertEquals(orderRes.getTitle(), cakeDto.getTitle());
	      assertEquals(orderRes.getDescription(), cakeDto.getDescription());
	      assertEquals(orderRes.getImageUrl(), cakeDto.getImageUrl());
	  }
	  
	  @Test
	  public void testPartialUpdateCake() throws JsonProcessingException {
		  long id = 5L;
		  RestTemplate restTemp = new RestTemplate();
		  restTemp.setRequestFactory(new HttpComponentsClientHttpRequestFactory()); 
		  PatchCakeDTO patchCakeDTO = PatchCakeDTO.builder().description("A cheesecake with lemon flavour").build();
		  HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(patchCakeDTO), headers);
		  ResponseEntity<Cake> response = restTemp.exchange(
	                createURLWithPort()+"/"+id, HttpMethod.PATCH, entity, Cake.class);
		  
		  Cake orderRes = response.getBody();
		  assert orderRes != null;
	      assert orderRes.getId() != null;
	      assertEquals(orderRes.getDescription(), patchCakeDTO.getDescription());
	      assertNotNull(orderRes.getImageUrl());
	      assertNotNull(orderRes.getTitle());
	  }
	  
	  @Test
	  public void testUpdateCake() throws JsonProcessingException {
		  long id = 4L;
		  CakeDTO cakeDto = CakeDTO.builder().title("Watermelon cake")
				  .description("A sponge cold cake with watermelon flavour")
				  .imageUrl("http://cakes.com/images/watermelon.jpg")
				  .build();
		  HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(cakeDto), headers);
		  ResponseEntity<Cake> response = restTemplate.exchange(
	                createURLWithPort()+"/"+id, HttpMethod.PUT, entity, Cake.class);
		  
		  Cake orderRes = response.getBody();
		  assert orderRes != null;
	      assert orderRes.getId() != null;
	      assertEquals(orderRes.getDescription(), cakeDto.getDescription());
	      assertEquals(orderRes.getTitle(), cakeDto.getTitle());
	      assertEquals(orderRes.getImageUrl(), cakeDto.getImageUrl());
	  }
	  
	  public void testDeleteCake() {
		  long id = 1L;
		  ResponseEntity<String> response = restTemplate.exchange(
	                createURLWithPort()+"/"+id, HttpMethod.DELETE, null, String.class);
		  
		  assertEquals(response.getStatusCode(), HttpStatus.OK);
	  }
}
