package com.example;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    
	@Autowired
	private MockMvc mockMvc;
	@InjectMocks
	private AuthController  authController;
	
	
	String json1 = "{\"email\":\"testing1\",\"username\":\"Hello world\",\"password\":\"testing\"}";

	@Before
	private void setUp() {
		
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
            .build();

//		mockMvc = MockMvcBuilders
//		        .standaloneSetup(new AuthController())
//		        .build();
	}
	
	@Test
	public void testAddUser() throws Exception {
		
         mockMvc.perform(post("/subs").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).content(json1))
             .andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@Test
	public void testAuthenticateUser() throws Exception {
		
         mockMvc.perform(post("/auth").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).content(json1))
             .andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	
	


}