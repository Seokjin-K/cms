package com.zerobase.cms.order.domain.repository;

import com.zerobase.cms.order.domain.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends
        JpaRepository<Product, Long>,
        ProductRepositoryCustom {

    Optional<Product> findBySellerIdAndId(Long sellerId, Long id);

    // productItems 를 같이 로드
    @EntityGraph(attributePaths = {"productItems"},
            type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> findWithProductItemById(Long id);
}
