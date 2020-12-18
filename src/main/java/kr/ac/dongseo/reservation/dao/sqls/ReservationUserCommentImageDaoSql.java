package kr.ac.dongseo.reservation.dao.sqls;

public class ReservationUserCommentImageDaoSql {
    public static final String SELECT_BY_RESERVATION_USER_COMMENT_ID = "SELECT id, reservation_info_id, reservation_user_comment_id," +
            "file_id FROM reservation_user_comment_image WHERE reservation_user_comment_id = :reservation_user_comment_id";
}
