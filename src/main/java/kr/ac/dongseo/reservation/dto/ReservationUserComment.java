package kr.ac.dongseo.reservation.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReservationUserComment {
    private int id;
    private int productId;
    private int reservationInfoId;
    private int userId;
    private BigDecimal score;
    private String reservationEmail;
    private String comment;
    private Date createDate;
    private Date modifyDate;
    private List<Map<String, Object>> reservationUserCommentImages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getReservationInfoId() {
        return reservationInfoId;
    }

    public void setReservationInfoId(int reservationInfoId) {
        this.reservationInfoId = reservationInfoId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getReservationEmail() {
        return reservationEmail;
    }

    public void setReservationEmail(String reservationEmail) {
        this.reservationEmail = reservationEmail;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public List<Map<String, Object>> getReservationUserCommentImages() {
        return reservationUserCommentImages;
    }

    public void setReservationUserCommentImages(List<Map<String, Object>> reservationUserCommentImages) {
        this.reservationUserCommentImages = reservationUserCommentImages;
    }

    @Override
    public String toString() {
        return "ReservationUserComment{" +
                "id=" + id +
                ", productId=" + productId +
                ", reservationInfoId=" + reservationInfoId +
                ", userId=" + userId +
                ", score=" + score +
                ", reservationEmail='" + reservationEmail + '\'' +
                ", comment='" + comment + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                ", reservationUserCommentImages=" + reservationUserCommentImages +
                '}';
    }
}
