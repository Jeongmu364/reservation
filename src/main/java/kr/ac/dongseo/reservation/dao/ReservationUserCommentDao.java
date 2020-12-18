package kr.ac.dongseo.reservation.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.ac.dongseo.reservation.dto.ReservationUserComment;

import static kr.ac.dongseo.reservation.dao.sqls.ReservationUserCommentDaoSqls.*;

@Repository
public class ReservationUserCommentDao {
    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<ReservationUserComment> rowMapper = BeanPropertyRowMapper.newInstance(ReservationUserComment.class);
    private SimpleJdbcInsert insertAction;
    private SimpleJdbcInsert voInsertAction;

    public ReservationUserCommentDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_user_comment")
                .usingGeneratedKeyColumns("product_id");
        this.voInsertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_user_comment")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationUserComment> selectByProductId(Integer productId, Integer start, Integer limit) {
        Map<String, Integer> params = new HashMap<>();
        params.put("product_id", productId);
        params.put("start", start);
        params.put("limit", limit);

        return jdbc.query(SELECT_BY_PRODUCT_ID, params, rowMapper);
    }

    public List<ReservationUserComment> selectByUserId(int userId, int productId, int start, int limit) {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("start", start);
        paramMap.put("productId", productId);
        paramMap.put("limit", limit);

        return jdbc.query(SELECT_BY_USER_ID, paramMap, rowMapper);
    }

    public List<ReservationUserComment> selectAllByUserId(int userId, int start, int limit) {
        Map<String, Integer> paramMap  = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("start", start);
        paramMap.put("limit", limit);

        return jdbc.query(SELECT_ALL_BY_USER_ID, paramMap, rowMapper);
    }

    public List<ReservationUserComment> selectAll(Integer start, Integer limit) {
        Map<String, Integer> params = new HashMap<>();
        params.put("start", start);
        params.put("limit", limit);

        return jdbc.query(SELECT_PAGING, params, rowMapper);
    }

    public List<BigDecimal> selectScore(Integer productId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("product_id", productId);

        List<Map<String, Object>> scoreMapList = jdbc.queryForList(SELECT_SCORE_BY_PRODUCT_ID, params);

        List<BigDecimal> listForReturn = new ArrayList<>();
        for (Map<String, Object> scoreMap : scoreMapList) {
            listForReturn.add((BigDecimal) scoreMap.get("score"));
        }

        return listForReturn;
    }

    public Long selectTotalCountByUserId(int userId, int productId) {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("productId", productId);

        return (Long) jdbc.queryForMap(SELECT_TOTAL_COUNT_BY_USER_ID_AND_PRODUCT_ID, paramMap).get("count");
    }

    // method overloading
    public Long selectTotalCountByUserId(int userId) {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("userId", userId);

        return (Long) jdbc.queryForMap(SELECT_TOTAL_COUNT_BY_USER_ID, paramMap).get("count");
    }

    public Long selectTotalCount(Integer productId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("product_id", productId);
        Map<String, Object> objectMap = jdbc.queryForMap(SELECT_TOTAL_COUNT_BY_PRODUCT_ID, params);

        return (Long) objectMap.get("count");
    }

    public Long selectTotalCount() {
        Map<String, Integer> params = new HashMap<>();
        Map<String, Object> objectMap = jdbc.queryForMap(SELECT_TOTAL_COUNT, params);

        return (Long) objectMap.get("count");
    }

    public int insertComment(ReservationUserComment reservationUserComment) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservationUserComment);
        return insertAction.executeAndReturnKey(parameterSource).intValue();
    }

    public Integer insertCommentV2(kr.ac.dongseo.reservation.vo.ReservationUserComment reservationUserComment) {
        SqlParameterSource parameterSource= new BeanPropertySqlParameterSource(reservationUserComment);
        return voInsertAction.executeAndReturnKey(parameterSource).intValue();
    }
}
