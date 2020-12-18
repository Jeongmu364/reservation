package kr.ac.dongseo.reservation.service.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.auth.In;
import kr.ac.dongseo.reservation.dao.*;
import kr.ac.dongseo.reservation.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.dongseo.reservation.service.ReservationService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final CategoryDao categoryDao;
    private final DisplayInfoImageDao displayInfoImageDao;
    private final ProductDao productDao;
    private final ProductImageDao productImageDao;
    private final ProductPriceDao productPriceDao;
    private final PromotionDao promotionDao;
    private final ReservationUserCommentDao reservationUserCommentDao;
    private final ReservationInfoDao reservationInfoDao;
    private final ReservationInfoPriceDao reservationInfoPriceDao;
    private final ReservationUserCommentImageDao reservationUserCommentImageDao;
    private final FileInfoDao fileInfoDao;

    public ReservationServiceImpl(CategoryDao categoryDao, DisplayInfoImageDao displayInfoImageDao, ProductDao productDao,
                                  ProductImageDao productImageDao, ProductPriceDao productPriceDao, PromotionDao promotionDao,
                                  ReservationUserCommentDao reservationUserCommentDao, ReservationInfoDao reservationInfoDao,
                                  ReservationInfoPriceDao reservationInfoPriceDao, ReservationUserCommentImageDao reservationUserCommentImageDao,
                                  FileInfoDao fileInfoDao) {
        this.categoryDao = categoryDao;
        this.displayInfoImageDao = displayInfoImageDao;
        this.productDao = productDao;
        this.productImageDao = productImageDao;
        this.productPriceDao = productPriceDao;
        this.promotionDao = promotionDao;
        this.reservationUserCommentDao = reservationUserCommentDao;
        this.reservationInfoDao = reservationInfoDao;
        this.reservationInfoPriceDao = reservationInfoPriceDao;
        this.reservationUserCommentImageDao = reservationUserCommentImageDao;
        this.fileInfoDao = fileInfoDao;
    }

    @Override
    @Transactional
    public Map<String, Object> getCategories() {
        Map<String, Object> resultMap = new HashMap<>();

        List<Category> items = categoryDao.selectAll();
        Integer size = items.size();

        resultMap.put("size", size);
        resultMap.put("items", items);

        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> getProducts(Map<String, Integer> params) {
        Map<String, Object> resultMap = new HashMap<>();

        Integer categoryId = params.get("categoryId");
        Integer start = params.get("start");

        Long totalCount;
        Integer productCount;
        List<Product> products;

        Boolean categoryIdIsNull = (categoryId == null);

        if (!categoryIdIsNull && start != null) {
            if (categoryId != 0) {
                products = productDao.selectPage(categoryId, start, DISPLAYINFOS_LIMIT);
                totalCount = productDao.selectTotalCount(categoryId);
            } else {
                products = productDao.selectAll(start, DISPLAYINFOS_LIMIT);
                totalCount = productDao.selectTotalCount();
            }

        } else if (!categoryIdIsNull && start == null) {
            if (categoryId != 0) {
                products = productDao.selectPage(categoryId, BASIC_START, DISPLAYINFOS_LIMIT);
                totalCount = productDao.selectTotalCount(categoryId);
            } else {
                products = productDao.selectAll(BASIC_START, DISPLAYINFOS_LIMIT);
                totalCount = productDao.selectTotalCount();
            }

        } else if (categoryIdIsNull && start != null) {
            products = productDao.selectAll(start, DISPLAYINFOS_LIMIT);
            totalCount = productDao.selectTotalCount();

        } else {
            products = productDao.selectAll(BASIC_START, DISPLAYINFOS_LIMIT);
            totalCount = productDao.selectTotalCount();
        }
        productCount = products.size();

        resultMap.put("totalCount", totalCount);
        resultMap.put("productCount", productCount);
        resultMap.put("products", products);

        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> getPromotions() {
        Map<String, Object> resultMap = new HashMap<>();

        List<Promotion> items = promotionDao.selectAll();
        Integer size = items.size();

        resultMap.put("size", size);
        resultMap.put("items", items);

        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> getDisplayinfos(Map<String, Integer> params) {
        Map<String, Object> resultMap = new HashMap<>();

        Integer displayId = params.get("displayId");

        Product product;
        List<ProductImage> productImages;
        List<DisplayInfoImage> displayInfoImages;
        Integer avgScore;
        List<ProductPrice> productPrices;

        if (displayId != null) {
            product = productDao.select(displayId);
            productImages = productImageDao.select(displayId);
            displayInfoImages = displayInfoImageDao.select(displayId);
            avgScore = getAvgScore(reservationUserCommentDao.selectScore(displayId));
            productPrices = productPriceDao.selectAllByProductId(displayId);

            resultMap.put("product", product);
            resultMap.put("productImages", productImages);
            resultMap.put("displayInfoImages", displayInfoImages);
            resultMap.put("avgScore", avgScore);
            resultMap.put("productPrices", productPrices);
        }

        return resultMap;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getReservationUserComments(Map<String, Integer> params) {
        Map<String, Object> resultMap = new HashMap<>();

        Integer productId = params.get("productId");
        Integer start = params.get("start");

        Long totalCount;
        Integer commentCount;
        List<ReservationUserComment> reservationUserComments;

        if (productId != null && start != null) {
            reservationUserComments = getReservationUserCommentList(productId, start);
            totalCount = reservationUserCommentDao.selectTotalCount(productId);

        } else if (productId != null && start == null) {
            reservationUserComments = getReservationUserCommentList(productId, BASIC_START);
            totalCount = reservationUserCommentDao.selectTotalCount(productId);

        } else if (start != null) {
            reservationUserComments = getReservationUserCommentList(start);
            totalCount = reservationUserCommentDao.selectTotalCount();

        } else {
            reservationUserComments = getReservationUserCommentList(BASIC_START);
            totalCount = reservationUserCommentDao.selectTotalCount();

        }
        commentCount = reservationUserComments.size();

        resultMap.put("totalCount", totalCount);
        resultMap.put("commentCount", commentCount);
        resultMap.put("reservationUserComments", reservationUserComments);

        return resultMap;
    }

    // Method Overloading
    private List<ReservationUserComment> getReservationUserCommentList(int productId, int start) {
        List<ReservationUserComment> reservationUserComments = reservationUserCommentDao.selectByProductId(productId,
                start, RESERVATION_USER_COMMENT_LIMIT);

        return setReservationUserCommentsImage(reservationUserComments);
    }

    // Method Overloading
    private List<ReservationUserComment> getReservationUserCommentList(int start) {
        List<ReservationUserComment> reservationUserComments = reservationUserCommentDao.selectAll(start,
                RESERVATION_USER_COMMENT_LIMIT);

        return setReservationUserCommentsImage(reservationUserComments);
    }

    private List<ReservationUserComment> setReservationUserCommentsImage(List<ReservationUserComment> userComments) {
        for (ReservationUserComment userComment : userComments) {
            userComment.setReservationUserCommentImages(getReservationUserCommentImages(userComment.getId()));
        }

        return userComments;
    }

    private List<Map<String, Object>> getReservationUserCommentImages(int reservationUserCommentId) {
        List<Map<String, Object>> images = reservationUserCommentImageDao.selectByReservationUserCommentId(reservationUserCommentId);
        for (Map<String, Object> image : images) {
            image.putAll(fileInfoDao.selectById((int) image.get("fileId")));
        }

        return images;
    }


    private Integer getAvgScore(List<BigDecimal> scoreList) {
        BigDecimal totalScore = BigDecimal.ZERO;
        for (BigDecimal score : scoreList) {
            totalScore = totalScore.add(score);
        }

        return totalScore.divide(new BigDecimal(scoreList.size()), 1, RoundingMode.HALF_EVEN).intValue();
    }

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> addReservation(Map<String, Object> requestBody) {
        ReservationInfo reservationInfo = getReservationInfoForInsert(requestBody);
        Long reservationInfoId = reservationInfoDao.insert(reservationInfo);
        Map<String, Object> resultResInfo = reservationInfoDao.selectById(reservationInfoId.intValue());

        List<ReservationInfoPrice> objectedPrices = getReservationInfoPricesForInsert(
                (List<Map<String, Object>>) requestBody.get("prices"), reservationInfoId);
        List<Long> priceIds = insertReservationInfoPrices(objectedPrices);

        List<Map<String, Object>> mappedPrices = getInsertedPrices(priceIds);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.putAll(resultResInfo);
        resultMap.put("prices", mappedPrices);

        return resultMap;
    }

    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> cancelReservation(Map<String, Object> requestBody) {
        int id = (int) requestBody.get("id");
        return reservationInfoDao.updateCancelFlag(id);
    }

    private List<Map<String, Object>> getInsertedPrices(List<Long> priceIds) {
        List<Map<String, Object>> resultPrices = new ArrayList<>();

        for (Long id : priceIds) {
            Map<String, Object> map = new HashMap<>();
            ReservationInfoPrice resInfoPrice = reservationInfoPriceDao.selectById(id.intValue());
            map.put("id", resInfoPrice.getId());
            map.put("reservationInfoId", resInfoPrice.getReservationInfoId());
            map.put("productPriceId", resInfoPrice.getProductPriceId());
            map.put("count", resInfoPrice.getCount());

            resultPrices.add(map);
        }

        return resultPrices;
    }

    private List<Long> insertReservationInfoPrices(List<ReservationInfoPrice> prices) {
        List<Long> resultPriceIds = new ArrayList<>();

        for (ReservationInfoPrice resInfoPrice : prices) {
            Long id = reservationInfoPriceDao.insert(resInfoPrice);
            resultPriceIds.add(id);
        }

        return resultPriceIds;
    }

    private List<ReservationInfoPrice> getReservationInfoPricesForInsert(List<Map<String, Object>> prices,
                                                                         Long resInfoId) {
        List<ReservationInfoPrice> newPrices = new ArrayList<>();

        for (Map<String, Object> price : prices) {
            ReservationInfoPrice resInfoPrice = new ReservationInfoPrice();
            resInfoPrice.setCount((Integer) price.get("count"));
            resInfoPrice.setProductPriceId((Integer) price.get("productPriceId"));
            resInfoPrice.setReservationInfoId(resInfoId.intValue());

            newPrices.add(resInfoPrice);
        }

        return newPrices;
    }

    private ReservationInfo getReservationInfoForInsert(Map<String, Object> params) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        ReservationInfo resInfo = new ReservationInfo();

        resInfo.setCancelFlag(0);
        resInfo.setCreateDate(new Date());
        resInfo.setDisplayInfoId((int) params.get("displayInfoId"));
        resInfo.setModifyDate(new Date());
        resInfo.setProductId((int) params.get("productId"));
        try {
            resInfo.setReservationDate(dateFormat.parse((String) params.get("reservationYearMonthDay")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        resInfo.setUserId((int) params.get("userId"));

        return resInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getReservationUserCommentsByUserId(Map<String, Integer> params) {
        int userId = params.get("userId");
        Integer start = params.get("start");
        Integer productId = params.get("productId");

        Long totalCount;
        List<ReservationUserComment> reservationUserComments;
        if (productId != null) {
            totalCount = reservationUserCommentDao.selectTotalCountByUserId(userId, productId);
            if (start != null) {
                reservationUserComments = reservationUserCommentDao.selectByUserId(userId, productId, start, RESERVATION_USER_COMMENT_LIMIT);
            } else {
                reservationUserComments = reservationUserCommentDao.selectByUserId(userId, productId, BASIC_START, RESERVATION_USER_COMMENT_LIMIT);
            }
        } else {
            totalCount = reservationUserCommentDao.selectTotalCountByUserId(userId);
            if (start != null) {
                reservationUserComments = reservationUserCommentDao.selectAllByUserId(userId, start, RESERVATION_USER_COMMENT_LIMIT);
            } else {
                reservationUserComments = reservationUserCommentDao.selectAllByUserId(userId, BASIC_START, RESERVATION_USER_COMMENT_LIMIT);
            }
        }
        reservationUserComments = setReservationUserCommentsImage(reservationUserComments);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("totalCount", totalCount);
        resultMap.put("commentCount", reservationUserComments.size());
        resultMap.put("reservationUserComments", reservationUserComments);

        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> addComment(Map<String, Object> params) {
        Integer reservationInfoId = (Integer) params.get("reservationInfoId");

        kr.ac.dongseo.reservation.vo.ReservationUserComment comment = new kr.ac.dongseo.reservation.vo.ReservationUserComment();
        comment.setReservationInfoId(reservationInfoId);
        comment.setScore(new BigDecimal((Integer) params.get("score")));
        comment.setComment((String) params.get("comment"));
        comment.setUserId((Integer) params.get("userId"));
        comment.setCreateDate(new Date());
        comment.setModifyDate(new Date());

        Integer productId = (Integer) reservationInfoDao.selectById(reservationInfoId).get("productId");
        comment.setProductId(productId);
        System.out.println("productId : " + productId);

        Integer resultCommentId = reservationUserCommentDao.insertCommentV2(comment);


        MultipartFile file = (MultipartFile) params.get("multipartFile");
        System.out.println("multipartFile : " + file.getOriginalFilename());
        if (file != null) {
            Integer fileInfoId = uploadFile(file);
            if (fileInfoId != null) {
                ReservationUserCommentImage reservationUserCommentImage = new ReservationUserCommentImage();
                reservationUserCommentImage.setReservationInfoId(reservationInfoId);
                reservationUserCommentImage.setReservationUserCommentId(resultCommentId);
                reservationUserCommentImage.setFileId(fileInfoId);
                Integer reservationUserCommentImageId = reservationUserCommentImageDao.insertReservationUserCommentImage(reservationUserCommentImage);
            }
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        resultMap.put("productId", productId);

        return resultMap;
    }

    private Integer uploadFile(MultipartFile file) {
        try (
//                FileOutputStream fos = new FileOutputStream("src/main/webapp/resources/img_map/" + file.getOriginalFilename());
                FileOutputStream fos = new FileOutputStream("c:/tmp/img_map/" + file.getOriginalFilename());
                InputStream is = file.getInputStream();
        ) {
            int readCount = 0;
            byte[] buffer = new byte[1024];
            while ((readCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, readCount);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getOriginalFilename());
        fileInfo.setSaveFileName("img_map/" + file.getOriginalFilename());
        fileInfo.setContentType("image/png");
        fileInfo.setDeleteFlag(0);
        fileInfo.setCreateDate(new Date());
        fileInfo.setModifyDate(new Date());

        int fileInfoId = fileInfoDao.insert(fileInfo);

        return fileInfoId;
    }

    @ApiOperation(value = "download a file by fileId")
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> downloadFile(Integer fileId, HttpServletResponse response) {
        Map<String, Object> fileInfo = fileInfoDao.selectById(fileId);
        String fileName = (String) fileInfo.get("fileName");
        String saveFileName = "c:/tmp/" + ((String) fileInfo.get("saveFileName"));
        String contentType = (String) fileInfo.get("contentType");

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", contentType);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");

        try (
                FileInputStream fis = new FileInputStream(saveFileName);
                OutputStream os = response.getOutputStream();
        ) {
            int readCount = 0;
            byte[] buffer = new byte[1024];
            while ((readCount = fis.read(buffer)) != 1) {
                os.write(buffer, 0, readCount);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
