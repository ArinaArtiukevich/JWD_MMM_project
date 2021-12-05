package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.User;
import com.jwd.dao.entity.enumType.Gender;
import com.jwd.dao.entity.enumType.ServiceStatus;
import com.jwd.dao.entity.enumType.ServiceType;
import com.jwd.dao.entity.enumType.UserRole;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.factory.DaoFactory;
import com.jwd.dao.repository.OrderDao;
import com.jwd.dao.repository.UserDao;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.validator.ServiceValidator;
import org.junit.Test;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceImplTest {

    private OrderDao orderDao = DaoFactory.getInstance().getOrderDao();
    private UserDao userDao = DaoFactory.getInstance().getUserDao();

    // testing class
    private ServiceValidator serviceValidator = new ServiceValidator();
    private OrderService orderService = new OrderServiceImpl(orderDao, serviceValidator);

    // region parameters
    private int pageNumber = 1;
    private long totalElements = 100;
    private int limit = 100;
    private List<Order> orders = Arrays.asList(
            new Order(137L, 42L, "change roof", "esenina 1", ServiceType.ROOFING, ServiceStatus.FREE, new SimpleDateFormat("dd/MM/yyyy").parse("27/11/2021"))
    );
    private List<Order> emptyOrders = new ArrayList<>();
    private String sortBy = "id_service";
    private String direction = "ASC";

    private Order invalidOrder = new Order("change roof", "esenina 1", ServiceType.ROOFING, ServiceStatus.FREE, null);
    private Order order = new Order("change roof", "esenina 1", ServiceType.ROOFING, ServiceStatus.FREE, new SimpleDateFormat("dd/MM/yyyy").parse("27/11/2021"));
    private Registration registrationInfo = new Registration("arina", "artiukevich", "arina@gmail.com", "Minsk", "arinka", "arina", "arina", Gender.FEMALE, UserRole.CLIENT);

    public OrderServiceImplTest() throws ParseException {
    }
    // endregion

    @Test(expected = ServiceException.class)
    public void testGetAllServices_validationExceptionNullParameter() throws ServiceException {
        Page<Order> orderPageRequest = null;
        orderService.getAllServices(orderPageRequest);
    }

    @Test
    public void testGetAllServices_positive() throws ServiceException, DaoException {
        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, sortBy, direction);
        boolean isAdded = false;
        Long idClient = 0L;
        userDao.addUser(registrationInfo);
        idClient = userDao.getUserByLogin(registrationInfo.getLogin()).getIdUser();
        isAdded = orderService.addServiceOrder(order, idClient);
        order.setIdService(orderDao.findOrdersByIdUser(orderPageRequest, idClient).getElements().get(0).getIdService());
        order.setIdClient(idClient);
        assertEquals(Boolean.TRUE, isAdded);
        Page<Order> actualOrderPageResult = orderService.getAllServices(orderPageRequest);
        assertTrue(actualOrderPageResult.getElements().contains(order));
        orderDao.deleteByIdClient(idClient);
        userDao.deleteUserByLogin(registrationInfo.getLogin());
    }

    @Test
    public void testGetAllServices_daoException() {
        Page<Order> orderPageRequest = new Page<>(-123, totalElements, limit, emptyOrders, sortBy, direction);
        final PSQLException psqlException = new PSQLException("Page number is invalid.", PSQLState.DATA_ERROR);

        ServiceException actual = null;
        try {
            orderService.getAllServices(orderPageRequest);
        } catch (ServiceException e) {
            actual = e;
        }
        // assert
        assertEquals(new ServiceException(new DaoException(psqlException.getMessage()).getMessage()).getMessage(), actual.getMessage());
    }

    @Test
    public void testAddServiceOrder_positive() throws DaoException, ServiceException {
        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, sortBy, direction);
        boolean isAdded = false;
        Long idClient = 0L;
        int elementsSize = 0;
        int actualElementsSize = 0;

        elementsSize = orderService.getAllServices(orderPageRequest).getElements().size();
        userDao.addUser(registrationInfo);
        idClient = userDao.getUserByLogin(registrationInfo.getLogin()).getIdUser();
        isAdded = orderService.addServiceOrder(order, idClient);
        actualElementsSize = orderService.getAllServices(orderPageRequest).getElements().size();


        assertEquals(1, (actualElementsSize - elementsSize));
        assertEquals(Boolean.TRUE, isAdded);

        orderDao.deleteByIdClient(idClient);
        userDao.deleteUserByLogin(registrationInfo.getLogin());

    }

    @Test
    public void testAddServiceOrder_ServiceException() throws DaoException {
        final PSQLException psqlException = new PSQLException("Date is null.", PSQLState.DATA_ERROR);
        boolean isAdded = false;
        Exception actual = null;
        try {
            userDao.addUser(registrationInfo);
            isAdded = orderService.addServiceOrder(invalidOrder, userDao.getUserByLogin(registrationInfo.getLogin()).getIdUser());

        } catch (ServiceException | DaoException e) {
            actual = e;
        }
        assertEquals(Boolean.FALSE, isAdded);
        assertEquals(new ServiceException(psqlException.getMessage()).getMessage(), actual.getMessage());

        userDao.deleteUserByLogin(registrationInfo.getLogin());

    }


//    @Test
//    public void testGetAllServices_positive() throws ServiceException, ParseException {
//        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, sortBy, direction);
//        Page<Order> expectedOrderPageRequest = new Page<>(pageNumber, totalElements, limit, orders, sortBy, direction);
//
//        Page<Order> actualOrderPageResult = orderService.getAllServices(orderPageRequest); // todo delete возвращаются реальные данные
//        /*
//        1. add new orders
//        2. getAllServices() -> actualSecondResult
//        3. assert THAT actualSecondResult-elements minus actualOrderPageResult-elements ARE new_orders that we have saved before
//         */
//        assertEquals(expectedOrderPageRequest, actualOrderPageResult);
//        for (int i = 0; i < expectedOrderPageRequest.getElements().size(); i++) {
//            assertEquals(expectedOrderPageRequest.getElements().get(i).getOrderCreationDate(), actualOrderPageResult.getElements().get(i).getOrderCreationDate());
//        }
//
//    }
}