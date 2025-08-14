package com.fbn.case_study.mapper;

import com.fbn.case_study.dto.CustomerDto;
import com.fbn.case_study.entity.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toEntity(CustomerDto.Request r) {
        Customer c = new Customer();
        c.setFirstName(r.getFirstName());
        c.setLastName(r.getLastName());
        c.setEmail(r.getEmail());
        c.setAddress(r.getAddress());
        c.setPhone(r.getPhone());
        c.setAccountNumber(r.getAccountNumber());
        c.setCustomerType(r.getCustomerType());
        c.setClassification(r.getClassification());
        c.setKycLevel(r.getKycLevel());
        c.setNin(r.getNin());
        c.setBvn(r.getBvn());
        c.setDob(r.getDob());
        return c;
    }

    public CustomerDto.Response toResponse(Customer c) {
        CustomerDto.Response dto = new CustomerDto.Response();
        BeanUtils.copyProperties(c, dto);
        return dto;
    }
}
