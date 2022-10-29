package com.zwei.testb.repository;

import com.zwei.testb.entities.DataSet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DataSetRepository extends CrudRepository<DataSet, Integer> {
    @Query("select d from DataSet d "
            + "where d.share.ticker = :ticker")
    List<DataSet> findByTicker(String ticker);
}
