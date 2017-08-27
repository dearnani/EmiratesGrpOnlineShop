package com.emrgrp.ecom.shopcart.service.impl;

import com.emrgrp.ecom.shopcart.service.ProcessOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProcessOrderServiceImpl implements ProcessOrderService {

    private final Logger log = LoggerFactory.getLogger(ProcessOrderServiceImpl.class);

}
