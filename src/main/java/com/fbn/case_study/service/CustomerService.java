package com.fbn.case_study.service;

import com.fbn.case_study.dto.CustomerDto;
import com.fbn.case_study.mapper.CustomerMapper;
import org.springframework.data.domain.Page;

public interface CustomerService {
//    CustomerDto.Response create(CustomerDto.Request request);
    CustomerDto.Response create(CustomerDto.Request request);
    Page<CustomerDto.Response> list(int page, int size);
    CustomerDto.Response get(String id);
//    CustomerDto.Response update(String id, CustomerDto.Request req, CustomerMapper mapper);
    CustomerDto.Response update(String id, CustomerDto.Request req);
    void delete(String id);
}
