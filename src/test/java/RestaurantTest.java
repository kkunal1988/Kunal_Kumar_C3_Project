import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    //spoof variable acts as the menu selected by the user
    List<Item> mockedItemList = new ArrayList<Item>();

    public void create_restaurant_data(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        create_restaurant_data();
        restaurant.openingTime = LocalTime.now().minusMinutes(10); // Setting restaurant openingTime to 10 minutes before currentTime
        /*
        An alternate and good practice would be to define the closingTime variable as private and have a setter method in Restaurant class.
        Use the setter method here to set the closingTime.

        public void setClosingTime(LocalTime closingTime) {
            this.closingTime = closingTime;
        }

        */
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        create_restaurant_data();
        restaurant.closingTime = LocalTime.now().minusMinutes(10); // Setting restaurant closingTime to 10 minutes before currentTime
        /*
        An alternate and good practice would be to define the closingTime variable as private and have a setter method in Restaurant class.
        Use the setter method here to set the closingTime.

        public void setClosingTime(LocalTime closingTime) {
            this.closingTime = closingTime;
        }

        */
        assertFalse(restaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER VALUE<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void order_value_should_get_cumulative_total_when_collection_of_items_selected(){
        create_restaurant_data();
        mockedItemList = restaurant.getMenu();
        assertEquals(388,restaurant.getOrderValue(mockedItemList));
    }

    @Test
    public void order_value_should_reduce_cumulative_total_when_an_item_removed(){
        create_restaurant_data();
        mockedItemList = restaurant.getMenu();
        int total = restaurant.getOrderValue(mockedItemList);
        int afterTotal = mockedItemList.get(1).getPrice();
        mockedItemList.remove(1);
        assertEquals(total-afterTotal,restaurant.getOrderValue(mockedItemList));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        create_restaurant_data();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        create_restaurant_data();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        create_restaurant_data();
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}