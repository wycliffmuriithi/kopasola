package com.kopasolar.database;

import com.kopasolar.database.models.SalesPayments;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * interface name: SalespaymentsRepo
 * Creater: wgicheru
 * Date:2/7/2020
 */
public interface SalespaymentsRepo extends JpaRepository<SalesPayments,Integer> {
}
