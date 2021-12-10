package com.jwd.service.serviceLogic.impl;

import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
import com.jwd.dao.entity.User;
import com.jwd.dao.entity.enumType.Gender;
import com.jwd.dao.entity.enumType.ServiceStatus;
import com.jwd.dao.entity.enumType.ServiceType;
import com.jwd.dao.entity.enumType.UserRole;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.OrderDao;
import com.jwd.dao.repository.UserDao;
import com.jwd.service.exception.ServiceException;
import com.jwd.service.serviceLogic.OrderService;
import com.jwd.service.validator.ServiceValidator;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.jwd.dao.entity.enumType.ServiceStatus.FREE;
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
            new Order(4L, 3L, "paint house", "esenina 9", ServiceType.PAINTING, ServiceStatus.FREE, new Date()),
            new Order(5L, 1L, "change sockets", "esenina 10", ServiceType.ELECTRICAL, ServiceStatus.APPROVED, new Date()),
            new Order(6L, 1L, "change socket", "esenina 12", ServiceType.ELECTRICAL, ServiceStatus.APPROVED, new Date())
    );
    private List<Order> ordersPainting = Arrays.asList(
            new Order(2L, 1L, "paint wall", "esenina 5", ServiceType.PAINTING, ServiceStatus.FREE, new Date()),
            new Order(3L, 1L, "paint door", "esenina 7", ServiceType.PAINTING, ServiceStatus.FREE, new Date()),
            new Order(4L, 3L, "paint house", "esenina 9", ServiceType.PAINTING, ServiceStatus.APPROVED, new Date())
    );
    private List<Order> ordersUser = Arrays.asList(
            new Order(1L, 1L, "change roof", "esenina 1", ServiceType.ROOFING, ServiceStatus.FREE, new Date()),
            new Order(2L, 1L, "paint wall", "esenina 5", ServiceType.PAINTING, ServiceStatus.FREE, new Date()),
            new Order(3L, 1L, "paint door", "esenina 7", ServiceType.PAINTING, ServiceStatus.FREE, new Date())
    );
    private List<Order> ordersApproved = Arrays.asList(
            new Order(5L, 1L, "change sockets", "esenina 10", ServiceType.ELECTRICAL, ServiceStatus.APPROVED, new Date()),
            new Order(6L, 1L, "change socket", "esenina 12", ServiceType.ELECTRICAL, ServiceStatus.APPROVED, new Date())
    );
    private List<Order> emptyOrders = new ArrayList<>();
    private String sortBy = "description";
    private String direction = "ASC";

    private Long idOrder = 1L;
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
        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, sortBy, direction);
        Page<Order> orderPage = new Page<>(pageNumber, totalElements, limit, orders, sortBy, direction);
        Page<Order> expectedOrderPageRequest = new Page<>(pageNumber, totalElements, limit, orders, sortBy, direction);

        when(orderDao.getServiceList(orderPageRequest)).thenReturn(orderPage);

        Page<Order> actualOrderPageResult = orderService.getAllServices(orderPageRequest);

        verify(orderDao, times(1)).getServiceList(pageArgumentCaptor.capture());
        assertEquals(orderPageRequest, pageArgumentCaptor.getValue());
        assertEquals(expectedOrderPageRequest, actualOrderPageResult);
    }

    @Test
    public void testGetAllServices_daoException() throws DaoException {
        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, sortBy, direction);
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
        assertEquals(orderPageRequest, pageArgumentCaptor.getValue());
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
        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, sortBy, direction);
        ServiceType serviceTypeRequest = ServiceType.PAINTING;
        Page<Order> orderPage = new Page<>(pageNumber, totalElements, limit, ordersPainting, sortBy, direction);
        Page<Order> expectedOrderPageRequest = new Page<>(pageNumber, totalElements, limit, ordersPainting, sortBy, direction);

        when(orderDao.getOrdersByServiceType(orderPageRequest, serviceTypeRequest)).thenReturn(orderPage);

        Page<Order> actualOrderPageResult = orderService.getOrdersByServiceType(orderPageRequest, serviceTypeRequest.toString());

        assertEquals(expectedOrderPageRequest, actualOrderPageResult);
    }

    @Test
    public void testGetOrdersByServiceType_daoException() throws DaoException {
        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, "sortBy", direction);
        final DaoException daoException = new DaoException("Invalid parameters.");
        ServiceType serviceTypeRequest = ServiceType.PAINTING;

        when(orderDao.getOrdersByServiceType(orderPageRequest, serviceTypeRequest)).thenThrow(daoException);

        ServiceException actual = null;
        try {
            orderService.getOrdersByServiceType(orderPageRequest, serviceTypeRequest.toString());
        } catch (ServiceException e) {
            actual = e;
        }
        assertEquals(new ServiceException(daoException).getMessage(), actual.getMessage());
    }

    @Test
    public void testGetOrdersByUserId_positive() throws ServiceException, DaoException {
        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, sortBy, direction);
        Page<Order> orderPage = new Page<>(pageNumber, totalElements, limit, ordersUser, sortBy, direction);
        Page<Order> expectedOrderPageRequest = new Page<>(pageNumber, totalElements, limit, ordersUser, sortBy, direction);

        when(orderDao.findOrdersByIdUser(orderPageRequest, idUser)).thenReturn(orderPage);

        Page<Order> actualOrderPageResult = orderService.getOrdersByUserId(orderPageRequest, idUser);

        verify(orderDao, times(1)).findOrdersByIdUser(pageArgumentCaptor.capture(), eq(idUser));
        assertEquals(orderPageRequest, pageArgumentCaptor.getValue());
        assertEquals(expectedOrderPageRequest, actualOrderPageResult);
    }

    @Test
    public void testGetOrdersByUserId_daoException() throws DaoException {
        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, sortBy, null);
        final DaoException daoException = new DaoException("Direction parameter is not available.");

        when(orderDao.findOrdersByIdUser(orderPageRequest, idUser)).thenThrow(daoException);

        ServiceException actual = null;
        try {
            orderService.getOrdersByUserId(orderPageRequest, idUser);
        } catch (ServiceException e) {
            actual = e;
        }
        verify(orderDao, times(0)).findOrdersByIdUser(pageArgumentCaptor.capture(), eq(idUser));
        assertEquals(new ServiceException(daoException.getMessage()).getMessage(), actual.getMessage());
    }


    @Test
    public void testGetOrderById_positive() throws ServiceException, DaoException {
        Order order = new Order(idOrder, idUser, description, address, ServiceType.ROOFING, ServiceStatus.FREE, new Date());
        Order expectedOrder = new Order(idOrder, idUser, description, address, ServiceType.ROOFING, ServiceStatus.FREE, new Date());

        when(orderDao.findOrderById(idOrder)).thenReturn(order);

        Order actualOrder = orderService.getOrderById(idOrder);

        verify(orderDao, times(1)).findOrderById(eq(idOrder));
        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void testGetOrderById_serviceException() throws DaoException {
        Long idOrder = -1L;
        final DaoException daoException = new DaoException("Id was not found.");

        when(orderDao.findOrderById(idOrder)).thenThrow(daoException);

        ServiceException actual = null;
        try {
            orderService.getOrderById(idOrder);
        } catch (ServiceException e) {
            actual = e;
        }
        assertEquals(daoException.getMessage(), actual.getMessage());
    }

    @Test
    public void testTakeOrder_positive() throws ServiceException, DaoException {
        Boolean expectedResult = true;

        when(orderDao.getServiceStatusById(idOrder)).thenReturn(ServiceStatus.FREE);
        when(orderDao.takeOrder(idOrder, idWorker, ServiceStatus.IN_PROCESS)).thenReturn(expectedResult);

        Boolean actualResult = orderService.takeOrder(idOrder, idWorker, ServiceStatus.IN_PROCESS);

        verify(orderDao, times(1)).takeOrder(eq(idOrder), eq(idWorker), eq(ServiceStatus.IN_PROCESS));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testTakeOrder_serviceException() throws DaoException {
        Long idOrder = -1L;
        final DaoException daoException = new DaoException("Id was not found.");

        when(orderDao.getServiceStatusById(idOrder)).thenThrow(daoException);

        ServiceException actual = null;
        try {
            orderService.getOrderById(idOrder);
        } catch (ServiceException e) {
            actual = e;
        }
        verify(orderDao, times(0)).takeOrder(eq(idOrder), eq(idWorker), eq(ServiceStatus.IN_PROCESS));
        assertEquals(daoException.getMessage(), actual.getMessage());
    }

    @Test
    public void testSetDoneOrderStatus_positive() throws ServiceException, DaoException {
        Boolean expectedResult = true;

        when(orderDao.findWorkerRoleByIdOrder(idOrder)).thenReturn(UserRole.WORKER);
        when(orderDao.getServiceStatusById(idOrder)).thenReturn(ServiceStatus.IN_PROCESS);
        when(orderDao.setOrderStatus(idOrder, ServiceStatus.DONE)).thenReturn(expectedResult);

        Boolean actualResult = orderService.setDoneOrderStatus(idOrder, idWorker);

        verify(orderDao, times(1)).setOrderStatus(eq(idOrder), eq(ServiceStatus.DONE));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSetDoneOrderStatus_serviceException() throws DaoException {
        Long idOrder = -1L;
        final DaoException daoException = new DaoException("Id was not found.");

        when(orderDao.setOrderStatus(idOrder, ServiceStatus.DONE)).thenThrow(daoException);

        ServiceException actual = null;
        try {
            orderService.setDoneOrderStatus(idOrder, idWorker);
        } catch (ServiceException e) {
            actual = e;
        }
        verify(orderDao, times(0)).setOrderStatus(eq(idOrder), eq(ServiceStatus.DONE));
        assertEquals(daoException.getMessage(), actual.getMessage());
    }

    @Test
    public void testSetApproveOrderStatus_positive() throws ServiceException, DaoException {
        Boolean expectedResult = true;

        when(orderDao.findClientRoleByIdOrder(idOrder)).thenReturn(UserRole.CLIENT);
        when(orderDao.getServiceStatusById(idOrder)).thenReturn(ServiceStatus.DONE);
        when(orderDao.setOrderStatus(idOrder, ServiceStatus.APPROVED)).thenReturn(expectedResult);

        Boolean actualResult = orderService.setApproveOrderStatus(idOrder, idUser);

        verify(orderDao, times(1)).setOrderStatus(eq(idOrder), eq(ServiceStatus.APPROVED));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testSetApproveOrderStatus_serviceException() throws DaoException {
        Long idOrder = -1L;
        final DaoException daoException = new DaoException("Id was not found.");

        when(orderDao.setOrderStatus(idOrder, ServiceStatus.APPROVED)).thenThrow(daoException);

        ServiceException actual = null;
        try {
            orderService.setApproveOrderStatus(idOrder, idWorker);
        } catch (ServiceException e) {
            actual = e;
        }
        verify(orderDao, times(0)).setOrderStatus(eq(idOrder), eq(ServiceStatus.APPROVED));
        assertEquals(daoException.getMessage(), actual.getMessage());
    }

    @Test
    public void testGetOrdersByServiceStatus_positive() throws ServiceException, DaoException {
        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, sortBy, direction);
        ServiceStatus serviceStatus = ServiceStatus.APPROVED;
        Page<Order> orderPage = new Page<>(pageNumber, totalElements, limit, ordersApproved, sortBy, direction);
        Page<Order> expectedOrderPageRequest = new Page<>(pageNumber, totalElements, limit, ordersApproved, sortBy, direction);

        when(orderDao.getOrdersByServiceStatus(orderPageRequest, serviceStatus, idUser)).thenReturn(orderPage);

        Page<Order> actualOrderPageResult = orderService.getOrdersByServiceStatus(orderPageRequest, serviceStatus.toString(), idUser);

        assertEquals(expectedOrderPageRequest, actualOrderPageResult);
    }

    @Test
    public void testGetOrdersByServiceStatus_daoException() throws DaoException {
        Page<Order> orderPageRequest = new Page<>(pageNumber, totalElements, limit, emptyOrders, sortBy, "direction");
        final DaoException daoException = new DaoException("Invalid parameters.");
        ServiceStatus serviceStatus = ServiceStatus.APPROVED;

        when(orderDao.getOrdersByServiceStatus(orderPageRequest, serviceStatus, idUser)).thenThrow(daoException);

        ServiceException actual = null;
        try {
            orderService.getOrdersByServiceStatus(orderPageRequest, serviceStatus.toString(), idUser);
        } catch (ServiceException e) {
            actual = e;
        }
        assertEquals(new ServiceException(daoException).getMessage(), actual.getMessage());
    }

    @Test
    public void testDeleteById_positive() throws DaoException, ServiceException {
        Order order = new Order(description, address, serviceType, status, orderCreationDate);
        order.setIdClient(idUser);
        boolean expectedResult = true;

        when(orderDao.findOrderById(idOrder)).thenReturn(order);
        when(orderDao.getServiceStatusById(idOrder)).thenReturn(FREE);
        doReturn(expectedResult).when(orderDao).deleteById(idOrder);

        boolean actualResult = orderService.deleteById(idOrder, idUser);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testDeleteById_daoException() throws DaoException {
        Long idOrder = -1L;
        final DaoException daoException = new DaoException("Id was not found.");

        when(orderDao.deleteById(idOrder)).thenThrow(daoException);

        ServiceException actual = null;
        try {
            orderService.deleteById(idOrder, idUser);
        } catch (ServiceException e) {
            actual = e;
        }
        assertEquals(new ServiceException(daoException.getMessage()).getMessage(), actual.getMessage());
    }
}