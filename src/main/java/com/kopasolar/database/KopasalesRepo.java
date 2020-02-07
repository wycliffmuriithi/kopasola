package com.kopasolar.database;

import com.kopasolar.database.models.KopaSales;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * interface name: KopasalesRepo
 * Creater: wgicheru
 * Date:2/7/2020
 */
public interface KopasalesRepo extends JpaRepository<KopaSales,Integer> {
}
