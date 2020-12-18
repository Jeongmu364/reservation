package kr.ac.dongseo.reservation.service;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

public interface ReservationService {
	public static final Integer BASIC_START = 0;
	public static final Integer DISPLAYINFOS_LIMIT = 4;
	public static final Integer RESERVATION_USER_COMMENT_LIMIT = 5;
	
	// /api/categories
	public Map<String, Object> getCategories();
	
	// /api/displayinfos
	public Map<String, Object> getProducts(Map<String, Integer> params);
	
	// /api/promotions
	public Map<String, Object> getPromotions();
	
	// /api/displayinfos/{displayId}
	public Map<String, Object> getDisplayinfos(Map<String, Integer> params);
	
	// GET /api/displayinfos
	public Map<String, Object> getReservationUserComments(Map<String, Integer> params);
	
	// POST /api/reservationInfos
	public Map<String, Object> addReservation(Map<String, Object> requestBody);
	
	// PUT /api/reservationInfos
	public Map<String, Object> cancelReservation(Map<String, Object> requestBody);

	// POST /api/comments
	public Map<String, Object> addComment(Map<String, Object> requestBody);

	// GET /api/comments
	public Map<String, Object> getReservationUserCommentsByUserId(Map<String, Integer> params);

	// GET /api/files/{fileId}
	public Map<String, Object> downloadFile(Integer fileId, HttpServletResponse response);
}
