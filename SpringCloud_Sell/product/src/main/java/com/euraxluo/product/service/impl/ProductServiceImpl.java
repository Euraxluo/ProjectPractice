package com.euraxluo.product.service.impl;

import com.euraxluo.product.dataobject.ProductInfo;
import com.euraxluo.product.enums.ProductStatusEnum;
import com.euraxluo.product.repository.ProductInfoRepository;
import com.euraxluo.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * product
 * com.euraxluo.product.service.impl
 * ProductServiceImpl
 * 2020/6/14 12:31
 * author:Euraxluo
 * TODO
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

//    @Autowired
//    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

//    @Override
//    public List<ProductInfoOutput> findList(List<String> productIdList) {
//        return productInfoRepository.findByProductIdIn(productIdList).stream()
//                .map(e -> {
//                    ProductInfoOutput output = new ProductInfoOutput();
//                    BeanUtils.copyProperties(e, output);
//                    return output;
//                })
//                .collect(Collectors.toList());
//    }

//    @Override
//    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
//        List<ProductInfo> productInfoList = decreaseStockProcess(decreaseStockInputList);
//
//        //发送mq消息
//        List<ProductInfoOutput> productInfoOutputList = productInfoList.stream().map(e -> {
//            ProductInfoOutput output = new ProductInfoOutput();
//            BeanUtils.copyProperties(e, output);
//            return output;
//        }).collect(Collectors.toList());
//        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoOutputList));
//
//    }
//
//    @Transactional
//    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList) {
//        List<ProductInfo> productInfoList = new ArrayList<>();
//        for (DecreaseStockInput decreaseStockInput: decreaseStockInputList) {
//            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(decreaseStockInput.getProductId());
//            //判断商品是否存在
//            if (!productInfoOptional.isPresent()){
//                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
//            }
//
//            ProductInfo productInfo = productInfoOptional.get();
//            //库存是否足够
//            Integer result = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
//            if (result < 0) {
//                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
//            }
//
//            productInfo.setProductStock(result);
//            productInfoRepository.save(productInfo);
//            productInfoList.add(productInfo);
//        }
//        return productInfoList;
//    }
}
