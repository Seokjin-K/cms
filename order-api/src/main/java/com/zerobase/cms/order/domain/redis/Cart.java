package com.zerobase.cms.order.domain.redis;

import com.zerobase.cms.order.domain.product.AddProductCartForm;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(":cart") // key 의 prefix
public class Cart {

    @Id
    private Long customerId;
    private List<Product> products;
    private List<String> messages;

    public void addMessage(String message){
        messages.add(message);
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Product {
        private Long id;
        private Long sellerId;
        private String name;
        private String description;
        private List<ProductItem> items;

        public static Product from(AddProductCartForm form) {
            return Product.builder()
                    .id(form.getId())
                    .sellerId(form.getSellerId())
                    .name(form.getName())
                    .description(form.getDescription())
                    .items(
                            form.getItems()
                                    .stream()
                                    .map(ProductItem::from)
                                    .collect(Collectors.toList())
                    )
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductItem {
        private Long id;
        private String name;
        private Integer count;
        private Integer price;

        public static ProductItem from(AddProductCartForm.ProductItem form) {

            return ProductItem.builder()
                    .id(form.getId())
                    .name(form.getName())
                    .count(form.getCount())
                    .price(form.getPrice())
                    .build();
        }
    }
}
