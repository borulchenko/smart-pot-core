package com.rborulchenko.spcore.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rborulchenko.spcore.model.PotStatus;

public interface PotStatusRepository extends PagingAndSortingRepository<PotStatus, Long>
{
    List<PotStatus> findAll();

    PotStatus save(PotStatus potStatus);

    Page<PotStatus> findAll(Pageable pageable);
}

