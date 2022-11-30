package com.example.studyhtmx.controller;

import com.example.studyhtmx.entity.TodoItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("todo")
public class TodoController {

    private static List<TodoItem> todos = new ArrayList<>();

    static {
        todos.add(new TodoItem(1L, "movie", false));
        todos.add(new TodoItem(2L, "eating", false));
        todos.add(new TodoItem(3L, "reading book", false));
        todos.add(new TodoItem(4L, "homework", false));
        todos.add(new TodoItem(5L, "study", false));
        todos.add(new TodoItem(6L, "working", false));
    }

    @GetMapping()
    public String index(Model model) {
        addAttributesForIndex(model, ListFilter.ALL);
        return "todo/index";
    }

    @PostMapping(headers = "HX-Request")
    public String htmxAddTodoItem(TodoItem todoItem,
                                  Model model,
                                  HttpServletResponse response) {
        todoItem.setId(todos.size() + 1L);
        todoItem.setCompleted(false);
        todos.add(todoItem);

        model.addAttribute("item", todoItem);
        response.setHeader("HX-Trigger", "itemAdded");
        return "fragments :: todoItem";
    }

    @PutMapping(value = "{id}/toggle", headers = "HX-Request")
    public String toggleSelection(@PathVariable Long id,
                                  Model model,
                                  HttpServletResponse response) {

        TodoItem todoItem = todos.stream().filter(todo -> todo.getId().equals(id)).findFirst().orElseThrow();

        todoItem.setCompleted(!todoItem.getCompleted());

        model.addAttribute("item", todoItem);
        response.setHeader("HX-Trigger", "itemCompletionToggled");
        return "fragments :: todoItem";
    }

    @DeleteMapping(value = "{id}", headers = "HX-Request")
    @ResponseBody
    public String htmxDeleteTodoItem(@PathVariable Long id,
                                     HttpServletResponse response) {
        todos = todos.stream().filter(todo -> !todo.getId().equals(id)).collect(Collectors.toList());

        response.setHeader("HX-Trigger", "itemDeleted");
        return "";
    }

    @PostMapping("toggle-all")
    public String toggleAll() {
        for (TodoItem todo : todos) {
            todo.setCompleted(!todo.getCompleted());
        }
        return "redirect:/";
    }

    @GetMapping("active")
    public String indexActive(Model model) {
        addAttributesForIndex(model, ListFilter.ACTIVE);
        return "todo/index";
    }

    @GetMapping("completed")
    public String indexCompleted(Model model) {
        addAttributesForIndex(model, ListFilter.COMPLETED);
        return "todo/index";
    }

    @GetMapping(value = "active-items-count", headers = "HX-Request")
    public String htmxActiveItemsCount(Model model) {
        model.addAttribute("numberOfActiveItems", todos.stream().filter(todo -> !todo.getCompleted()).count());

        return "fragments :: active-items-count";
    }

    @PostMapping("completed")
    public String deleteCompletedItems() {
        todos = todos.stream().filter(todo -> !todo.getCompleted()).collect(Collectors.toList());
        return "redirect:/";
    }


    private void addAttributesForIndex(Model model, ListFilter filter) {
        model.addAttribute("item", new TodoItem());
        model.addAttribute("filter", filter);
        model.addAttribute("todos", getTodoItems(filter));
        model.addAttribute("totalNumberOfItems", todos.size());
        model.addAttribute("numberOfActiveItems", todos.stream().filter(todo -> !todo.getCompleted()).count());
        model.addAttribute("numberOfCompletedItems", todos.stream().filter(TodoItem::getCompleted).count());
    }

    private List<TodoItem> getTodoItems(ListFilter filter) {
        switch (filter) {
            case ACTIVE:
                return todos.stream().filter(todo -> !todo.getCompleted()).collect(Collectors.toList());
            case COMPLETED:
                return todos.stream().filter(TodoItem::getCompleted).collect(Collectors.toList());
            case ALL:
                return todos;
            default:
                return todos;
        }
    }

    public enum ListFilter {
        ALL,
        ACTIVE,
        COMPLETED
    }

}
