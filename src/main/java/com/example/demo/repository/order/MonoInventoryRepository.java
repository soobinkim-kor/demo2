package com.example.demo.repository.order;

import com.example.demo.entity.MonoInventoryEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MonoInventoryRepository extends JpaRepository<MonoInventoryEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM MonoInventoryEntity i WHERE i.productNo = :productNo")
    Optional<MonoInventoryEntity> findByProductNoWithLock(Long productNo);
}
