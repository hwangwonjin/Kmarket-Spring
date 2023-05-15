package kr.co.kmarket.service.admin;
/*
 * 날짜: 2023/02/08
 * 이름: 이원정
 * 내용: 관리자 상품 서비스
 */

import kr.co.kmarket.dao.admin.AdminProdDAO;
import kr.co.kmarket.entity.ProdEntity;
import kr.co.kmarket.repository.AdminProdRepo;
import kr.co.kmarket.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
public class AdminProdService {

    @Autowired
    private AdminProdDAO dao;

    @Autowired
    private AdminProdRepo repo;


    // 상품 등록
    public void registerProduct(ProductVO vo) throws Exception{

        // vo의 thumb1~3 가져오기
        MultipartFile thumb1 = vo.getFile1();
        MultipartFile thumb2 = vo.getFile2();
        MultipartFile thumb3 = vo.getFile3();
        MultipartFile detail = vo.getFile4();


        // 파일 업로드
        ProductVO file = fileUpload(thumb1, thumb2, thumb3, detail, vo);
        vo.setNewThumb1(file.getNewThumb1());
        vo.setNewThumb2(file.getNewThumb2());
        vo.setNewThumb3(file.getNewThumb3());
        vo.setNewDetail(file.getNewDetail());


        // 상품 등록하기
        dao.registerProduct(vo);

    }

    // 파일
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public ProductVO fileUpload(MultipartFile thumb1, MultipartFile thumb2, MultipartFile thumb3, MultipartFile detail, ProductVO vo){

        // 시스템 경로 지정
        String cate1 = vo.getCate1();
        String cate2 = vo.getCate2();
        String path = new File(uploadPath+cate1+"/"+cate2).getAbsolutePath();

        // 파일 ( 각각 thumb1, thumb2, thumb3, detail ) - getOriginalFilename(): 업로드한 파일의 이름 구하기
        String oriThumb1 = thumb1.getOriginalFilename();
        String oriThumb2 = thumb2.getOriginalFilename();
        String oriThumb3 = thumb3.getOriginalFilename();
        String oriDetail = detail.getOriginalFilename();

        // 파일명 새로 생성 ( 각각 thumb1, thumb2, thumb3, detail )
        String extThumb1 = oriThumb1.substring(oriThumb1.lastIndexOf("."));
        String extThumb2 = oriThumb2.substring(oriThumb2.lastIndexOf("."));
        String extThumb3 = oriThumb3.substring(oriThumb3.lastIndexOf("."));
        String extDetail = oriDetail.substring(oriDetail.lastIndexOf("."));

        String newThumb1 = cate1 + "-" + cate2 + "-" + UUID.randomUUID().toString() + extThumb1;
        String newThumb2 = cate1 + "-" + cate2 + "-" + UUID.randomUUID().toString() + extThumb2;
        String newThumb3 = cate1 + "-" + cate2 + "-" + UUID.randomUUID().toString() + extThumb3;
        String newDetail = cate1 + "-" + cate2 + "-" + UUID.randomUUID().toString() + extDetail;

        // 외부 저장 폴더 자동 생성
        File checkFolder = new File(path);

        if(!checkFolder.exists()){
            try {
                Files.createDirectories(checkFolder.toPath());
            }catch (IOException e){
                e.printStackTrace();
            }

        }

        // 파일 저장
        // transferTo : 업로드한 파일 데이터를 지정한 파일에 저장
        try{
            thumb1.transferTo(new File(path, newThumb1));
            thumb2.transferTo(new File(path, newThumb2));
            thumb3.transferTo(new File(path, newThumb3));
            detail.transferTo(new File(path, newDetail));
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        ProductVO file = new ProductVO();
        file.setNewThumb1(newThumb1);
        file.setNewThumb2(newThumb2);
        file.setNewThumb3(newThumb3);
        file.setNewDetail(newDetail);

        return file;

    }

    // 상품 목록 (판매자가 조회 시)
    public List<ProductVO> selectProducts(String seller, int start){
        System.out.println("service: " + seller);
        return dao.selectProducts(seller, start);
    }
    // 상품 목록 (관리자가 조회 시)
    public List<ProductVO> selectProductsAdmin(int start){
        System.out.println("service - admin");
        return dao.selectProductsAdmin(start);
    }
    // 상품 목록 (키워드 검색)
    public List<ProductVO> search(String prodName){

        List<ProdEntity> products = repo.findByProdNameContains(prodName);
        //List<ProdEntity> products = repo.findAll();

        List<ProductVO> productVOList = new ArrayList<>();

        if(products.isEmpty()){
            log.warn("repo - null");
            return productVOList;
        }

        for (ProdEntity product : products){
            productVOList.add(this.convertEntityToVO(product));
        }

        return productVOList;
    }

    private ProductVO convertEntityToVO(ProdEntity product){
        return ProductVO.builder()
                .prodNo(product.getProdNo())
                .cate1(product.getCate1())
                .cate2(product.getCate2())
                .seller(product.getSeller())
                .prodName(product.getProdName())
                .thumb1((product.getThumb1()))
                .price(product.getPrice())
                .discount(product.getDiscount())
                .point(product.getPoint())
                .stock(product.getStock())
                .hit(product.getHit())
                .build();
    }

    // 페이징
    // 전체 게시글 개수
    public int selectCountProduct(String seller) {
        return dao.selectCountProduct(seller);
    }

    // 상품 업데이트
    public int updateProduct(ProductVO vo){
        return dao.updateProduct(vo);
    }
    // 상품 삭제
    public int deleteProduct(String prodNo){
        return dao.deleteProduct(prodNo);
    }







}
