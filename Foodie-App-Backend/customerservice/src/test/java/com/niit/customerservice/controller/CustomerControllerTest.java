//
//import com.niit.customerservice.controller.CustomerController;
//import com.niit.customerservice.domain.Customer;
//import com.niit.customerservice.domain.Food;
//import com.niit.customerservice.exception.CustomerAlreadyExistsException;
//import com.niit.customerservice.exception.FavoriteException;
//import com.niit.customerservice.service.ICustomerService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class CustomerControllerTest {
//
//    @Mock
//    private ICustomerService customerService;
//
//    @InjectMocks
//    private CustomerController customerController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void testRegisterCustomer() throws CustomerAlreadyExistsException {
//        Customer customer = new Customer();
//        when(customerService.registerCustomer(customer)).thenReturn(customer);
//
//        ResponseEntity<?> response = customerController.registerCustomer(customer);
//
//        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
//        assertEquals(customer, response.getBody());
//    }
//
//    @Test
//    void testAddCustomerFavToList() throws FavoriteException {
//        Food food = new Food();
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        when(request.getAttribute("emailId")).thenReturn("test@example.com");
//
//        Customer customer = new Customer(); // Create a mock Customer object
//        when(customerService.addCustomerFavToList(food, "test@example.com")).thenReturn(customer);
//
//        ResponseEntity<?> response = customerController.addCustomerFavToList(food, request);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(customer, response.getBody());
//    }
//
//    // Similarly, you can write test cases for viewFavoriteList() and deleteFoodFromFavorite() methods
//}
