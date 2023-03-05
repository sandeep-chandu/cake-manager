package com.waracle.cakemgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waracle.cakemgr.domain.Cake;

public interface CakeRepo extends JpaRepository<Cake, Long> {

}
