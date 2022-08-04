package com.example.springdatajpa.service;

import com.example.springdatajpa.domain.item.Item;
import com.example.springdatajpa.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findALl();
    }

    public Item findItem(Long itemId) {
        return itemRepository.find(itemId);
    }
    
}
