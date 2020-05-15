package com.github.yanzord.salesdataanalysis.service;

import com.github.yanzord.salesdataanalysis.dao.FileDAO;

public class Worker {
    private FileDAO fileDAO;

    public Worker() {
        this.fileDAO = new FileDAO();
    }



}
