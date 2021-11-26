package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.Registration;
import com.jwd.dao.entity.User;
import com.jwd.dao.entity.enums.Gender;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.dao.entity.enums.UserRole;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.OrderDao;
import com.jwd.dao.repository.UserDao;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.validator.ServiceValidator;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class OrderServiceImplUnitTest {

    // mock
    private OrderDao orderDao = Mockito.mock(OrderDao.class);
    // testing class
    private ServiceValidator serviceValidator = new ServiceValidator();
    private OrderService orderService = new OrderServiceImpl(orderDao, serviceValidator);

    // captors
    private ArgumentCaptor<Page<Order>> pageArgumentCaptor = ArgumentCaptor.forClass(Page.class);

    // region parameters
    private int pageNumber = 1;
    private long totalElements = 10;
    private int limit = 3;
    private List<Order> orders = Arrays.asList(
            new Order(1L, 1L, "change roof", "esenina 1", ServiceType.ROOFING, ServiceStatus.FREE, new Date()),
            new Order(2L, 1L, "paint wall", "esenina 5", ServiceType.PAINTING, ServiceStatus.FREE, new Date()),
            new Order(3L, 1L, "paint door", "esenina 7", ServiceType.PAINTING, ServiceStatus.FREE, new Date()),
            new Order(4L, 1L, "paint house", "esenina 9", ServiceType.PAINTING, ServiceStatus.FREE, new Date())
    );
    private List<Order> ordersPainting = Arrays.asList(
            new Order(2L, 1L, "paint wall", "esenina 5", ServiceType.PAINTING, ServiceStatus.FREE, new Date()),
            new Order(3L, 1L, "paint door", "esenina 7", ServiceType.PAINTING, ServiceStatus.FREE, new Date())
    );
    private List<Order> emptyOrders = new ArrayList<>();
    private Order filter = new Order();
    private String sortBy = "description";
    private String direction = "ASC";

    private Long idUser = 1L;
    private String firstName = "arina";
    private String lastName = "artiukevich";
    private String email = "artarina15@gmail.com";
    private String city = "minsk";
    private String login = "artarina15";
    private Gender gender = Gender.FEMALE;
    private UserRole userRole = UserRole.CLIENT;
    private Long idWorker = 2L;

    private String description = "change socket";
    private String address = "esenina 2";
    private ServiceType serviceType = ServiceType.ELECTRICAL;
    private ServiceStatus status = ServiceStatus.FREE;
    private Date orderCreationDate = new Date();
    // endregion

    @Test(expected = ServiceException.class)
    public void testGetAllServices_validationExceptionNullParameter() throws ServiceException {
        Page<Order> orderPageRequest = null;

        orderService.getAllServices(orderPageRequest);
    }

    @Test
    public void testGetAllServices_positive() throws ServiceException, DaoException {
        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, filter, sortBy, direction);
        // Page<Order> changedOrderPageRequest = new Page<>(pageNumber, totalElements, 111, orders, filter, sortBy, direction);
        Page<Order> orderPage = new Page<>(pageNumber, totalElements, limit, orders, filter, sortBy, direction);
        Page<Order> expectedOrderPageRequest = new Page<>(pageNumber, totalElements, limit, orders, filter, sortBy, direction);

        when(orderDao.getServiceList(orderPageRequest)).thenReturn(orderPage);

        Page<Order> actualOrderPageResult = orderService.getAllServices(orderPageRequest);
        // Page<Order> changedOrderPageResult = orderService.getAllServices(changedOrderPageRequest); NULL

        verify(orderDao, times(1)).getServiceList(pageArgumentCaptor.capture()); // todo delete что реально передается orderPage = orderDao.getServiceList(orderPageRequest); in OrderServiceImpl
        assertEquals(orderPageRequest, pageArgumentCaptor.getValue()); // первое значение - ожидаемое значение в getServiceList()
        assertEquals(expectedOrderPageRequest, actualOrderPageResult);
    }

    @Test
    public void testGetAllServices_daoException() throws DaoException {
        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, filter, sortBy, direction);
        final DaoException daoException = new DaoException("message");

        when(orderDao.getServiceList(orderPageRequest)).thenThrow(daoException);

        ServiceException actual = null;
        try {
            orderService.getAllServices(orderPageRequest);
        } catch (ServiceException e) {
            actual = e;
        }

        // assert
        verify(orderDao, times(1)).getServiceList(pageArgumentCaptor.capture());
        assertEquals(new ServiceException(daoException).getMessage(), actual.getMessage());
    }

    @Test
    public void testAddServiceOrder_positive() throws DaoException, ServiceException {
        User user = new User(idUser, firstName, lastName, email, city, login, gender, userRole);
        Order order = new Order(description, address, serviceType, status, orderCreationDate);
        boolean expectedResult = true;

        doReturn(Boolean.TRUE).when(orderDao).add(order, user.getIdUser());

        boolean actualResult = orderService.addServiceOrder(order, user.getIdUser());
        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = ServiceException.class)
    public void testAddServiceOrder_daoException() throws DaoException, ServiceException {
        User user = new User(idUser, firstName, lastName, email, city, login, gender, userRole);
        Order order = null;

        when(orderDao.add(order, user.getIdUser())).thenThrow(new DaoException("Object is null."));
        orderService.addServiceOrder(order, user.getIdUser());
    }

    @Test
    public void testGetOrdersByServiceType_positive() throws ServiceException, DaoException {
        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, filter, sortBy, direction);
        ServiceType serviceTypeRequest = ServiceType.PAINTING;
        Page<Order> orderPage = new Page<>(pageNumber, totalElements, limit, ordersPainting, filter, sortBy, direction);
        Page<Order> expectedOrderPageRequest = new Page<>(pageNumber, totalElements, limit, ordersPainting, filter, sortBy, direction);

        when(orderDao.getOrdersByServiceType(orderPageRequest, serviceTypeRequest)).thenReturn(orderPage);

        Page<Order> actualOrderPageResult = orderService.getOrdersByServiceType(orderPageRequest, serviceTypeRequest);

        assertEquals(expectedOrderPageRequest, actualOrderPageResult);
    }

    @Test
    public void testGetOrdersByServiceType_daoException() throws DaoException {
        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, filter, "sortBy", direction);
        final DaoException daoException = new DaoException("Invalid parameters.");
        ServiceType serviceTypeRequest = ServiceType.PAINTING;

        when(orderDao.getOrdersByServiceType(orderPageRequest, serviceTypeRequest)).thenThrow(daoException);

        ServiceException actual = null;
        try {
            orderService.getOrdersByServiceType(orderPageRequest, serviceTypeRequest);
        } catch (ServiceException e) {
            actual = e;
        }
        assertEquals(new ServiceException(daoException).getMessage(), actual.getMessage());
    }
// todo
//    @Test
//    public void testTakeOrder_positive() throws ServiceException, DaoException {
//        Order order = new Order(description, address, serviceType, status, orderCreationDate);
//        boolean expectedResult = true;
//        orderService.addServiceOrder(order, idUser);
//
//        doReturn(Boolean.TRUE).when(orderDao).takeOrder(order.getIdService(), idWorker);
//
//        boolean actualResult = orderService.takeOrder(order.getIdService(), idWorker);
//        assertEquals(expectedResult, actualResult);
//    }

}