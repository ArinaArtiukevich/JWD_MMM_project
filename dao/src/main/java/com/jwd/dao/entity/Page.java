package com.jwd.dao.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Page<T> {
    private int pageNumber;
    private long totalElements;
    private int limit;
    private List<T> elements = new ArrayList<>();
    private String sortBy = "name";
    private String direction = "ASC";

    public Page() {}

    public Page(int pageNumber, long totalElements, int limit, List<T> elements, String sortBy, String direction) {
        this.pageNumber = pageNumber;
        this.totalElements = totalElements;
        this.limit = limit;
        this.elements = elements;
        this.sortBy = sortBy;
        this.direction = direction;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page<?> page = (Page<?>) o;
        return pageNumber == page.pageNumber &&
                totalElements == page.totalElements &&
                limit == page.limit &&
                Objects.equals(elements, page.elements) &&
                Objects.equals(sortBy, page.sortBy) &&
                Objects.equals(direction, page.direction);
    }

    @Override
    public int hashCode() {
        int result = pageNumber;
        result = 31 * result + (int) (totalElements ^ (totalElements >>> 32));
        result = 31 * result + limit;
        result = 31 * result + (elements != null ? elements.hashCode() : 0);
        result = 31 * result + (sortBy != null ? sortBy.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNumber=" + pageNumber +
                ", totalElements=" + totalElements +
                ", limit=" + limit +
                ", elements=" + elements +
                ", sortBy='" + sortBy + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
