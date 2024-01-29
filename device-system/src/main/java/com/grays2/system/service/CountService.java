package com.grays2.system.service;


import java.util.List;
import java.util.Map;

public interface CountService {
    Map<String, Object> getProjectLine();

    List<Map<String, Integer>> getUserInsert();
    Map<String, Object> getContribution();

}
