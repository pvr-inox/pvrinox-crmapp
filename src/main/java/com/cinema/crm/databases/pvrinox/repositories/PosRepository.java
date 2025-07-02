package com.cinema.crm.databases.pvrinox.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.crm.databases.pvrinox.entities.Pos;

import java.util.List;

@Repository
public interface PosRepository extends JpaRepository<Pos, Long> {

    List<Pos> findAllByChainKeyAndActive(String id, boolean active);

    List<Pos> findAllByActive(boolean b);

    Pos findByChainKeyAndNameAndActive(String chainKey, String pos, boolean b);

    Pos findByName(String posName);
}
