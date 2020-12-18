package kr.ac.dongseo.reservation.dao.sqls;

public class ReservationUserCommentDaoSqls {
    public static final String SELECT_BY_PRODUCT_ID =
            "SELECT ruc.id, ruc.product_id, ruc.reservation_info_id,"
                    + " ruc.score, u.email reservation_email, ruc.comment, ruc.create_date, ruc.modify_date"
                    + " FROM reservation_user_comment AS ruc"
                    + " JOIN user AS u"
                    + " ON u.id = ruc.user_id"
                    + " WHERE ruc.product_id = :product_id"
                    + " ORDER BY ruc.id DESC LIMIT :start, :limit";

    public static final String SELECT_BY_USER_ID =
            "SELECT ruc.id, ruc.product_id, ruc.reservation_info_id,"
                    + " ruc.score, u.email reservation_email, ruc.comment, ruc.create_date, ruc.modify_date"
                    + " FROM reservation_user_comment AS ruc"
                    + " JOIN user AS u"
                    + " ON u.id = ruc.user_id"
                    + " WHERE ruc.user_id = :userId AND ruc.product_id = :productId"
                    + " ORDER BY ruc.id DESC LIMIT :start, :limit";

    public static final String SELECT_ALL_BY_USER_ID =
            "SELECT ruc.id, ruc.product_id, ruc.reservation_info_id,"
                    + " ruc.score, u.email reservation_email, ruc.comment, ruc.create_date, ruc.modify_date"
                    + " FROM reservation_user_comment AS ruc"
                    + " JOIN user AS u"
                    + " ON u.id = ruc.user_id"
                    + " WHERE ruc.user_id = :userId"
                    + " ORDER BY ruc.id DESC LIMIT :start, :limit";

    public static final String SELECT_PAGING =
            "SELECT ruc.id, ruc.product_id, ruc.reservation_info_id,"
                    + " ruc.score, u.email reservation_email, ruc.comment, ruc.create_date, ruc.modify_date"
                    + " FROM reservation_user_comment AS ruc"
                    + " JOIN user AS u"
                    + " ON u.id = ruc.user_id"
                    + " ORDER BY ruc.id DESC LIMIT :start, :limit";

    public static final String SELECT_ALL_IMAGES =
            "SELECT id, reservation_info_id, reservation_user_comment_id, file_id FROM reservation_user_comment_image"
                    + " WHERE reservation_user_comment_id = :reservationUserCommentId";

    public static final String SELECT_SCORE_BY_PRODUCT_ID =
            "SELECT score FROM reservation.reservation_user_comment"
                    + " WHERE product_id = :product_id";

    public static final String SELECT_TOTAL_COUNT_BY_USER_ID =
            "SELECT count(*) count" +
                    " FROM reservation.reservation_user_comment" +
                    " WHERE user_id = :userId";

    public static final String SELECT_TOTAL_COUNT_BY_USER_ID_AND_PRODUCT_ID =
            "SELECT count(*) count" +
                    " FROM reservation.reservation_user_comment" +
                    " WHERE user_id = :userId AND product_id = :productId";

    public static final String SELECT_TOTAL_COUNT_BY_PRODUCT_ID =
            "SELECT COUNT(*) AS count"
                    + " FROM reservation.reservation_user_comment"
                    + " WHERE product_id = :product_id";

    public static final String SELECT_TOTAL_COUNT =
            "SELECT COUNT(*) AS count"
                    + " FROM reservation.reservation_user_comment";
}
