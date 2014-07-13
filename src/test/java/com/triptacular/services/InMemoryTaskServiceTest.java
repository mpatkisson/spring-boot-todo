package com.triptacular.services;

import com.triptacular.core.Task;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the in memory task service.
 * @author Mike Atkisson
 */
public class InMemoryTaskServiceTest {
    
    private final String DEFAULT_ITEM = "Test Item";
    private int firstId;
    private InMemoryTaskService service;
    
    public InMemoryTaskServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        service = new InMemoryTaskService();
        Task task = new Task();
        task.setItem(DEFAULT_ITEM);
        task = service.save(task);
        firstId = task.getId();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Determines if a new task can be saved ensuring the ID of the task is
     * created by the service.
     */
    @Test
    public void canSaveNewTask() {
        Task task = new Task();
        task.setItem("item");
        task = service.save(task);
        int id = task.getId();
        assertTrue(id > 0);
        assertTrue(service.getCount() > 1);
    }
    
    /**
     * Determines if a new task can be added.   The ID of the task is created
     * by the service.
     */
    @Test
    public void canAddTask() {
        Task task = new Task();
        task.setItem("New Item");
        task = service.add(task);
        Task added = service.getById(task.getId());
        assertNotNull(added);
    }
    
    /**
     * Determines if a new task can be added using the task item.
     */
    @Test
    public void canAddTaskByItem() {
        Task task = service.add(DEFAULT_ITEM);
        Task added = service.getById(task.getId());
        assertNotNull(added);
    }
    
    /**
     * Determines if an existing task can be updated.
     */
    @Test
    public void canUpdateTask() {
        Task task = service.getFirst();
        task.setItem("Updated item");
        service.update(task);
        Task updated = service.getFirst();
        assertEquals(task.getItem(), updated.getItem());
    }
    
    /**
     * Determines if an existing task can be updated.
     */
    @Test
    public void canSaveExistingTask() {
        Task original = service.getById(firstId);
        original.setItem("updated item");
        Task updated = service.save(original);
        assertTrue(original.getItem().equals(updated.getItem()));
        assertTrue(service.getCount() == 1);
    }
    
    /**
     * Determines if an existing task can be deleted.
     */
    @Test
    public void canDeleteByInstance() {
        Task task = service.getFirst();
        service.delete(task);
        assertTrue(service.getCount() == 0);
    }
    
    /**
     * Determines if an existing task can be deleted by ID.
     */
    @Test
    public void canDeleteById() {
        Task task = service.getFirst();
        service.delete(task.getId());
        assertTrue(service.getCount() == 0);
    }
    
    /**
     * Determines if all tasks can be fetched.
     */
    @Test
    public void canGetAll() {
        List<Task> tasks = service.getAll();
        assertEquals(tasks.size(), service.getCount());
    }
    
    /**
     * Determines if fetching all tasks returns a non-null collection.
     */
    @Test
    public void getAllReturnsNonNullCollection() {
        InMemoryTaskService service = new InMemoryTaskService();
        Object tasks = service.getAll();
        assertNotNull(tasks);
    }
    
    /**
     * Determines if a task can be fetched by ID.
     */
    @Test
    public void canGetById() {
        Task task = service.getById(firstId);
        assertNotNull(task);
    }
    
    /**
     * Determines if the getSorted routine returns a sorted collection.
     */
    @Test
    public void doesGetSortedReturnAnOrderedList() {
        Task task = new Task();
        task.setItem("item 2");
        service.save(task);
        List<Task> tasks = service.getSorted();
        for (int i = 0; i < tasks.size() - 1; i++) {
            Task lesser = tasks.get(i);
            Task greater = tasks.get(i + 1);
            assertTrue(lesser.getId() < greater.getId());
        }
    }
    
}
