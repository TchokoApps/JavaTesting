package com.tchoko.springboot.apitest.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.tchoko.springboot.apitest.domain.Item;
import com.tchoko.springboot.apitest.repository.ItemRepository;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

	@Mock
	private ItemRepository itemRepository;

	@InjectMocks
	private ItemService itemService;

	@Spy
	private List<Item> items;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Spy
	private Item item;

	@Before
	public void setUp() throws Exception {
		items = Arrays.asList(new Item(), new Item());
		item = new Item();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getAllItemsShouldReturnAListOfItems() {

		List<Item> allItems = itemService.getAllItems();
		verify(itemRepository).findAll();
		verify(itemRepository, times(1)).findAll();
		assertThat(allItems.size(), is(equalTo(0)));
	}

	@Test
	public void getItemsByLocationsShouldReturnZeroItem() {

		when(itemRepository.findByLocation(anyString())).thenReturn(null);
		List<Item> itemsByLocation = itemService.getItemsByLocation(anyString());
		assertThat(itemsByLocation.size(), IsEqual.equalTo(0));
	}

	@Test
	public void getItemByLocationShouldReturnItems() {

		when(itemRepository.findByLocation(anyString())).thenReturn(items);
		List<Item> itemsByLocation = itemService.getItemsByLocation(anyString());
		verify(itemRepository, times(1)).findByLocation(anyString());
		assertThat(itemsByLocation, is(equalTo(items)));
	}

	@Test
	public void createItemShouldThrowIllegalArgumentException() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("id in Item must be null");

		item.setId(100L);
		itemService.createItem(item);
		verify(item, never()).setItemDate(Mockito.any(Date.class));
		verify(itemRepository, never()).save(item);
	}

	@Test
	public void deleteItemShouldNotDeleteAnyItem() {
		itemService.deleteItem(100L);
		verify(itemRepository, never()).deleteById(100L);
	}
	
	@Test
	public void deleteItemShouldDeleteOneItem() {
		Whitebox.setInternalState(itemService, "testFlag", true);
		itemService.deleteItem(100L);
		verify(itemRepository).deleteById(100L);
	}

}
