package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.enums.Status;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class EpicTest {
    private Epic epic;

    @BeforeEach
    public void beforeEach() {
        epic = new Epic("name", "description", Status.NEW, 1);
    }

    @Test
    //Проверка метода addSubtask
    public void shouldBeSubtaskWhenAddSubtask() {
        epic.addSubtask(2);
        ArrayList<Integer> expectedSubtasks = new ArrayList<>();
        expectedSubtasks.add(2);
        assertEquals(expectedSubtasks.getFirst(), epic.getSubtasks().getFirst());
    }

    @Test
    //Проверка метода deleteSubtask
    public void shouldBeNoSubtaskWhenDeleteSubtask() {
        epic.addSubtask(2);
        epic.deleteSubtask(2);
        assertEquals(0,epic.getSubtasks().size());
    }

    @Test
    //Epic нельзя добавить в самого себя в виде подзадачи
    public void shouldBeNoActionWhenEpicAndSubtaskHaveSameId() {
        epic.addSubtask(1);
        assertEquals(0,epic.getSubtasks().size());
    }
}
