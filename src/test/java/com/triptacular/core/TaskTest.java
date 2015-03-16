package com.triptacular.core;

import org.junit.*;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author mike
 */
public class TaskTest {
    
    public TaskTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of compareTo method, of class Task.
     */
    @Test
    public void doesComparatorCorrectlyEvaluateLessThan() {
        Task task1 = new Task();
        task1.setId(1);
        Task task2 = new Task();
        task2.setId(2);
        assertTrue(task1.compareTo(task2) < 0);
    }
    
    /**
     * Determines if comparator evaluates equality correctly.
     */
    @Test
    public void doesComparatorCorrectlyEvaluateEquality() {
        Task task1 = new Task();
        task1.setId(1);
        Task task2 = new Task();
        task2.setId(1);
        assertTrue(task1.compareTo(task2) == 0);
    }
    
    /**
     * Determines if comparator evaluates greater than correctly.
     */
    @Test
    public void doesComparatorCorrectlyEvaluateGreaterThan() {
        Task task1 = new Task();
        task1.setId(2);
        Task task2 = new Task();
        task2.setId(1);
        assertTrue(task1.compareTo(task2) > 0);
    }
    
}
