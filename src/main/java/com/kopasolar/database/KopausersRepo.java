package com.kopasolar.database;

import com.kopasolar.database.models.KopaUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * interface name: KopausersRepo
 * Creater: wgicheru
 * Date:1/27/2020
 */
public interface KopausersRepo extends JpaRepository<KopaUsers,Long> {
    int countByUsernameOrEmail(String username,String email);


    List<KopaUsers> findByActivationtokenAndActiveFalse(int token);
    List<KopaUsers> findByEmail(String email);
}
