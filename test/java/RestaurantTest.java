import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void setup() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant mockedRestaurant = spy(restaurant);
        when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("12:00:00"));

        boolean openStatus = mockedRestaurant.isRestaurantOpen();

        assertTrue(openStatus);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant mockedRestaurant = spy(restaurant);
        when(mockedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("23:00:00"));

        boolean openStatus = mockedRestaurant.isRestaurantOpen();

        assertFalse(openStatus);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    @Test
    public void calling_getOrderValue_with_2_items_should_return_proper_order_value() {
        // Not using menu items added in @BeforeAll to avoid possible future issues due to dependency
        List<String> itemNames = Arrays.asList("Apple Pie", "Cheese chilly toast");
        int[] itemPrices = { 149, 99 };
        restaurant.addToMenu(itemNames.get(0), itemPrices[0]);
        restaurant.addToMenu(itemNames.get(1), itemPrices[1]);
        int expectedOrderValue = itemPrices[0] + itemPrices[1];

        int orderValue = restaurant.getOrderValue(itemNames.get(0), itemNames.get(1));

        assertEquals(expectedOrderValue, orderValue);
    }
    @Test
    public void calling_getOrderValue_without_arguments_should_return_0() {
        int expectedOrderValue = 0;

        int orderValue = restaurant.getOrderValue();

        assertEquals(expectedOrderValue, orderValue);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}