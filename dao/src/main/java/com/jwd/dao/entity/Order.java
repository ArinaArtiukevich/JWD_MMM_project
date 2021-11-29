package com.jwd.dao.entity;

import com.jwd.dao.entity.enumType.ServiceStatus;
import com.jwd.dao.entity.enumType.ServiceType;

import java.util.Date;
import java.util.Objects;

public class Order {

    private Long idService;
    private Long idClient;
    private String description;
    private String address;
    private ServiceType serviceType;
    private ServiceStatus status;
    private Date orderCreationDate;
    private Long idWorker;

    public Order() {
    }

    public Order(String description, String address, ServiceType serviceType, ServiceStatus status, Date orderCreationDate) {
        this.description = description;
        this.address = address;
        this.serviceType = serviceType;
        this.status = status;
        this.orderCreationDate = orderCreationDate;
    }

    public Order(Long idService, Long idClient, String description, String address, ServiceType serviceType, ServiceStatus status, Date orderCreationDate, Long idWorker) {
        this.idService = idService;
        this.idClient = idClient;
        this.description = description;
        this.address = address;
        this.serviceType = serviceType;
        this.status = status;
        this.orderCreationDate = orderCreationDate;
        this.idWorker = idWorker;
    }

    public Order(Long idService, Long idClient, String description, String address, ServiceType serviceType, ServiceStatus status, Date orderCreationDate) {
        this.idService = idService;
        this.idClient = idClient;
        this.description = description;
        this.address = address;
        this.serviceType = serviceType;
        this.status = status;
        this.orderCreationDate = orderCreationDate;
    }

    public Long getIdService() {
        return idService;
    }

    public void setIdService(Long idService) {
        this.idService = idService;
    }

    public long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public Date getOrderCreationDate() {
        return orderCreationDate;
    }

    public void setOrderCreationDate(Date orderCreationDate) {
        this.orderCreationDate = orderCreationDate;
    }

    public Long getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(Long idWorker) {
        this.idWorker = idWorker;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(idService, order.idService) &&
                Objects.equals(idClient, order.idClient) &&
                Objects.equals(description, order.description) &&
                Objects.equals(address, order.address) &&
                serviceType == order.serviceType &&
                status == order.status &&
                Objects.equals(orderCreationDate, order.orderCreationDate) &&
                Objects.equals(idWorker, order.idWorker);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + (idService == null ? 0 : idService.hashCode());
        hash = hash * 31 + (idClient == null ? 0 : idClient.hashCode());
        hash = hash * 31 + (description == null ? 0 : description.hashCode());
        hash = hash * 31 + (address == null ? 0 : address.hashCode());
        hash = hash * 31 + (serviceType == null ? 0 : serviceType.hashCode());
        hash = hash * 31 + (status == null ? 0 : status.hashCode());
        hash = hash * 31 + (orderCreationDate == null ? 0 : orderCreationDate.hashCode());
        hash = hash * 31 + (idWorker == null ? 0 : idWorker.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return "Order{" +
                "idService=" + idService +
                ", idClient=" + idClient +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", serviceType=" + serviceType +
                ", status=" + status +
                ", orderCreationDate=" + orderCreationDate +
                ", idWorker=" + idWorker +
                '}';
    }
}
