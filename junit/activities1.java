package activities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;

public class activities1 {
    // to create fixture
    static ArrayList<String> list;
    // initialise fixtures before each test method
    @BeforeEach
    void setUp() throws Exception{
        list = new ArrayList<String>();
        list.add("alpha");
        list.add("beta");

    }
    // to test the insert operation
    @Test
    public void insertTest(){
        // Assertion for size
        assertEquals(2, list.size(), "Wrong size");
        System.out.println("Array size : " + list.size());
        // Add new element
        list.add(2,"charlie");
        assertEquals(3, list.size(), "Wrong size");

        // Assert individual elements
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("beta", list.get(1), "Wrong element");
        assertEquals("charlie", list.get(2), "Wrong element");
        System.out.println("New Array size : " + list.size());
        System.out.println("New Array values : " + list.toString());
    }
    //to test the replace operation
    @Test
    public void replaceTest() {
        // replace new elements
        list.set(1, "charlie");
        // Assert size of list
        assertEquals(2, list.size(), "Wrong size");

        // Assert individual elements
        assertEquals("alpha", list.get(0), "Wrong Element");
        assertEquals("charlie", list.get(1), "Wrong Element");
         System.out.println("New Array values : " + list.toString());
    }
}
