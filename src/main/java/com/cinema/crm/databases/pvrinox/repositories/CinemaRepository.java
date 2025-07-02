package com.cinema.crm.databases.pvrinox.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cinema.crm.databases.pvrinox.entities.Cinema;

import java.util.Date;
import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    List<Cinema> findAllByChainKeyAndPos(String chainKey, String pos);

    List<Cinema> findAllByChainKeyAndPosAndCityIdInAndActive(String chainKey, String pos, List<Integer> cityIds, Boolean active);

    Cinema findByTheatreId(String cCode);

    List<Cinema> findByTheatreIdInAndVakaao(List<String> cCode,Boolean vakaao);

    List<Cinema> findAllByChainKeyAndPosAndActive(String chainKey, String pos, Boolean active);

    @Modifying
    @Transactional
    @Query(value = "UPDATE location_cinema SET refresh_at = :refreshAt, sync_status = :syncStatus, err_data = :errData WHERE id = :cinemaId", nativeQuery = true)
    void updateCinemaFields(@Param("cinemaId") Long cinemaId, @Param("refreshAt") Date refreshAt, @Param("syncStatus") Boolean syncStatus, @Param("errData") String errData);

    @Query(value = "SELECT c.theatreId FROM Cinema c WHERE c.active = :active")
    List<Object[]> getTheatreIdByActive(@Param("active") Boolean active);
    
    List<Cinema> findAllByChainKeyAndPosAndCityIdInAndActiveAndVakaao(String chainKey, String pos, List<Integer> cityIds, Boolean active, Boolean vakaao);

    @Query(value = "SELECT c.theatreId FROM Cinema c WHERE c.active = :active")
    List<String> findTheatreIdByActive(boolean active);
}
