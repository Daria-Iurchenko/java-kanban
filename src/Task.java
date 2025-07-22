import java.util.Objects;

public class Task {
    String name;
    String description;
    int id;
    Status status;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    Task (String name, String description, Status status, int id){
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
    }
}
