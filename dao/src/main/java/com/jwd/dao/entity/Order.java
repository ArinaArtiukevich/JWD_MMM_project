package com.jwd.dao.entity;

import com.jwd.dao.entity.enums.ServiceStatus;
import com.jwd.dao.entity.enums.ServiceType;

public class Order {

    private Long idService;
    private Long idClient;
    private String description;
    private String address;
    private ServiceType serviceType;
    private ServiceStatus status;
    private Long idWorker;

    public Order() {
    }

    public Order(String description, String address, ServiceType serviceType, ServiceStatus status) {
        this.description = description;
        this.address = address;
        this.serviceType = serviceType;
        this.status = status;
    }


    public Order(Long idService, Long idClient, String description, String address, ServiceType serviceType, ServiceStatus status) {
        this.idService = idService;
        this.idClient = idClient;
        this.description = description;
        this.address = address;
        this.serviceType = serviceType;
        this.status = status;
    }

    public Order(Long idService, Long idClient, String description, String address, ServiceType serviceType, ServiceStatus status, Long idWorker) {
        this.idService = idService;
        this.idClient = idClient;
        this.description = description;
        this.address = address;
        this.serviceType = serviceType;
        this.status = status;
        this.idWorker = idWorker;
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
        return  idService != null &&
                idService.equals(order.idService) &&
                idClient != null &&
                idClient.equals(order.idClient) &&
                description != null &&
                description.equals(order.description) &&
                address != null &&
                address.equals(order.address) &&
                serviceType == order.serviceType &&
                status == order.status &&
                idWorker != null &&
                idWorker.equals(order.idWorker);
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
                ", idWorker=" + idWorker +
                '}';
    }
}
