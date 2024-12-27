package backend.ToDoApplication;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ToDoService {
    // ToDo-list för projektet
    private final List<ToDo> toDoList = new ArrayList<>();
    private Long currentId = 1L;

    // Hämta alla todos
    public List<ToDo> getAllToDos() {
        return toDoList;
    }

    // Hämta specifik todo med id
    public ToDo getToDoById(Long id) {
        return toDoList.stream()
                .filter(toDo -> toDo.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ToDo not found with id: " + id));
    }

    // Skapa ny Todo i listan
    public ToDo createToDo(ToDo toDo) {
        toDo.setId(currentId++);
        toDoList.add(toDo);
        return toDo;
    }

    // Uppdatera todo med id
    public ToDo updateToDo(Long id, ToDo updatedToDo) {
        ToDo toDo = getToDoById(id);
        toDo.setTitle(updatedToDo.getTitle());
        toDo.setDescription(updatedToDo.getDescription());
        //toDo.setCompleted(updatedToDo.isCompleted());
        return toDo;
    }

    // Ta bort todo med id
    public void deleteToDo(Long id) {

        boolean removed =  toDoList.removeIf(toDo -> toDo.getId().equals(id));
        if (!removed) {
            throw new RuntimeException("ToDo not found with id: " + id);
        }
        //return ResponseEntity.ok("Todo deleted successfully");
    }
}
