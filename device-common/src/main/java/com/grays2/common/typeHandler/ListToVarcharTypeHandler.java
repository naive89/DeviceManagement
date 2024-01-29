package com.grays2.common.typeHandler;

import com.grays2.common.utils.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * 列表到 Varchar 类型处理程序
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class ListToVarcharTypeHandler implements TypeHandler<List<String>> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
        if (StringUtils.isNotEmpty(strings)) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < strings.size(); j++) {
                if (j == strings.size() - 1) {
                    sb.append(strings.get(j));
                } else {
                    sb.append(strings.get(j)).append(",");
                }
            }
            preparedStatement.setString(i, sb.toString());
        } else {
            preparedStatement.setString(i, "");
        }
    }

    @Override
    public List<String> getResult(ResultSet resultSet, String s) throws SQLException {
        String resultString = resultSet.getString(s);
        if (StringUtils.isNotEmpty(resultString)) {
            return Arrays.asList(resultString.split(","));
        }
        return null;
    }

    @Override
    public List<String> getResult(ResultSet resultSet, int i) throws SQLException {
        String resultString = resultSet.getString(i);
        if (StringUtils.isNotEmpty(resultString)) {
            return Arrays.asList(resultString.split(","));
        }
        return null;
    }

    @Override
    public List<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
        String resultString = callableStatement.getString(i);
        if (StringUtils.isNotEmpty(resultString)) {
            return Arrays.asList(resultString.split(","));
        }
        return null;
    }
}