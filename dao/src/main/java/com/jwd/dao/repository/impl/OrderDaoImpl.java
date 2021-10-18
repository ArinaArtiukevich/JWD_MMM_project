package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.DataBaseConfig;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.ServiceType;
import com.jwd.dao.exception.DaoException;
import com.jwd.dao.repository.OrderDao;
import com.jwd.dao.resources.DataBaseBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);
    private final DataBaseConfig dataBaseConfig;

    public OrderDaoImpl() {
        dataBaseConfig = new DataBaseConfig();
    }

    public boolean add(Order order, Long idClient) throws DaoException {
        logger.info("Start add(Service service). Id = " + order.getIdService());
        PreparedStatement statement = null;
        Connection connection = null;
        boolean isAdded = false;
        try {
            connection = dataBaseConfig.getConnection();
            statement = connection.prepareStatement(DataBaseBundle.getProperty("orders.insert.order"));
            statement.setLong(1, idClient);
            statement.setString(2, order.getDescription());
            statement.setString(3, order.getAddress());
            statement.setString(4, order.getServiceType().toString());
            statement.setString(5, order.getStatus().toString());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                logger.info("An order was added into orders.");
                isAdded = true;
            }
        }
        catch(SQLException e) {
            throw new DaoException("An order was not added into orders.");
        }
        finally {
            dataBaseConfig.close(connection, statement);
        }
        return isAdded;
    }


    public List<Order> getServiceList() throws DaoException {
        logger.info("Start List<Service> getServiceList().");
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = dataBaseConfig.getConnection();
            statement = connection.prepareStatement(DataBaseBundle.getProperty("orders.find.all"));
            resultSet = statement.executeQuery();

            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                long idService = resultSet.getLong(1);
                long idClient = resultSet.getLong(2);
                String description = resultSet.getString(3);
                String address = resultSet.getString(4);
                ServiceType service_type = ServiceType.valueOf(resultSet.getString(5).toUpperCase());
                ServiceStatus service_status = ServiceStatus.valueOf(resultSet.getString(6).toUpperCase());
                orders.add(new Order(idService, idClient, description, address, service_type, service_status));
            }
            return orders;
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        finally {
            dataBaseConfig.close(connection, statement, resultSet);
        }

    }

    @Override
    public List<Order> findOrdersByIdUser(Long idUser) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Order> list =new ArrayList<>();
        if(idUser <= 0) {
            throw new DaoException("Invalid idClient");
        }
        try {
            connection = dataBaseConfig.getConnection();
            statement = connection.prepareStatement(DataBaseBundle.getProperty("orders.select.by.idUser"));
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
        }
        finally {
            dataBaseConfig.close(connection, statement, resultSet);
        }
        return list;
    }

}
