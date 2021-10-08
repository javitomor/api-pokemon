package com.example.apipokemon.service.impl;

import com.example.apipokemon.service.ICSVManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CSVManagerServiceImpl implements ICSVManagerService {

    @Value("${url.filecsv}")
    private String fileName;



}
