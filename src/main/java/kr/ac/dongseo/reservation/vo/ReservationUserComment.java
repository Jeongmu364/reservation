package kr.ac.dongseo.reservation.vo;

import java.math.BigDecimal;
import java.util.Date;

public class ReservationUserComment {
    private Integer id;
    private Integer productId;
    private Integer reservationInfoId;
    private Integer userId;
    private BigDecimal score;
    private String comment;
    private Date createDate;
    private Date modifyDate;

    @Override
    public String toString() {
        return "ReservationUserComment{" +
                "id=" + id +
                ", productId=" + productId +
                ", reservationInfoId=" + reservationInfoId +
                ", userId=" + userId +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getReservationInfoId() {
        return reservationInfoId;
    }

    public void setReservationInfoId(Integer reservationInfoId) {
        this.reservationInfoId = reservationInfoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
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
}
