package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.AbstractDao;
import com.jwd.dao.repository.OrderDao;
import com.jwd.dao.config.DataBaseConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OrderDaoImpl extends AbstractDao implements OrderDao {
    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);

    public OrderDaoImpl(ConnectionPoolImpl connectionPool) {
        super(connectionPool);
    }

    public boolean add(Order order, Long idClient) throws DaoException {
        logger.info("Start add(Service service). Id = " + order.getIdService());
        PreparedStatement statement = null;
        Connection connection = null;
        boolean isAdded = false;
        try {
            connection = getConnection(false);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("orders.insert.order"));

            statement.setLong(1, idClient);
            statement.setString(2, order.getDescription());
            statement.setString(3, order.getAddress());
            statement.setString(4, order.getServiceType().toString());
            statement.setString(5, order.getStatus().toString());
            int affectedRows = statement.executeUpdate();
            connection.commit();
            if (affectedRows > 0) {
                logger.info("An order was added into orders.");
                isAdded = true;
            }
        }
        catch(SQLException e) {
            logger.error(e);
            throw new DaoException("An order was not added into orders.");
        }
        finally {
            close(statement);
            retrieve(connection);
        }
        return isAdded;
    }

    public List<Order> getServiceList() throws DaoException {
        logger.info("Start List<Service> getServiceList().");
        PreparedStatement statement = null;
        Connection connection = null;
        List<Order> orders = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(true);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("orders.find.all"));
            resultSet = statement.executeQuery();
            orders = new ArrayList<>();
            while (resultSet.next()) {
                long idService = resultSet.getLong(1);
                long idClient = resultSet.getLong(2);
                String description = resultSet.getString(3);
                String address = resultSet.getString(4);
                ServiceType service_type = ServiceType.valueOf(resultSet.getString(5).toUpperCase());
                ServiceStatus service_status = ServiceStatus.valueOf(resultSet.getString(6).toUpperCase());
                orders.add(new Order(idService, idClient, description, address, service_type, service_status));
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return orders;
    }

    @Override
    public List<Order> findOrdersByIdUser(Long idUser) throws DaoException {
        logger.info("Start List<Order> findOrdersByIdUser(Long idUser).");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Order> list = new ArrayList<>();
        if(idUser <= 0) {
            throw new DaoException("Invalid idClient");
        }
        try {
            connection = getConnection(true);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("orders.select.by.idUser"));
            statement.setLong(1, idUser);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Order order = new Order();
                order.setIdService(resultSet.getLong("id_service"));
                order.setIdClient(resultSet.getLong("id_client"));
                order.setDescription(resultSet.getString("description"));
                order.setAddress(resultSet.getString("address"));
                order.setServiceType(ServiceType.valueOf(resultSet.getString("service_type")));
                order.setStatus(ServiceStatus.valueOf(resultSet.getString("service_status")));
                list.add(order);
            }
        }
        catch(SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return list;
    }

    @Override
    public Order findOrderById(Long idService) throws DaoException {
        logger.info("Start Order findOrderById(Long idService).");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Order order = new Order();
        if(idService <= 0) {
            throw new DaoException("Invalid idService");
        }
        try {
            connection = getConnection(true);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("orders.select.by.idService"));
            statement.setLong(1, idService);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                order = new Order();
                order.setIdService(resultSet.getLong("id_service"));
                order.setIdClient(resultSet.getLong("id_client"));
                order.setDescription(resultSet.getString("description"));
                order.setAddress(resultSet.getString("address"));
                order.setServiceType(ServiceType.valueOf(resultSet.getString("service_type")));
                order.setStatus(ServiceStatus.valueOf(resultSet.getString("service_status")));
            }
        }
        catch(SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return order;
    }

    @Override
    public boolean takeOrder(Long idOrder, Long idWorker) throws DaoException {
        logger.info("Start boolean takeOrder(Long idOrder, Long idWorker).");
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isTaken = false;
        if(idOrder <= 0 || idWorker <= 0) {
            throw new DaoException("Invalid id");
        }
        try {
            connection = getConnection(false);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("orders.update.worker"));
            statement.setLong(1, idWorker);
            statement.setLong(2, idOrder);
            int affectedRows = statement.executeUpdate();
            connection.commit();
            if (affectedRows == 1) {
                isTaken = true;
                logger.info("Order was taken.");
            } else {
                logger.info("Order was not taken.");
            }
        }
        catch(SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        finally {
            close(statement);
            retrieve(connection);
        }
        return isTaken;
    }

    @Override
    public boolean setOrderStatus(Long idOrder, ServiceStatus serviceStatus) throws DaoException {
        logger.info("Start boolean setOrderStatus(Long idOrder, ServiceStatus serviceStatus).");
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isSet = false;
        try {
            connection = getConnection(false);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("orders.update.order.status"));
            statement.setString(1, serviceStatus.toString());
            statement.setLong(2, idOrder);
            int affectedRows = statement.executeUpdate();
            connection.commit();
            if (affectedRows == 1) {
                isSet = true;
                logger.info("OrderStatus was set.");
            } else {
                logger.info("OrderStatus was not set.");
            }
        }
        catch(SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        finally {
            close(statement);
            retrieve(connection);
        }
        return isSet;
    }

    @Override
    public List<Order> getOrdersByWorkerId(Long idWorker) throws DaoException {
        logger.info("Start List<Order> getOrdersByWorkerId(Long idWorker).");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Order> list = new ArrayList<>();
        if(idWorker <= 0) {
            throw new DaoException("Invalid idWorker");
        }
        try {
            connection = getConnection(true);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("orders.select.response.by.idWorker"));
            statement.setLong(1, idWorker);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Order order = new Order();
                order.setIdService(resultSet.getLong("id_service"));
                order.setIdClient(resultSet.getLong("id_client"));
                order.setDescription(resultSet.getString("description"));
                order.setAddress(resultSet.getString("address"));
                order.setServiceType(ServiceType.valueOf(resultSet.getString("service_type")));
                order.setStatus(ServiceStatus.valueOf(resultSet.getString("service_status")));
                order.setIdWorker(idWorker);
                list.add(order);
            }
        }
        catch(SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return list;
    }

    @Override
    public List<Order> getOrdersResponseByClientId(Long idClient) throws DaoException {
        logger.info("Start List<Order> getOrdersByClientId(Long idClient).");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Order> list = new ArrayList<>();
        if(idClient <= 0) {
            throw new DaoException("Invalid idWorker");
        }
        try {
            connection = getConnection(true);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("orders.select.response.by.idClient"));
            statement.setLong(1, idClient);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Order order = new Order();
                order.setIdService(resultSet.getLong("id_service"));
                order.setIdClient(resultSet.getLong("id_client"));
                order.setDescription(resultSet.getString("description"));
                order.setAddress(resultSet.getString("address"));
                order.setServiceType(ServiceType.valueOf(resultSet.getString("service_type")));
                order.setStatus(ServiceStatus.valueOf(resultSet.getString("service_status")));
                order.setIdWorker(resultSet.getLong("id_worker"));
                list.add(order);
            }
        }
        catch(SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return list;
    }
}
