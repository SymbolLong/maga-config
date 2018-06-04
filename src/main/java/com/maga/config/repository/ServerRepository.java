package com.maga.config.repository;

import com.maga.config.entity.Server;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {

    Server findByApi(String api);

    Page<Server> findByApiLike(String api, Pageable pageable);
}
