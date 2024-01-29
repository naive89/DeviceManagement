package com.grays2.system.service;



import java.util.List;
import java.util.Map;

public interface ProcessService {

    List<Map<String, Object>> getAllShareUser();

    void createShareUserRedis();
}
