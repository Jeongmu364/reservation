package kr.ac.dongseo.reservation.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kr.ac.dongseo.reservation.service.ReservationService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(value = "ReservationController V1")
@RequestMapping("/api")
public class ReservationApiController {

    private final ReservationService reservationService;

    public ReservationApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ApiOperation(value = "Get categories")
    @GetMapping("/categories")
    public Map<String, Object> getCategories() {
        return reservationService.getCategories();
    }

    @ApiOperation(value = "Get displayinfos")
    @GetMapping("/displayinfos")
    public Map<String, Object> getProducts(@RequestParam(name = "categoryId", required = false) Integer categoryId,
                                           @RequestParam(name = "start", required = false) Integer start) {
        Map<String, Integer> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("start", start);

        return reservationService.getProducts(params);
    }

    @ApiOperation(value = "Get promotions")
    @GetMapping("/promotions")
    public Map<String, Object> getPromotions() {
        return reservationService.getPromotions();
    }

    @ApiOperation(value = "Get dinsplayinfos")
    @GetMapping("/displayinfos/{displayId}")
    public Map<String, Object> getDisplayinfosByDisplayId(@PathVariable Integer displayId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("displayId", displayId);

        return reservationService.getDisplayinfos(params);
    }

    @ApiOperation(value = "Get comments about a reservation")
    @GetMapping("/userComments")
    public Map<String, Object> getReservationUserComments(@RequestParam(name = "productId", required = false) Integer productId,
                                                          @RequestParam(name = "start", required = false) Integer start) {
        Map<String, Integer> params = new HashMap<>();
        params.put("productId", productId);
        params.put("start", start);

        return reservationService.getReservationUserComments(params);
    }

    @ApiOperation(value = "Add a reservation")
    @PostMapping("/reservationInfos")
    public Map<String, Object> addReservationInfo(@RequestBody Map<String, Object> params) {

        return reservationService.addReservation(params);
    }

    @GetMapping(value = "/reservationInfos")
    public Map<String, Object> getReservationInfo() {

        return null;
    }

    @ApiOperation(value = "cancel a reservation")
    @PutMapping("/reservationInfos")
    public Map<String, Object> deleteReservationInfo(@RequestBody Map<String, Object> params) {
        System.out.println(params);
        return reservationService.cancelReservation(params);
    }

//
//    @ApiOperation(value = "Add a reservationUserComment")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "OK"),
//            @ApiResponse(code = 500, message = "Exception")
//    })
//    @PostMapping("/comments")
//    public Map<String, Object> addComment(@ModelAttribute Map<String, Object> params) {
//        params.put("userId", 1);
//
//        return reservationService.addComment(params);
//    }


    @ApiOperation(value = "get user_comments by user id")
    @GetMapping("/comments")
    public Map<String, Object> getReservationUserCommentsByUserId(@RequestParam(name = "productId", required = false) Integer productId,
                                                                  @RequestParam(name = "start", required = false) Integer start) {

        int tempUserId = 1;
        Map<String, Integer> params = new HashMap<>();
        params.put("productId", productId);
        params.put("start", start);
        params.put("userId", tempUserId);

        return reservationService.getReservationUserCommentsByUserId(params);
    }

    @ApiOperation(value = "Insert ReservationUserComment with Images")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Exception")
    })
    @PostMapping("/comments")
    public Map<String, Object> addReservationUserComment(@RequestParam(name = "reservationInfoId") Integer reservationInfoId,
                                                         @RequestParam(name = "score") Integer score,
                                                         @RequestParam(name = "comment") String comment,
                                                         @RequestParam(name = "multipartFile", required = false) MultipartFile multipartFile) {
        int tempUserId = 1;
        Map<String, Object> params = new HashMap<>();
        params.put("reservationInfoId", reservationInfoId);
        params.put("score", score);
        params.put("comment", comment);
        params.put("multipartFile", multipartFile);
        params.put("userId", tempUserId);

        return reservationService.addComment(params);
    }

    @GetMapping("/files/{fileId}")
    public Map<String, Object> downloadFile(@PathVariable(name = "fileId") Integer fileId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("fileId", fileId);

        return null;
    }

}
