package com.wora.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IComponentMapper<Entity> {
    Entity map(ResultSet rs) throws SQLException;
}
