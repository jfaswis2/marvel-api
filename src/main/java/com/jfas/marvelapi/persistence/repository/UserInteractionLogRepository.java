package com.jfas.marvelapi.persistence.repository;

import com.jfas.marvelapi.persistence.entity.UserInteractionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInteractionLogRepository extends JpaRepository<UserInteractionLog, Long> {
}
