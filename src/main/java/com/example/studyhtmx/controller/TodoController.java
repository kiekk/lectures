package com.example.studyhtmx.controller;

import com.example.studyhtmx.entity.TodoItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/todo")
public class TodoController {

    private static final List<TodoItem> todos = new ArrayList<>();

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
        model.addAttribute("item", new TodoItem());
        model.addAttribute("todos", todos);
        model.addAttribute("totalNumberOfItems", todos.size());
        model.addAttribute("numberOfActiveItems", todos.stream().filter(todo -> !todo.getCompleted()).count());
        model.addAttribute("numberOfCompletedItems", todos.stream().filter(TodoItem::getCompleted).count());
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

}
