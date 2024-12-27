package backend.ToDoApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class ToDoController {

    private final ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public List<ToDo> getAllToDos() {
        return toDoService.getAllToDos();
    }

    @GetMapping("/{id}")
    public ToDo getToDoById(@PathVariable Long id) {
        return toDoService.getToDoById(id);
    }

    @PostMapping
    public ToDo createToDo(@RequestBody ToDo toDo) {
        return toDoService.createToDo(toDo);
    }

    @PutMapping("/{id}")
    public ToDo updateToDo(@PathVariable Long id, @RequestBody ToDo toDo) {
        return toDoService.updateToDo(id, toDo);
    }

    @DeleteMapping("/{id}")
    public void deleteToDo(@PathVariable Long id) {
        toDoService.deleteToDo(id);
    }
}
