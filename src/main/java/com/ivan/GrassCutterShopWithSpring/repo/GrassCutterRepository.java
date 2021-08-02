package com.ivan.GrassCutterShopWithSpring.repo;

import com.ivan.GrassCutterShopWithSpring.model.GrassCutter;
import com.ivan.GrassCutterShopWithSpring.model.GrassCutterBrand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrassCutterRepository extends JpaRepository <GrassCutter, Long> {
    List<GrassCutter> findAllByBrand(GrassCutterBrand brand);
    List<GrassCutter>  findAllByOrderByPrice();
    List<GrassCutter>  findAllByOrderByPriceDesc();

}
