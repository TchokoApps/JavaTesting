package com.tchoko.springboot.apitest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tchoko.springboot.apitest.domain.Item;
import com.tchoko.springboot.apitest.service.ItemService;

@RestController
public class ItemController {

	private static Logger logger = LoggerFactory.getLogger(ItemController.class);

	private ItemService itemService;

	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping("/items")
	public ResponseEntity<List<Item>> getAllItems() {

		List<Item> allItems = itemService.getAllItems();

		return new ResponseEntity<List<Item>>(allItems, HttpStatus.OK);
	}

	@GetMapping("/items/{location}")
	public ResponseEntity<List<Item>> getItemsByLocation(@PathVariable("location") String location) {

		List<Item> allItems = itemService.getItemsByLocation(location);

		return new ResponseEntity<List<Item>>(allItems, HttpStatus.OK);
	}

	@PostMapping("/items")
	public ResponseEntity<Item> createItem(@RequestBody Item item) {

		Item createdItem = itemService.createItem(item);
		
		return new ResponseEntity<Item>(createdItem, HttpStatus.CREATED);
	}

	@PutMapping("/items")
	public ResponseEntity<Item> updateItem(@RequestBody Item item) {

		logger.info("updateItem() called");

		Item updatedItem = itemService.updateItem(item);

		return new ResponseEntity<Item>(updatedItem, HttpStatus.OK);
	}

	@DeleteMapping("/items/{id}")
	public ResponseEntity<Item> updateItem(@PathVariable("id") long id) {

		itemService.deleteItem(id);

		return new ResponseEntity<Item>(HttpStatus.NO_CONTENT);
	}

}
