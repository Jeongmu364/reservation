package kr.ac.dongseo.reservation.dao;

import static kr.ac.dongseo.reservation.dao.sqls.ReservationUserCommentImageDaoSql.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.dongseo.reservation.dto.ReservationUserCommentImage;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationUserCommentImageDao {
    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<ReservationUserCommentImage> rowMapper = BeanPropertyRowMapper.newInstance(ReservationUserCommentImage.class);
    private SimpleJdbcInsert insertAction;
    private ObjectMapper objectMapper = new ObjectMapper();

    public ReservationUserCommentImageDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_user_comment_image")
                .usingGeneratedKeyColumns("id");
    }

   public List<Map<String, Object>> selectByReservationUserCommentId(int reservationUserCommentId) {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("reservation_user_comment_id", reservationUserCommentId);

        List<ReservationUserCommentImage> reservationUserCommentImages = jdbc.query(SELECT_BY_RESERVATION_USER_COMMENT_ID, paramMap, rowMapper);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for(ReservationUserCommentImage image : reservationUserCommentImages) {
            resultList.add(objectMapper.convertValue(image, Map.class));
        }

        return resultList;
    }

    public Integer insertReservationUserCommentImage(ReservationUserCommentImage reservationUserCommentImage) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(reservationUserCommentImage);
        return insertAction.executeAndReturnKey(sqlParameterSource).intValue();
    }

}
