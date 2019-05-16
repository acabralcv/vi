package com.library.helpers;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

public class HelperPaging {

    private int size;
    private Long totalPages;
    private int pageNumbers;
    private Sort sort;
    private  Long number;

    public HelperPaging(){}

    public HelperPaging(int size, Long totalPages, int pageNumbers, Sort sort){
        this.size = size;
        this.totalPages = totalPages;
        this.pageNumbers = pageNumbers;
        this.sort = sort;
    }

    public HelperPaging(Pageable pageable){
        this.size = pageable.getPageSize();
        this.pageNumbers = pageable.getPageNumber();
        this.sort = pageable.getSort();
    }

    public HelperPaging(Pageable pageable, Long totalPages){
        this.size = pageable.getPageSize();
        this.totalPages = totalPages;
        this.pageNumbers = pageable.getPageNumber();
        this.sort = pageable.getSort();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(int pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}


