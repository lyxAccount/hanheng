package com.example.interfacedemo.controller.service;

import javax.servlet.http.HttpServletResponse;

public interface AmrService {
    void amr();

    void testIo(HttpServletResponse response) throws Exception;
}
