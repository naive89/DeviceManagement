package com.grays2.system.service;




import com.grays2.system.domain.Perm;

import java.util.List;

public interface PermService {
    List<Perm> getList();

    void createPermRedis();
}
