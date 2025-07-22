import java.util.HashMap;
import java.util.Objects;

public class TaskManager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    Integer maxId = 0;

    // Получение списка всех задач
    void getListOfTasks(){
        for (Task task : tasks.values()){
            System.out.println(task.toString());
        }
    }

    // Удаление всех задач
    void clearTasks(){
        tasks.clear();
    }

    // Получение задачи по идентификатору
    Task getTask(int id){
        return tasks.get(id);
    }

    // Создание задачи
    void createTask(Task task, TaskType taskType){
        if (!tasks.containsValue(task)){
            if (Objects.requireNonNull(taskType) == TaskType.SUBTASK) {
                Subtask subtask = (Subtask) task;
                if (tasks.containsKey(subtask.epicId) && getTask(subtask.epicId).getClass().toString().equals("class Epic")){
                    tasks.put(task.id, subtask);
                    Epic epic = (Epic) getTask(subtask.epicId);
                    epic.subtasks.put(task.id, subtask);
                    updateEpicStatus(subtask.epicId);
                } else {
                    System.out.println("Нет эпика с указанным номером");
                }
            } else {
                tasks.put(task.id, task);
            }
        }
    }

    // Обновление задачи
    void updateTask(Task task, TaskType taskType){
        if (tasks.containsValue(task)){
            if (Objects.requireNonNull(taskType) == TaskType.SUBTASK) {
                Subtask subtask = (Subtask) task;
                if (tasks.containsKey(subtask.epicId) && getTask(subtask.epicId).getClass().toString().equals("class Epic")) {
                    Subtask oldSubtask = (Subtask) getTask(subtask.id);

                    // Перенос подзадачи в другой эпик
                    if (subtask.epicId != oldSubtask.epicId) {
                        //Удаляем задачу в эпике
                        Epic epic = (Epic) getTask(oldSubtask.epicId);
                        epic.subtasks.remove(oldSubtask.id);
                    }

                    // Обновляем/добавляем задачу в эпике
                    tasks.put(subtask.getId(), subtask);
                    Epic epic = (Epic) getTask(subtask.epicId);
                    epic.subtasks.put(task.id, subtask);
                    updateEpicStatus(subtask.epicId);
                } else {
                    System.out.println("Нет эпика с указанным номером");
                }
            } else {
                tasks.put(task.getId(), task);
            }
        } else{
            System.out.println("Задачи с таким номером нет");
        }
    }

    // Удаление задачи по идентификатору
    void deleteTask(int id){
        // При удалении эпика все подзадачи также удаляются
        if (tasks.containsKey(id)  && getTask(id).getClass().toString().equals("class Epic")) {
            Epic epic = (Epic) getTask(id);
            for (Subtask subtask : epic.subtasks.values()){
                deleteTask(subtask.id);
            }
        }
        tasks.remove(id);
    }

    // Получение списка всех подзадач определённого эпика
    void getEpicTasks(int epicId){
        if (tasks.containsKey(epicId) && getTask(epicId).getClass().toString().equals("class Epic")){
            Epic epic = (Epic) getTask(epicId);
            for (Subtask subtask : epic.subtasks.values()){
                System.out.println(subtask);
            }
        } else{
            System.out.println("Эпика с таким номером не существует");
        }
    }

    // Рассчитать id для новой задачи
    Integer setId(){
        maxId++;
        return maxId;
    }

    // Обновление статуса эпика в соответствии с его подзадачами
    void updateEpicStatus(int epicId){
        Epic epic = (Epic) getTask(epicId);
        Status newStatus = Status.IN_PROGRESS;
        int doneCount = 0;
        int newCount = 0;
        int totalCount = epic.subtasks.size();

        for (Subtask subtask: epic.subtasks.values()){
            Status status = subtask.status;
            switch (status) {
                case DONE:
                    doneCount++;
                    break;
                case IN_PROGRESS:
                    break;
                case NEW:
                    newCount++;
                    break;
            }
        }
        if (doneCount == totalCount){
            newStatus = Status.DONE;
        } else if (totalCount == newCount){
            newStatus = Status.NEW;
        }
        epic.status = newStatus;
        updateTask(epic, TaskType.EPIC);
    }


}
