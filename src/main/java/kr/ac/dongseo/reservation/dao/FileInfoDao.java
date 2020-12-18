package kr.ac.dongseo.reservation.dao;

import static kr.ac.dongseo.reservation.dao.sqls.FileInfoDaoSqls.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.dongseo.reservation.dto.FileInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FileInfoDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAction;
    private final RowMapper<FileInfo> rowMapper = BeanPropertyRowMapper.newInstance(FileInfo.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileInfoDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("file_info")
                .usingGeneratedKeyColumns("id");
    }

    public Map<String, Object> selectById(int id) {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("id", id);

        FileInfo fileInfo = jdbcTemplate.queryForObject(SELECT_BY_ID, paramMap, rowMapper);

        return objectMapper.convertValue(fileInfo, Map.class);
    }

    public int insert(FileInfo fileInfo) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(fileInfo);
        return insertAction.executeAndReturnKey(params).intValue();
    }

}
