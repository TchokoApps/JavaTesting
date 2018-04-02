package com.tchoko.springboot.apitest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tchoko.springboot.apitest.domain.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {
	List<Item> findByLocation(String location);
}
