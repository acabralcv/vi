package com.library.helpers;

import org.json.simple.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HelperPaging {

    private int size;
    public Long totalPages;
    private List<Integer> pageNumbers;
    private Sort sort;
    private  Long number;

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    private  Long totalElements;


    public HelperPaging(){}

    /**
     * Retorna a paginação atualizada com base na resposta do  serviço
     * @param pageable
     * @param objResponse
     * @return
     */
    public HelperPaging getResponsePaging(Pageable pageable, JSONObject objResponse){

        HelperPaging objPaging = new HelperPaging(pageable);
        objPaging.setNumber((Long)objResponse.get("number"));
        objPaging.setTotalPages((Long) objResponse.get(("totalPages")));
        objPaging.setTotalElements((Long) objResponse.get(("totalElements")));

        if (objPaging.getTotalPages() > 0) {
            List<Integer>  pageNumbers = IntStream.rangeClosed(1,  objPaging.getTotalPages().intValue())
                    .boxed().collect(Collectors.toList());
            objPaging.setPageNumbers(pageNumbers);
        }
        return objPaging;
    }

    public HelperPaging(Pageable pageable){
        this.size = pageable.getPageSize();
        this.sort = pageable.getSort();
    }

//    public HelperPaging(Pageable pageable, Long totalPages){
//        this.size = pageable.getPageSize();
//        this.totalPages = totalPages;
//        this.sort = pageable.getSort();
//    }

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

    public List<Integer> getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(List<Integer> pageNumbers) {
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


