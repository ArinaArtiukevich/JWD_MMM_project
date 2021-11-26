package com.jwd.dao.repository.impl;

import com.jwd.dao.connection.ConnectionPool;
import com.jwd.dao.connection.impl.ConnectionPoolImpl;
import com.jwd.dao.entity.Order;
import com.jwd.dao.entity.Page;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OrderDaoImpl extends AbstractDao implements OrderDao {
    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);
    private static final String DATE_FORMAT = "yyyy.MM.dd";

    public OrderDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    public boolean add(Order order, Long idClient) throws DaoException {
        logger.info("Start add(Service service). Id = " + order.getIdService());
        PreparedStatement statement = null;
        Connection connection = null;
        boolean isAdded = false;
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        try {
            connection = getConnection(false);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("orders.insert.order"));

            statement.setLong(1, idClient);
            statement.setString(2, order.getDescription());
            statement.setString(3, order.getAddress());
            statement.setString(4, order.getServiceType().toString());
            statement.setString(5, order.getStatus().toString());
            statement.setString(6, format.format(order.getOrderCreationDate()));
            int affectedRows = statement.executeUpdate();
            connection.commit();
            if (affectedRows <= 0) {
                logger.error("An order was not added into orders.");
                throw new DaoException("An order was not added into orders.");
            } else {
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

    @Override
    public Page<Order> getServiceList(Page<Order> daoOrderPage) throws DaoException {
        logger.info("Start Page<Order> getServiceList(Page<Order> daoOrderPage).");
        final int offset = (daoOrderPage.getPageNumber() - 1) * daoOrderPage.getLimit();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PreparedStatement statement_total_elements = null;
        ResultSet resultSet_total_elements = null;
        Page<Order> page = new Page<>();
        try {
            connection = getConnection(false);
            statement_total_elements = connection.prepareStatement(DataBaseConfig.getQuery("orders.number.all"));
            resultSet_total_elements = statement_total_elements.executeQuery();

            final String findPageOrderedQuery =
                    String.format(DataBaseConfig.getQuery("page.filter.sorted.all"), daoOrderPage.getSortBy(), daoOrderPage.getDirection());
            statement = connection.prepareStatement(findPageOrderedQuery);
            statement.setInt(1, daoOrderPage.getLimit());
            statement.setInt(2, offset);
            resultSet = statement.executeQuery();
            connection.commit();
            page = getOrderPage(daoOrderPage, resultSet, resultSet_total_elements);

        } catch (ParseException e) {
            logger.error("Invalid date format");
            throw new DaoException(e);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        finally {
            close(resultSet, resultSet_total_elements);
            close(statement, statement_total_elements);
            retrieve(connection);
        }
        return page;
    }

    @Override
    public Page<Order> getOrdersByServiceType(Page<Order> daoOrderPage, ServiceType serviceType) throws DaoException {
        logger.info("Start Page<Order> getOrdersByServiceType(Page<Order> orderPageRequest, ServiceType serviceType).");
        final int offset = (daoOrderPage.getPageNumber() - 1) * daoOrderPage.getLimit();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PreparedStatement statement_total_elements = null;
        ResultSet resultSet_total_elements = null;
        Page<Order> page = new Page<>();
        try {
            connection = getConnection(false);
            statement_total_elements = connection.prepareStatement(DataBaseConfig.getQuery("orders.number.by.serviceType"));
            statement_total_elements.setString(1, serviceType.toString());
            resultSet_total_elements = statement_total_elements.executeQuery();

            final String findPageOrderedQuery =
                    String.format(DataBaseConfig.getQuery("page.filter.sorted.by.serviceType"), daoOrderPage.getSortBy(), daoOrderPage.getDirection());
            statement = connection.prepareStatement(findPageOrderedQuery);
            statement.setString(1, serviceType.toString());
            statement.setInt(2, daoOrderPage.getLimit());
            statement.setInt(3, offset);
            resultSet = statement.executeQuery();
            connection.commit();
            page = getOrderPage(daoOrderPage, resultSet, resultSet_total_elements);

        } catch (ParseException e) {
            logger.error("Invalid date format");
            throw new DaoException(e);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        finally {
            close(resultSet, resultSet_total_elements);
            close(statement, statement_total_elements);
            retrieve(connection);
        }
        return page;
    }

    @Override
    public Page<Order> findOrdersByIdUser(Page<Order> daoOrderPage, Long idUser) throws DaoException {
        logger.info("Start Page<Order> findOrdersByIdUser(Page<Order> daoOrderPage, Long idUser).");
        final int offset = (daoOrderPage.getPageNumber() - 1) * daoOrderPage.getLimit();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PreparedStatement statement_total_elements = null;
        ResultSet resultSet_total_elements = null;
        Page<Order> page = new Page<>();

        List<Order> list = new ArrayList<>();
        if(idUser <= 0) {
            throw new DaoException("Invalid idClient");
        }
        try {
            connection = getConnection(false);
            statement_total_elements = connection.prepareStatement(DataBaseConfig.getQuery("orders.number.all.by.idUser"));
            statement_total_elements.setLong(1, idUser);
            resultSet_total_elements = statement_total_elements.executeQuery();

            final String findPageOrderedQuery =
                    String.format(DataBaseConfig.getQuery("page.filter.sorted.by.idUser"), daoOrderPage.getSortBy(), daoOrderPage.getDirection());
            statement = connection.prepareStatement(findPageOrderedQuery);
            statement.setLong(1, idUser);
            statement.setInt(2, daoOrderPage.getLimit());
            statement.setInt(3, offset);

            logger.info("Query : {}", statement.toString());
            resultSet = statement.executeQuery();
            connection.commit();

            page = getOrderPage(daoOrderPage, resultSet, resultSet_total_elements);

        } catch (ParseException e) {
            logger.error("Invalid date format");
            throw new DaoException(e);
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        finally {
            close(resultSet, resultSet_total_elements);
            close(statement, statement_total_elements);
            retrieve(connection);
        }
        return page;
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
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
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
                order.setOrderCreationDate(format.parse(resultSet.getString("order_creation_date")));
            }
        } catch (ParseException e) {
            logger.error("Invalid date format");
            throw new DaoException(e);
        } catch(SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return order;
    }

    @Override
    public boolean takeOrder(Long idOrder, Long idWorker, ServiceStatus serviceStatus) throws DaoException {
        logger.info("Start boolean takeOrder(Long idOrder, Long idWorker).");
        Connection connection = null;
        PreparedStatement statement_update = null;
        PreparedStatement statement_set_status = null;
        boolean isTaken = false;
        if(idOrder <= 0 || idWorker <= 0) {
            throw new DaoException("Invalid id");
        }
        try {
            connection = getConnection(false);
            statement_update = connection.prepareStatement(DataBaseConfig.getQuery("orders.update.worker"));
            statement_update.setLong(1, idWorker);
            statement_update.setLong(2, idOrder);
            int affectedRowsUpdate= statement_update.executeUpdate();

            statement_set_status = connection.prepareStatement(DataBaseConfig.getQuery("orders.update.order.status"));
            statement_set_status.setString(1, serviceStatus.toString());
            statement_set_status.setLong(2, idOrder);
            int affectedRowsSetStatus = statement_set_status.executeUpdate();
            connection.commit();

            if (affectedRowsUpdate == 1 && affectedRowsSetStatus == 1) {
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
            close(statement_update, statement_set_status);
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
    public Page<Order> getOrdersByWorkerId(Page<Order> daoOrderPage, Long idWorker) throws DaoException {
        logger.info("Start List<Order> getOrdersByWorkerId(Long idWorker).");
        final int offset = (daoOrderPage.getPageNumber() - 1) * daoOrderPage.getLimit();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PreparedStatement statement_total_elements = null;
        ResultSet resultSet_total_elements = null;
        Page<Order> page = new Page<>();
        if(idWorker <= 0) {
            throw new DaoException("Invalid idWorker");
        }
        try {
            connection = getConnection(false);
            statement_total_elements = connection.prepareStatement(DataBaseConfig.getQuery("orders.number.responses.by.idWorker"));
            statement_total_elements.setLong(1, idWorker);
            resultSet_total_elements = statement_total_elements.executeQuery();

            final String findPageOrderedQuery =
                    String.format(DataBaseConfig.getQuery("page.filter.sorted.responses.by.idWorker"), daoOrderPage.getSortBy(), daoOrderPage.getDirection());
            statement = connection.prepareStatement(findPageOrderedQuery);
            statement.setLong(1, idWorker);
            statement.setInt(2, daoOrderPage.getLimit());
            statement.setInt(3, offset);
            resultSet = statement.executeQuery();
            connection.commit();

            page = getResponsePage(daoOrderPage, resultSet, resultSet_total_elements);
        } catch (ParseException e) {
            logger.error("Invalid date format");
            throw new DaoException(e);
        } catch(SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return page;
    }

    @Override
    public Page<Order> getOrdersResponseByClientId(Page<Order> daoOrderPage, Long idClient) throws DaoException {
        logger.info("Start Page<Order> getOrdersResponseByClientId(Page<Order> daoOrderPage, Long idClient).");
        final int offset = (daoOrderPage.getPageNumber() - 1) * daoOrderPage.getLimit();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PreparedStatement statement_total_elements = null;
        ResultSet resultSet_total_elements = null;
        Page<Order> page = new Page<>();
        if(idClient <= 0) {
            throw new DaoException("Invalid idClient");
        }
        try {
            connection = getConnection(false);
            statement_total_elements = connection.prepareStatement(DataBaseConfig.getQuery("orders.number.responses.by.idClient"));
            statement_total_elements.setLong(1, idClient);
            resultSet_total_elements = statement_total_elements.executeQuery();

            final String findPageOrderedQuery =
                    String.format(DataBaseConfig.getQuery("page.filter.sorted.responses.by.idClient"), daoOrderPage.getSortBy(), daoOrderPage.getDirection());
            statement = connection.prepareStatement(findPageOrderedQuery);
            statement.setLong(1, idClient);
            statement.setInt(2, daoOrderPage.getLimit());
            statement.setInt(3, offset);
            resultSet = statement.executeQuery();
            connection.commit();

            page = getResponsePage(daoOrderPage, resultSet, resultSet_total_elements);
        } catch (ParseException e) {
            logger.error("Invalid date format");
            throw new DaoException(e);
        } catch(SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        finally {
            close(resultSet, resultSet_total_elements);
            close(statement, statement_total_elements);
            retrieve(connection);
        }
        return page;
    }

    @Override
    public Page<Order> getOrdersByServiceStatus(Page<Order> daoOrderPage, ServiceStatus serviceStatus, Long idClient) throws DaoException {
        logger.info("Start Page<Order> getOrdersByServiceStatus(Page<Order> daoOrderPage, ServiceStatus serviceStatus).");
        final int offset = (daoOrderPage.getPageNumber() - 1) * daoOrderPage.getLimit();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PreparedStatement statement_total_elements = null;
        ResultSet resultSet_total_elements = null;
        Page<Order> page = new Page<>();
        try {
            connection = getConnection(false);
            statement_total_elements = connection.prepareStatement(DataBaseConfig.getQuery("orders.number.by.serviceStatus.and.id_client"));
            statement_total_elements.setString(1, serviceStatus.toString());
            statement_total_elements.setLong(2, idClient);
            resultSet_total_elements = statement_total_elements.executeQuery();

            final String findPageOrderedQuery =
                    String.format(DataBaseConfig.getQuery("page.filter.by.id_client.sorted.by.serviceStatus"), daoOrderPage.getSortBy(), daoOrderPage.getDirection());
            statement = connection.prepareStatement(findPageOrderedQuery);
            statement.setString(1, serviceStatus.toString());
            statement.setLong(2, idClient);
            statement.setInt(3, daoOrderPage.getLimit());
            statement.setInt(4, offset);
            resultSet = statement.executeQuery();
            connection.commit();
            page = getOrderPage(daoOrderPage, resultSet, resultSet_total_elements);

        } catch (ParseException e) {
            logger.error("Invalid date format");
            throw new DaoException("Invalid date format");
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        finally {
            close(resultSet, resultSet_total_elements);
            close(statement, statement_total_elements);
            retrieve(connection);
        }
        return page;
    }

    @Override
    public boolean deleteById(Long idOrder) throws DaoException {
        logger.info("Start boolean deleteById(Long idOrder). IdOrder: " + idOrder);
        PreparedStatement statement = null;
        Connection connection = null;
        boolean isDeleted = false;
        try {
            connection = getConnection(false);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("orders.delete.order"));
            statement.setLong(1, idOrder);
            int affectedRows = statement.executeUpdate();
            connection.commit();
            if (affectedRows <= 0) {
                logger.error("An order was not deleted.");
                throw new DaoException("An order was not deleted.");
            } else {
                logger.info("An order was deleted.");
                isDeleted = true;
            }
        }
        catch(SQLException e) {
            logger.error(e);
            throw new DaoException("An order was not deleted.");
        }
        finally {
            close(statement);
            retrieve(connection);
        }
        return isDeleted;
    }

    @Override
    public ServiceStatus getServiceStatusById(Long idOrder) throws DaoException {
        logger.info("Start ServiceStatus getServiceStatusById(Long idOrder)");
        PreparedStatement statement = null;
        Connection connection = null;
        ServiceStatus serviceStatus = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(false);
            statement = connection.prepareStatement(DataBaseConfig.getQuery("orders.find.service.status.by.id"));
            statement.setLong(1, idOrder);
            resultSet = statement.executeQuery();
            connection.commit();
            while (resultSet.next()) {
                serviceStatus = ServiceStatus.valueOf(resultSet.getString(1).toUpperCase());
            }

        }
        catch(SQLException e) {
            logger.error(e);
            throw new DaoException("An order was not deleted.");
        }
        finally {
            close(resultSet);
            close(statement);
            retrieve(connection);
        }
        return serviceStatus;
    }

    private Page<Order> getOrderPage(Page<Order> daoOrderPage, ResultSet resultSet, ResultSet resultSet_total_elements) throws SQLException, ParseException {
        Page<Order> page = new Page<>();
        long totalElements = 0L;
        while (resultSet_total_elements.next()) {
            totalElements = resultSet_total_elements.getLong(1);
        }
        List<Order> orders = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        while (resultSet.next()) {
            long idService = resultSet.getLong(1);
            long idClient = resultSet.getLong(2);
            String description = resultSet.getString(3);
            String address = resultSet.getString(4);
            ServiceType service_type = ServiceType.valueOf(resultSet.getString(5).toUpperCase());
            ServiceStatus service_status = ServiceStatus.valueOf(resultSet.getString(6).toUpperCase());
            Date orderCreationDate = format.parse(resultSet.getString(8));
            orders.add(new Order(idService, idClient, description, address, service_type, service_status, orderCreationDate));
        }
        page = setPageResult(daoOrderPage, totalElements, orders);
        return page;
    }

    private Page<Order> getResponsePage(Page<Order> daoOrderPage, ResultSet resultSet, ResultSet resultSet_total_elements) throws SQLException, ParseException {
        Page<Order> page = new Page<>();
        long totalElements = 0L;
        while (resultSet_total_elements.next()) {
            totalElements = resultSet_total_elements.getLong(1);
        }
        List<Order> orders = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        while (resultSet.next()) {
            long idService = resultSet.getLong(1);
            long idClient = resultSet.getLong(2);
            String description = resultSet.getString(3);
            String address = resultSet.getString(4);
            ServiceType service_type = ServiceType.valueOf(resultSet.getString(5).toUpperCase());
            ServiceStatus service_status = ServiceStatus.valueOf(resultSet.getString(6).toUpperCase());
            Long idWorler = resultSet.getLong(7);
            Date orderCreationDate = format.parse(resultSet.getString(8));
            orders.add(new Order(idService, idClient, description, address, service_type, service_status, orderCreationDate, idWorler));
        }
        page = setPageResult(daoOrderPage, totalElements, orders);
        return page;
    }
}
