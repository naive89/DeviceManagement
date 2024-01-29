package com.grays2.system.service;




import com.grays2.system.domain.Dict;

import java.util.List;

public interface DictService {
    void createDictRedis();

    List<Dict> getDict(String dict);

    int getId(String db);

    void updateDBId(String db);
}
