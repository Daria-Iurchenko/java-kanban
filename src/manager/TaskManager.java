package manager;
import model.Task;
import util.enums.TaskType;

import java.util.HashMap;

public interface TaskManager{
    // Получение списка всех задач
    void getListOfTasks();

    // Удаление всех задач
    void clearTasks();

    // Получение задачи по идентификатору
    Task getTask(int id);

    // Создание задачи
    void createTask(Task task, TaskType taskType);

    // Обновление задачи
    void updateTask(Task task, TaskType taskType);

    // Удаление задачи по идентификатору
    void deleteTask(int id);

    // Получение списка всех подзадач определённого эпика
    void getEpicTasks(int epicId);

    // Рассчитать id для новой задачи
    Integer setId();

    // Обновление статуса эпика в соответствии с его подзадачами
    void updateEpicStatus(int epicId);

    HashMap<Integer, Task> getTasks();

    void setTasks(HashMap<Integer, Task> tasks);
}
