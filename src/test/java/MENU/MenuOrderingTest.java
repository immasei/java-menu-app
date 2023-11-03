package MENU;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
public class MenuOrderingTest {
    OrderHistory orderHistoryPanel;
    MenuOrdering menuOrdering;
    @Before
    public void setup() {
        orderHistoryPanel = new OrderHistory();
        menuOrdering = new MenuOrdering(orderHistoryPanel);
    }
    @Test
    public void testAddToCart1() {
        MenuItem pizza = new MenuItem("Pizza", "Cheese Pizza", 10.0);

        menuOrdering.addToCart(pizza, 2);
        double totalAmount = menuOrdering.calculateTotalAmount();
        String tot2f = String.format("%.02f", totalAmount);

        assertEquals("20.00", tot2f);
    }
    @Test
    public void testAddToCart2() {
        MenuItem pizza = new MenuItem("Pizza", "Cheese Pizza", 10.0);
        MenuItem pizza1 = new MenuItem("Pizza", "Cheese Pizza", 12.0);
        menuOrdering.addToCart(pizza, 2);
        menuOrdering.addToCart(pizza1, 2);
        double totalAmount = menuOrdering.calculateTotalAmount();
        String tot2f = String.format("%.02f", totalAmount);

        assertEquals("44.00", tot2f);
    }

    @Test
    public void testAddToCart3() {
        MenuItem pizza = new MenuItem("Pizza", "Cheese Pizza", 10.0);
        MenuItem pizza1 = new MenuItem("Pizza", "Cheese Pizza", 12.0);;
        MenuItem burger = new MenuItem("Burger", "Burger is good", 5.0);

        menuOrdering.addToCart(pizza, 2);
        menuOrdering.addToCart(pizza1, 2);
        menuOrdering.addToCart(burger,1);
        double totalAmount = menuOrdering.calculateTotalAmount();
        String tot2f = String.format("%.02f", totalAmount);

        assertEquals("49.00", tot2f);
    }

    @Test
    public void testRemoveFromCart1(){
        MenuItem pizza = new MenuItem("Pizza", "Cheese Pizza", 10.0);
        menuOrdering.addToCart(pizza,2);
        menuOrdering.removeFromCart(pizza,1);
        double totalAmount = menuOrdering.calculateTotalAmount();
        String tot2f = String.format("%.02f", totalAmount);
        assertEquals("10.00",tot2f);
    }

    @Test
    public void testRemoveFromCart2(){
        MenuItem pizza = new MenuItem("Pizza", "Cheese Pizza", 10.0);
        menuOrdering.addToCart(pizza,2);
        menuOrdering.removeFromCart(pizza,2);
        double totalAmount = menuOrdering.calculateTotalAmount();
        String tot2f = String.format("%.02f", totalAmount);
        assertEquals("0.00",tot2f);
    }
    @Test
    public void testRemoveFromCart3(){
        MenuItem pizza = new MenuItem("Pizza", "Cheese Pizza", 10.0);
        MenuItem burger = new MenuItem("Burger", "Beef Burger", 5.0);
        menuOrdering.addToCart(burger,10);
        menuOrdering.addToCart(pizza,2);
        menuOrdering.removeFromCart(pizza,1);
        menuOrdering.removeFromCart(burger,8);
        double totalAmount = menuOrdering.calculateTotalAmount();
        String tot2f = String.format("%.02f", totalAmount);
        assertEquals("20.00",tot2f);
    }
    @Test
    public void testClearCart(){
        MenuItem pizza = new MenuItem("Pizza", "Cheese Pizza", 10.0);
        MenuItem burger = new MenuItem("Burger", "Beef Burger", 5.0);
        menuOrdering.addToCart(burger,10);
        menuOrdering.addToCart(pizza,2);
        menuOrdering.clearCart();
        double totalAmount = menuOrdering.calculateTotalAmount();
        String tot2f = String.format("%.02f", totalAmount);
        assertEquals("0.00",tot2f);
    }
    @Test
    public void testClearCart1(){
        MenuItem pizza = new MenuItem("Pizza", "Cheese Pizza", 10.0);
        MenuItem burger = new MenuItem("Burger", "Beef Burger", 5.0);
        menuOrdering.addToCart(burger,1000);
        menuOrdering.addToCart(pizza,2);
        menuOrdering.clearCart();
        double totalAmount = menuOrdering.calculateTotalAmount();
        String tot2f = String.format("%.02f", totalAmount);
        assertEquals("0.00",tot2f);

        System.out.println(burger);
    }

    @Test
    public void generateOrderNo() {
        String orderID = menuOrdering.generateOrderNo();
        assertEquals(orderID.length(), 8);
    }

    @Test
    public void isNum(){
        boolean t = menuOrdering.isNumber("12344");
        assertTrue(t);
    }

    @Test
    public void isNum2(){
        boolean t = menuOrdering.isNumber("a12344");
        assertFalse(t);
    }
}