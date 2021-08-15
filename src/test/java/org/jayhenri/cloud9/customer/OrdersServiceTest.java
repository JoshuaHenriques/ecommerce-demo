package org.jayhenri.cloud9.customer;

import org.jayhenri.cloud9.model.customer.*;
import org.jayhenri.cloud9.model.item.Item;
import org.jayhenri.cloud9.model.login.Login;
import org.jayhenri.cloud9.repository.customer.OrdersRepository;
import org.jayhenri.cloud9.service.customer.CustomerService;
import org.jayhenri.cloud9.service.customer.OrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * The type Order db service test.
 */
@ExtendWith(MockitoExtension.class)
class OrdersServiceTest {

    /**
     * The Order db repository.
     */
    @Mock
    private OrdersRepository ordersRepository;

    /**
     * The Order db repository.
     */
    @Mock
    private CustomerService customerService;

    @Captor
    private ArgumentCaptor<Customer> captorCustomer;

    @Captor
    private ArgumentCaptor<UUID> captorUUID;

    /**
     * The Order db repository.
     */
    private OrdersService ordersService;
    
    private Customer customer;
    
    private Orders orders;

    private UUID uuid;

    /**
     * The Argument captor.
     */
    @Captor
    private ArgumentCaptor<Orders> captorOrders;

    @BeforeEach
    void setUp() {
        ordersService = new OrdersService(ordersRepository, customerService);

        customer = new Customer(
                "customer.mail@gmail.com",
                new Login(),
                new Cart(),
                new Address(),
                new HashSet<CreditCard>(),
                new HashSet<Orders>(),
                "John",
                "Doe",
                "6473829338",
                "08/23/1995"
        );
        
        orders = new Orders(
                "PENDING",
                new HashSet<Item>(),
                293.68
        );
    }
    
    /**
     * Add order.
     */
    @Test
    void addOrder() {

        ordersService.addOrder(customer, orders);

        then(customerService).should().update(captorCustomer.capture());

        assertThat(captorCustomer.getValue()).isEqualTo(customer);
        assertThat(captorCustomer.getValue().getOrders().contains(orders)).isTrue();
    }

    /**
     * Update order.
     */
    @Test
    void updateOrder() {
        orders.setOrderStatus("COMPLETED");
        ordersService.updateOrders(orders);

        then(ordersRepository).should().save(captorOrders.capture());

        assertThat(captorOrders.getValue().getOrderStatus()).isEqualTo("COMPLETED");
        assertThat(orders).isEqualTo(captorOrders.getValue());
    }

    /**
     * Find all orders.
     */
    @Test
    void findAllOrders() {

        Set<Orders> orders = customer.getOrders();
        Set<Orders> findAllOrders = ordersService.findAllOrders(customer);

        assertThat(findAllOrders).isEqualTo(orders);
    }

    @Test
    void existsById() {

        given(ordersRepository.existsById(uuid)).willReturn(true);

        boolean exists = ordersService.existsById(uuid);

        then(ordersRepository).should().existsById(captorUUID.capture());

        assertThat(exists).isTrue();
        assertThat(captorUUID.getValue()).isEqualTo(uuid);
    }

    @Test
    void doesNotExistsById() {

        given(ordersRepository.existsById(uuid)).willReturn(false);

        boolean exists = ordersService.existsById(uuid);

        then(ordersRepository).should().existsById(captorUUID.capture());

        assertThat(exists).isFalse();
        assertThat(captorUUID.getValue()).isEqualTo(uuid);
    }

    @Test
    void getById() {

        given(ordersRepository.getById(uuid)).willReturn(orders);

        Orders _orders = ordersService.getById(uuid);

        then(ordersRepository).should().getById(captorUUID.capture());

        assertThat(_orders).isEqualTo(orders);
        assertThat(captorUUID.getValue()).isEqualTo(uuid);
    }
}