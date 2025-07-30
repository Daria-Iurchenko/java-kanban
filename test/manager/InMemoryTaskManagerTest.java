package manager;

import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import util.enums.Status;
import util.enums.TaskType;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest {

    private final InMemoryTaskManager taskManager = new InMemoryTaskManager();

    //Проверяем корректность добавления эпика и его получения по методу getTask
    @Test
    public void shouldAddEpicWhenCallCreateTask(){
        Epic epic = new Epic("name", "description", Status.NEW, 1);
        taskManager.createTask(epic, TaskType.EPIC);
        assertEquals(epic, taskManager.getTask(1)); //Проверяем, что эпик добавлен

        //Проверяем поля эпика на соответствие заданным
        assertEquals(1, taskManager.getTask(1).getId());
        assertEquals("name", taskManager.getTask(1).getName());
        assertEquals("description", taskManager.getTask(1).getDescription());
        assertEquals(Status.NEW, taskManager.getTask(1).getStatus());
    }

    //Проверяем корректность добавления подзадачи и ее получения по методу getTask
    @Test
    public void shouldAddSubtaskWhenCallCreateTask(){
        Subtask subtask = new Subtask("name2", "description2", Status.IN_PROGRESS, 1, 2);
        Epic epic = new Epic("name", "description", Status.NEW, 2);
        taskManager.createTask(epic, TaskType.EPIC);
        taskManager.createTask(subtask, TaskType.SUBTASK);
        assertEquals(subtask, taskManager.getTask(1)); //Проверяем, что подзадача добавлена

        //Проверяем поля подзадачи на соответствие заданным
        assertEquals(1, taskManager.getTask(1).getId());
        assertEquals("name2", taskManager.getTask(1).getName());
        assertEquals("description2", taskManager.getTask(1).getDescription());
        assertEquals(Status.IN_PROGRESS, taskManager.getTask(1).getStatus());
        assertEquals(2, ((Subtask) taskManager.getTask(1)).getEpicId());

        //Проверяем, что подзадача добавилась в эпик
        assertEquals(1, ((Epic) taskManager.getTask(2)).getSubtasks().getFirst());
    }

    //Проверяем корректность добавления задачи и ее получения по методу getTask
    @Test
    public void shouldAddTaskWhenCallCreateTask(){
        Task task = new Task("name2", "description2", Status.IN_PROGRESS, 1);
        taskManager.createTask(task, TaskType.TASK);
        assertEquals(task, taskManager.getTask(1));

        //Проверяем поля задачи на соответствие заданным
        assertEquals(1, taskManager.getTask(1).getId());
        assertEquals("name2", taskManager.getTask(1).getName());
        assertEquals("description2", taskManager.getTask(1).getDescription());
        assertEquals(Status.IN_PROGRESS, taskManager.getTask(1).getStatus());
    }

    //Проверяем корректность переноса подзадачи в другой эпик с помощью метода updateTask
    @Test
    public void shouldMoveSubtaskWhenCallUpdateTask(){
        Subtask subtask = new Subtask("name", "description", Status.IN_PROGRESS, 1, 2);
        Epic epic1 = new Epic("name1", "description", Status.NEW, 2);
        Epic epic2 = new Epic("name2", "description", Status.NEW, 3);
        taskManager.createTask(epic1, TaskType.EPIC);
        taskManager.createTask(epic2, TaskType.EPIC);
        taskManager.createTask(subtask, TaskType.SUBTASK);

        subtask = new Subtask("name", "description", Status.IN_PROGRESS, 1, 3);
        taskManager.updateTask(subtask, TaskType.SUBTASK);

        //Проверяем поля эпиков на соответствие
        assertEquals(0, ((Epic) taskManager.getTask(2)).getSubtasks().size());
        assertEquals(Status.NEW, taskManager.getTask(2).getStatus());
        assertEquals(1, ((Epic) taskManager.getTask(3)).getSubtasks().getFirst());
        assertEquals(Status.IN_PROGRESS, taskManager.getTask(3).getStatus());
    }

    //Проверяем корректность удаления задачи
    @Test
    public void shouldDeleteTaskWhenCallDeleteTask(){
        Task task = new Task("name", "description", Status.IN_PROGRESS, 1);
        taskManager.createTask(task, TaskType.TASK);

        taskManager.deleteTask(1);

        //Проверяем корректность удаления задачи
        assertNull(taskManager.getTask(1));
    }

    //Проверяем корректность удаления подзадачи
    @Test
    public void shouldDeleteSubtaskWhenCallDeleteTask(){
        Subtask subtask = new Subtask("name", "description", Status.IN_PROGRESS, 2, 1);
        Epic epic1 = new Epic("name1", "description", Status.NEW, 1);
        taskManager.createTask(epic1, TaskType.EPIC);
        taskManager.createTask(subtask, TaskType.SUBTASK);

        taskManager.deleteTask(2);

        //Проверяем корректность удаления подзадачи
        assertNotNull(taskManager.getTask(1));
        assertNull(taskManager.getTask(2));
    }

    //Проверяем корректность удаления подзадач при удалении эпика
    @Test
    public void shouldDeleteSubtasksWhenCallDeleteTaskForEpic(){
        Subtask subtask = new Subtask("name", "description", Status.IN_PROGRESS, 2, 1);
        Epic epic1 = new Epic("name1", "description", Status.NEW, 1);
        taskManager.createTask(epic1, TaskType.EPIC);
        taskManager.createTask(subtask, TaskType.SUBTASK);

        taskManager.deleteTask(1);

        //Проверяем корректность удаления эпика и его подзадач при удалении эпика
        assertNull(taskManager.getTask(1));
        assertNull(taskManager.getTask(2));
    }

    //Проверяем корректность обновления задачи
    @Test
    public void shouldUpdateTaskWhenCallUpdateTask(){
        Task task = new Task("name", "description", Status.IN_PROGRESS, 1);
        taskManager.createTask(task, TaskType.TASK);
        task = new Task("NEW_name", "NEW_description", Status.DONE, 1);
        taskManager.updateTask(task, TaskType.TASK);

        //Проверяем поля задачи на соответствие новым значениям
        assertEquals(1, taskManager.getTask(1).getId());
        assertEquals("NEW_name", taskManager.getTask(1).getName());
        assertEquals("NEW_description", taskManager.getTask(1).getDescription());
        assertEquals(Status.DONE, taskManager.getTask(1).getStatus());
    }

    //Проверяем корректность обновления подзадачи
    @Test
    public void shouldUpdateSubtaskWhenCallUpdateTask(){
        Subtask subtask = new Subtask("name", "description", Status.IN_PROGRESS, 2, 1);
        Epic epic1 = new Epic("name1", "description", Status.NEW, 1);
        taskManager.createTask(epic1, TaskType.EPIC);
        taskManager.createTask(subtask, TaskType.SUBTASK);
        subtask = new Subtask("NEW_name", "NEW_description", Status.DONE, 2, 1);
        taskManager.updateTask(subtask, TaskType.SUBTASK);

        //Проверяем поля подзадачи на соответствие новым значениям
        assertEquals(2, taskManager.getTask(2).getId());
        assertEquals("NEW_name", taskManager.getTask(2).getName());
        assertEquals("NEW_description", taskManager.getTask(2).getDescription());
        assertEquals(Status.DONE, taskManager.getTask(2).getStatus());
    }

    //Проверяем корректность обновления эпика
    @Test
    public void shouldUpdateEpicWhenCallUpdateTask(){
        Epic epic = new Epic("name1", "description", Status.NEW, 1);
        taskManager.createTask(epic, TaskType.EPIC);
        epic = new Epic("NEW_name", "NEW_description", Status.NEW, 1);
        taskManager.updateTask(epic, TaskType.EPIC);

        //Проверяем поля задачи на соответствие новым значениям
        assertEquals(1, taskManager.getTask(1).getId());
        assertEquals("NEW_name", taskManager.getTask(1).getName());
        assertEquals("NEW_description", taskManager.getTask(1).getDescription());
    }

    //Проверяем корректность обновления статуса эпика при изменении подзадач
    @Test
    public void shouldUpdateEpicStatusWhenCallUpdateTask(){
        Subtask subtask1 = new Subtask("name", "description", Status.NEW, 2, 1);
        Subtask subtask2 = new Subtask("name", "description", Status.NEW, 3, 1);
        Epic epic = new Epic("name1", "description", Status.NEW, 1);
        taskManager.createTask(epic, TaskType.EPIC);
        taskManager.createTask(subtask1, TaskType.SUBTASK);
        taskManager.createTask(subtask2, TaskType.SUBTASK);
        //Проверяем статус эпика
        assertEquals(Status.NEW, taskManager.getTask(1).getStatus());


        subtask1 = new Subtask("name", "description", Status.IN_PROGRESS, 2, 1);
        taskManager.updateTask(subtask1, TaskType.SUBTASK);
        //Проверяем статус эпика
        assertEquals(Status.IN_PROGRESS, taskManager.getTask(1).getStatus());


        subtask1 = new Subtask("name", "description", Status.DONE, 2, 1);
        taskManager.updateTask(subtask1, TaskType.SUBTASK);
        //Проверяем статус эпика
        assertEquals(Status.IN_PROGRESS, taskManager.getTask(1).getStatus());

        subtask2 = new Subtask("name", "description", Status.DONE, 3, 1);
        taskManager.updateTask(subtask2, TaskType.SUBTASK);
        //Проверяем статус эпика
        assertEquals(Status.DONE, taskManager.getTask(1).getStatus());

        Subtask subtask3 = new Subtask("name", "description", Status.NEW, 4, 1);
        taskManager.createTask(subtask3, TaskType.SUBTASK);
        //Проверяем статус эпика
        assertEquals(Status.IN_PROGRESS, taskManager.getTask(1).getStatus());

        taskManager.deleteTask(4);
        //Проверяем статус эпика
        assertEquals(Status.DONE, taskManager.getTask(1).getStatus());
    }

    @AfterEach
    public void afterEach(){
        taskManager.clearTasks();
    }

}