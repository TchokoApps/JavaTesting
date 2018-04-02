package com.tchoko.springboot.apitest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.tchoko.springboot.apitest.domain.Item;
import com.tchoko.springboot.apitest.service.ItemService;
import com.tchoko.springboot.apitest.utils.TestUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ItemController.class, secure = false)
public class ItemControllerMockMvcTest {

	private static final String DOUALA = "douala";

	private static final String YAOUNDE = "yaounde";

	private static final String LEARN_SPRING_BOOT = "Learn Spring boot";

	private static final String LEARN_JAVA_8 = "Learn Java 8";

	private static final String LOCATION = "location";

	private static final String DESCRIPTION = "description";

	@MockBean
	private ItemService itemService;

	@Autowired
	private MockMvc mockMvc;

	private Item item;

	@Before
	public void setUp() throws Exception {
		item = Item
				.builder()
				.withId(1L)
				.withDescription(DESCRIPTION)
				.withLocation(LOCATION)
				.build();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void getAllItemsShouldReturnItems() throws Exception {
		
		Item item = Item
			.builder()
			.withId(1L)
			.withDescription(LEARN_SPRING_BOOT)
			.withLocation(DOUALA)
			.build();
		
		Item item2 = Item
				.builder()
				.withId(2L)
				.withDescription(LEARN_JAVA_8)
				.withLocation(YAOUNDE)
				.build();
		
		List<Item> items = Arrays.asList(item, item2);
		
		when(itemService.getAllItems()).thenReturn(items);
		
		mockMvc.perform(get("/items")
				.accept(TestUtils.APPLICATION_JSON_UTF8)
				.contentType(TestUtils.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].description", is(LEARN_SPRING_BOOT)))
            .andExpect(jsonPath("$[0].location", is(DOUALA)))
            .andExpect(jsonPath("$[1].id", is(2)))
            .andExpect(jsonPath("$[1].description", is(LEARN_JAVA_8)))
            .andExpect(jsonPath("$[1].location", is(YAOUNDE)));
	}

//	@Test
	public void getItemsByLocationShouldReturnAllItems() throws Exception {
		
		Item item = Item.builder()
				.withId(1L)
				.withLocation(DOUALA)
				.withItemDate(new Date())
				.build();
		
		Item item2 = Item.builder()
				.withId(2L)
				.withLocation(YAOUNDE)
				.withItemDate(new Date())
				.build();
		
		given(itemService.getItemsByLocation(DOUALA)).willReturn(Arrays.asList(item, item2));
		
		mockMvc.perform(get("items/douala").accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk());
	}
	
	@Test
	public void createItemShouldCreateANewItem() throws Exception {
		
		when(itemService.createItem(Mockito.any(Item.class))).thenReturn(item);
		
			doPost(item)
			.andExpect(status().isCreated())
			.andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.description", is(DESCRIPTION)))
            .andExpect(jsonPath("$.location", is(LOCATION)));
		
		verify(itemService, times(1)).createItem(Mockito.any());
		verifyNoMoreInteractions(itemService);
		
	}
	
	private ResultActions doPost(Item item) throws Exception, IOException {
		return mockMvc.perform(post("/items")
				.accept(TestUtils.APPLICATION_JSON_UTF8)
				.contentType(TestUtils.APPLICATION_JSON_UTF8)
				.content(TestUtils.convertObjectToJsonBytes(item)));
	}
}
