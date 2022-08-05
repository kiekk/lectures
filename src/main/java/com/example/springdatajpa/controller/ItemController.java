package com.example.springdatajpa.controller;

import com.example.springdatajpa.domain.item.Book;
import com.example.springdatajpa.domain.item.Item;
import com.example.springdatajpa.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("items")
public class ItemController {

    private final ItemService itemService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public String itemList(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("new")
    public String createForm(Model model) {
        model.addAttribute("bookForm", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("new")
    public String create(@Valid BookForm bookForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "items/createItemForm";
        }
        Book book = modelMapper.map(bookForm, Book.class);
        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId,
                                 Model model) {
        Book item = (Book) itemService.findItem(itemId);
        BookForm form = modelMapper.map(item, BookForm.class);
        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("{itemId}/edit")
    public String updateItem(@PathVariable Long itemId,
                             @ModelAttribute("form") BookForm form) {
        form.setId(itemId);
        Book book = modelMapper.map(form, Book.class);
        itemService.saveItem(book);
        return "redirect:/items";
    }

}
