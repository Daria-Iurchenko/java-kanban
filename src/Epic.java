import java.util.HashMap;

public class Epic extends Task{
    HashMap<Integer, Subtask> subtasks = new HashMap<>();

    Epic (String name, String description, Status status, int id){
        super(name, description, status, id);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

}
