package manager;

import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;
import util.enums.Status;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private final InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    @Test
    public void shouldAdd3ObjectsToHistory(){
        Epic epic = new Epic("name", "description", Status.NEW, 1);
        Subtask subtask = new Subtask("name2", "description2", Status.IN_PROGRESS, 2, 1);
        Task task = new Task("name2", "description2", Status.IN_PROGRESS, 3);
        ArrayList<Task> expectedList = new ArrayList<>();

        expectedList.add(epic);
        expectedList.add(subtask);
        expectedList.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);
        historyManager.add(task);

        for (int i = 0; i <  expectedList.size(); i++){
            assertEquals(expectedList.get(i), historyManager.getHistory().get(i));
        }

    }

    @Test
    public void shouldAdd10ObjectsToHistory(){
        ArrayList<Task> expectedList = new ArrayList<>();
        for (int i = 0; i < 11; i++ ){
            Task task = new Task("name2", "description2", Status.IN_PROGRESS, 3);
            historyManager.add(task);
            expectedList.add(task);
            if (i >= 10){
                expectedList.removeFirst();
            }
        }

        for (int i = 0; i < expectedList.size(); i++){
            assertEquals(expectedList.get(i), historyManager.getHistory().get(i));
        }
    }

    @Test
    public void shouldAdd10ObjectsToHistoryAndNotOvercome(){
        ArrayList<Task> expectedList = new ArrayList<>();
        for (int i = 0; i < 12; i++ ){
            Task task = new Task("name2", "description2", Status.IN_PROGRESS, 3);
            historyManager.add(task);
            expectedList.add(task);
            if (i >= 10){
                expectedList.removeFirst();
            }
        }

        for (int i = 0; i < expectedList.size(); i++){
            assertEquals(expectedList.get(i), historyManager.getHistory().get(i));
        }
    }
}