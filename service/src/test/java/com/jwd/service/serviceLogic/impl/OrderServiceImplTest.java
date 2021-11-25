package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.factory.DaoFactory;
import com.jwd.dao.repository.OrderDao;
import com.jwd.dao.repository.UserDao;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.OrderService;
import org.junit.Test;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {

    private OrderDao orderDao = DaoFactory.getInstance().getOrderDao();
    // testing class
    private OrderService orderService = new OrderServiceImpl(orderDao);

    // region parameters
    private int pageNumber = 1;
    private long totalElements = 36;
    private int limit = 1;
    private List<Order> orders = Arrays.asList(
            new Order(89L, 18L, "q", "q", ServiceType.PLUMBING, ServiceStatus.DONE, new SimpleDateFormat("dd/MM/yyyy").parse("07/11/2021"))
    );
    private List<Order> emptyOrders = new ArrayList<>();
    private Order filter = new Order();
    private String sortBy = "id_service";
    private String direction = "ASC";

    public OrderServiceImplTest() throws ParseException {
    }
    // endregion

    @Test(expected = ServiceException.class)
    public void testGetAllServices_validationExceptionNullParameter() throws ServiceException {
        Page<Order> orderPageRequest = null;

        orderService.getAllServices(orderPageRequest);
    }

    /*
    1. orderService.addOrder() write tests
    2. in testGetAllServices() - run orderService.addOrder(), adding new products
    3. in testGetAllServices() - orderService.getAllServices(orderPageRequest), pageNumber = 1, limit == totalElements // вернется все
    4. in testGetAllServices() - assert that NewOrders are among Page<Order> actual result
     */
//    @Test
//    public void testGetAllServices_positive() throws ServiceException, ParseException {
//        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, filter, sortBy, direction);
//        Page<Order> expectedOrderPageRequest = new Page<>(pageNumber, totalElements, limit, orders, filter, sortBy, direction);
//
//        Page<Order> actualOrderPageResult = orderService.getAllServices(orderPageRequest); // todo delete возвращаются реальные данные
//        /*
//        1. add new orders
//        2. getAllServices() -> actualSecondResult
//        3. assert THAT actualSecondResult-elements minus actualOrderPageResult-elements ARE new_orders that we have saved before
//         */
//        assertEquals(expectedOrderPageRequest, actualOrderPageResult);
////        for (int i = 0; i < expectedOrderPageRequest.getElements().size(); i++) {
////            assertEquals(expectedOrderPageRequest.getElements().get(i).getOrderCreationDate(), actualOrderPageResult.getElements().get(i).getOrderCreationDate());
////        }
//
//    }

    @Test
    public void testGetAllServices_daoException() {
        Page<Order> orderPageRequest = new Page<>(-123, totalElements, limit, emptyOrders, filter, sortBy, direction);
        final PSQLException psqlException = new PSQLException("ERROR: OFFSET must not be negative", PSQLState.DATA_ERROR);

        ServiceException actual = null;
        try {
            orderService.getAllServices(orderPageRequest);
        } catch (ServiceException e) {
            actual = e;
        }

        // assert
        assertEquals(new ServiceException(new DaoException(psqlException)).getMessage(), actual.getMessage());
    }
}