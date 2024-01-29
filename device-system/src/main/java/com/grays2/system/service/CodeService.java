package com.grays2.system.service;



import com.grays2.common.result.R;

import java.util.Map;

public interface CodeService {
    R<Map<String, Object>> getCode();

    R<String> getCodeMail(String qq);
}
