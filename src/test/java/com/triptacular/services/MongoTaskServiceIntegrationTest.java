package com.triptacular.services;

import com.triptacular.Application;
import com.triptacular.core.Task;
import org.jongo.MongoCollection;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Tests the in memory task service.
 * @author Mike Atkisson
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("file:src/main/resources")
@SpringBootTest(classes = Application.class)
public class MongoTaskServiceIntegrationTest {
    
    private final String DEFAULT_ITEM = "Test Item";
    private int firstId;
    
    @Autowired
    private MongoTaskService service;
    
    @Autowired
    private MongoCollection tasks;
    
    public MongoTaskServiceIntegrationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws UnknownHostException {
        Task task = new Task();
        task.setItem(DEFAULT_ITEM);
        task = service.save(task);
        firstId = task.getId();
    }
    
    @After
    public void tearDown() {
        tasks.remove();
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
        Assert.assertTrue(id > 0);
        Assert.assertTrue(service.getCount() > 1);
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
        Assert.assertNotNull(added);
    }

    /**
     * Determines if a new task can be added using the task item.
     */
    @Test
    public void canAddTaskByItem() {
        Task task = service.add(DEFAULT_ITEM);
        Task added = service.getById(task.getId());
        Assert.assertNotNull(added);
    }

    /**
     * Determines if an existing task can be updated.
     */
    @Test
    public void canUpdateTask() {
        Task task = getFirst();
        task.setItem("Updated item");
        service.update(task);
        Task updated = getFirst();
        Assert.assertEquals(task.getItem(), updated.getItem());
    }

    /**
     * Determines if an existing task can be updated.
     */
    @Test
    public void canSaveExistingTask() {
        Task original = getFirst();
        original.setItem("updated item");
        service.save(original);
        Task updated = getFirst();
        Assert.assertTrue(original.getItem().equals(updated.getItem()));
        Assert.assertTrue(service.getCount() == 1);
    }

    /**
     * Determines if an existing task can be deleted.
     */
    @Test
    public void canDeleteByInstance() {
        Task task = getFirst();
        service.delete(task);
        Assert.assertTrue(service.getCount() == 0);
    }

    /**
     * Determines if an existing task can be deleted by ID.
     */
    @Test
    public void canDeleteById() {
        Task task = getFirst();
        service.delete(task.getId());
        Assert.assertTrue(service.getCount() == 0);
    }

    /**
     * Determines if all tasks can be fetched.
     */
    @Test
    public void canGetAll() {
        List<Task> all = service.getAll();
        Assert.assertEquals(all.size(), service.getCount());
    }

    /**
     * Determines if fetching all tasks returns a non-null collection.
     */
    @Test
    public void getAllReturnsNonNullCollection() {
        tasks.remove();
        Object all = service.getAll();
        Assert.assertNotNull(all);
    }

    /**
     * Determines if a task can be fetched by ID.
     */
    @Test
    public void canGetById() {
        Task first = getFirst();
        Task task = service.getById(firstId);
        Assert.assertNotNull(task);
        Assert.assertTrue(first.getId() == task.getId());
    }

    /**
     * Determines if the getSorted routine returns a sorted collection.
     */
    @Test
    public void doesGetSortedReturnAnOrderedList() {
        Task task = new Task();
        task.setItem("item 2");
        service.save(task);
        List<Task> sorted = service.getSorted();
        for (int i = 0; i < sorted.size() - 1; i++) {
            Task lesser = sorted.get(i);
            Task greater = sorted.get(i + 1);
            Assert.assertTrue(lesser.getId() < greater.getId());
        }
    }

    private Task getFirst() {
        return tasks.findOne("{ id: # }", firstId).as(Task.class);
    }
    
}
