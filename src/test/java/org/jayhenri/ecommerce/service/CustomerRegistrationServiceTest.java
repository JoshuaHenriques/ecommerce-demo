package org.jayhenri.ecommerce.service;

import org.jayhenri.ecommerce.exception.CustomerAlreadyExistsException;
import org.jayhenri.ecommerce.exception.InvalidPostalCodeException;
import org.jayhenri.ecommerce.model.*;
import org.jayhenri.ecommerce.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

/**
 * The type Customer registration service test.
 */
@ExtendWith(MockitoExtension.class)
class CustomerRegistrationServiceTest {

    /**
     * The Test me.
     */
    private CustomerRegistrationService testMe;

    /**
     * The Customer repository.
     */
    @Mock
    private CustomerRepository customerRepository;

    /**
     * The Captor customer.
     */
    @Captor
    private ArgumentCaptor<Customer> captorCustomer;

    /**
     * The Captor string.
     */
    @Captor
    private ArgumentCaptor<String> captorString;

    /**
     * The Customer.
     */
    private Customer customer;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        testMe = new CustomerRegistrationService(customerRepository);
        ArrayList<Item> items = new ArrayList<>();
        ArrayList<OrderDetails> orderDetails = new ArrayList<>();
        ArrayList<CreditCard> creditCards = new ArrayList<>();

        Item item = new Item(
                "Test Product",
                "Test Description",
                9.99
        );

        items.add(item);

        OrderDetails orderDetails1 = new OrderDetails(
                "PROCESSING",
                "testMe@gmail.com",
                items,
                9.00
        );
        Cart cart = new Cart(
                items,
                "testMe@gmail.com",
                29.23
        );
        CreditCard creditCard = new CreditCard(
                "Ubuntu User",
                "4716902620158281",
                "8281",
                "05/25",
                "8281"
        );

        orderDetails.add(orderDetails1);
        creditCards.add(creditCard);

        customer = new Customer(
                "testMe",
                "TestMe",
                "2934811932",
                "testMe@gmail.com",
                "testMePassword",
                "082395",
                new Address(
                        "Test Me",
                        "29L",
                        "0L",
                        "New York",
                        "T2K9R3",
                        "Province"
                ),
                cart,
                creditCards,
                orderDetails
        );
    }

    /**
     * Test add.
     *
     * @throws InvalidPostalCodeException     the invalid postal code exception
     * @throws CustomerAlreadyExistsException the customer already exists exception
     */
    @Test
    void testAdd() {
        testMe.add(this.customer);

        then(customerRepository).should().save(captorCustomer.capture());

        assertThat(captorCustomer.getValue()).isEqualTo(this.customer);
    }

    /**
     * Exists by phone number.
     */
    @Test
    void existsByPhoneNumber() {
        given(customerRepository.existsByPhoneNumber("1234567890"))
                .willReturn(true);

        Boolean bool = testMe.existsByPhoneNumber("1234567890");
        then(customerRepository).should().existsByPhoneNumber(captorString.capture());

        assertThat(captorString.getValue()).isEqualTo("1234567890");
        assertThat(bool).isTrue();
    }

    /**
     * Does not exists by phone number.
     */
    @Test
    void doesNotExistsByPhoneNumber() {
        given(customerRepository.existsByPhoneNumber("1234567890"))
                .willReturn(false);

        Boolean bool = testMe.existsByPhoneNumber("1234567890");
        then(customerRepository).should().existsByPhoneNumber(captorString.capture());

        assertThat(captorString.getValue()).isEqualTo("1234567890");
        assertThat(bool).isFalse();
    }

    /**
     * Is valid postal code.
     */
    @Test
    void isValidPostalCode() {
        Boolean bool = testMe.isValidPostalCode("M1C8N3");

        assertThat(bool).isTrue();
    }

    /**
     * Is not valid postal code.
     */
    @Test
    void isNotValidPostalCode() {
        Boolean bool = testMe.isValidPostalCode("M1CM8N3");

        assertThat(bool).isFalse();
    }

    /**
     * Exists by email.
     */
    @Test
    void existsByEmail() {
        given(customerRepository.existsByEmail("testMe@gmail.com"))
                .willReturn(true);

        Boolean bool = testMe.existsByEmail("testMe@gmail.com");
        then(customerRepository).should().existsByEmail(captorString.capture());

        assertThat(captorString.getValue()).isEqualTo("testMe@gmail.com");
        assertThat(bool).isTrue();
    }

    /**
     * Does not exists by email.
     */
    @Test
    void doesNotExistsByEmail() {
        given(customerRepository.existsByEmail("testMe@gmail.com"))
                .willReturn(false);

        Boolean bool = testMe.existsByEmail("testMe@gmail.com");
        then(customerRepository).should().existsByEmail(captorString.capture());

        assertThat(captorString.getValue()).isEqualTo("testMe@gmail.com");
        assertThat(bool).isFalse();
    }
}