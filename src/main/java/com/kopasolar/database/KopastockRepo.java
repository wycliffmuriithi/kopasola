package com.kopasolar.database;

import com.kopasolar.database.models.KopaStock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * interface name: KopastockRepo
 * Creater: wgicheru
 * Date:1/27/2020
 */
public interface KopastockRepo extends JpaRepository<KopaStock,Long> {
}
