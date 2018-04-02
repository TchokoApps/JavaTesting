package com.tchoko.springboot.apitest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.tchoko.springboot.apitest.domain.Item;
import com.tchoko.springboot.apitest.repository.ItemRepository;

@Service
public class ItemService {

	private static Logger logger = LoggerFactory.getLogger(ItemService.class);

	private ItemRepository itemRepository;

	private List<Item> items = new ArrayList<>();

	private boolean testFlag;

	@Autowired
	public ItemService(ItemRepository itemRepository) {

		Assert.notNull(itemRepository, "itemRepository must not be null");

		this.itemRepository = itemRepository;
	}

	public List<Item> getAllItems() {

		items.clear();

		itemRepository.findAll().forEach(items::add);

		return items;
	}

	public List<Item> getAllItemsWithPrefixedLocation(String prefix) {

		getAllItems().forEach(item -> item.setLocation(prefix + item.getLocation()));

		return getAllItems();
	}

	public List<Item> getItemsByLocation(String locationName) {

		List<Item> findByLocation = itemRepository.findByLocation(locationName);

		if (findByLocation != null)
			return findByLocation;
		else
			return new ArrayList<>();
	}

	public Item createItem(Item item) throws IllegalArgumentException {

		if (item.getId() != null)
			throw new IllegalArgumentException("id in Item must be null");

		item.setItemDate(new Date());

		return itemRepository.save(item);
	}

	public Item updateItem(Item item) {

		if (!itemRepository.findById(item.getId()).isPresent())
			return null;

		item.setItemDate(new Date());

		return itemRepository.save(item);
	}

	public void deleteItem(long id) {

		if (testFlag) {
			itemRepository.deleteById(id);
		}
	}

}
