package model;

import org.junit.jupiter.api.Test;
import util.enums.Status;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    //экземпляры класса Task равны друг другу, если равен их id;
    public void shouldBeEqualWhenSameId() {
        Task task1 = new Task("name", "description", Status.NEW, 1);
        Task task2 = new Task("name2", "description2", Status.DONE, 1);
        assertEquals(task1, task2);
    }

    @Test
    //наследники класса Task НЕ равны друг другу, если равен их id
    public void differentClassesShouldBeNotEqualWhenSameId() {
        Epic epic = new Epic("name", "description", Status.NEW, 1);
        Subtask subtask = new Subtask("name2", "description2", Status.IN_PROGRESS, 1, 2);
        assertNotEquals(epic, subtask);
    }
}