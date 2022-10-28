package com.zwei.testb.repository;

import com.zwei.testb.entities.Share;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ShareRepository extends CrudRepository<Share, String> {
    Page<Share> findAll(Pageable giveMePage);

    @Query("select distinct s from Share s")
    List<Share> findAllShare();
}
