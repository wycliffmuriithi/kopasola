package com.kopasolar.database;

import com.kopasolar.database.models.KopaProducts;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * interface name: KopaproductsRepo
 * Creater: wgicheru
 * Date:1/27/2020
 */
public interface KopaproductsRepo extends JpaRepository<KopaProducts,Integer> {
    int countByName(String name);
}
