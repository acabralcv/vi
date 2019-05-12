package com.library.service;

import org.springframework.stereotype.Service;

@Service
public class ProcessService {

    //final private List<Process> books = BookUtils.buildBooks();

//    public Page<Process> findPaginated(Pageable pageable, WfActivityRepository wfActivityRepository) {
//
//        int pageSize = pageable.getPageSize();
//        int currentPage = pageable.getPageNumber();
//        int startItem = currentPage * pageSize;
//        List<Process> list;
//
//        List<Process> process = wfActivityRepository.findAll(PageRequest.of(0, 5));
//
//        if (books.size() < startItem) {
//            list = Collections.emptyList();
//        } else {
//            int toIndex = Math.min(startItem + pageSize, books.size());
//            list = books.subList(startItem, toIndex);
//        }
//
//        Page<Book> bookPage = new PageImpl<Book>(list, PageRequest.of(currentPage, pageSize), books.size());
//
//        return bookPage;
//    }
}