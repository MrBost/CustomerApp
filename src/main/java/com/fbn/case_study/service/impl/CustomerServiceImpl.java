package com.fbn.case_study.service.impl;

import com.fbn.case_study.dto.CustomerDto;
import com.fbn.case_study.entity.Customer;
import com.fbn.case_study.mapper.CustomerMapper;
import com.fbn.case_study.repository.CustomerRepository;
import com.fbn.case_study.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;
    @Transactional
    public CustomerDto.Response create(CustomerDto.Request request, CustomerMapper mapper, String principal) {
        System.out.println("received request "+request);
        if (customerRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new DuplicateKeyException("Account number already exists");
        }
        Customer c = mapper.toEntity(request);
        c.setCreatedBy(principal);
        c.setLastModifiedBy(principal);
        return mapper.toResponse(customerRepository.save(c));
    }
    @Transactional
    public CustomerDto.Response create(CustomerDto.Request request) {
        if (customerRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new DuplicateKeyException("Account number already exists");
        }
        Customer c = mapper.toEntity(request);
        return mapper.toResponse(customerRepository.save(c));
    }
    @Transactional(readOnly = true)
    public Page<CustomerDto.Response> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return customerRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public CustomerDto.Response get(String id) {
        return customerRepository.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    @Transactional
    public CustomerDto.Response update(String id, CustomerDto.Request req, CustomerMapper mapper, String principal) {
        Customer c = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        c.setFirstName(req.getFirstName());
        c.setLastName(req.getLastName());
        c.setEmail(req.getEmail());
        c.setPhone(req.getPhone());
        c.setCustomerType(req.getCustomerType());
        c.setClassification(req.getClassification());
        c.setKycLevel(req.getKycLevel());
        c.setLastModifiedBy(principal);
        return mapper.toResponse(customerRepository.save(c));
    }
    @Transactional
    public CustomerDto.Response update(String id, CustomerDto.Request req, CustomerMapper mapper) {
        Customer c = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        c.setFirstName(req.getFirstName());
        c.setLastName(req.getLastName());
        c.setEmail(req.getEmail());
        c.setPhone(req.getPhone());
        c.setCustomerType(req.getCustomerType());
        c.setClassification(req.getClassification());
        c.setKycLevel(req.getKycLevel());
        return mapper.toResponse(customerRepository.save(c));
    }

    @Transactional
    public void delete(String id) {
        if (!customerRepository.existsById(id)) throw new EntityNotFoundException("Customer not found");
        customerRepository.deleteById(id);
    }
}
