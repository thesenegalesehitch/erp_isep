package com.isep.repository;

import com.isep.model.Activity;
import com.isep.model.ActivityRegistration;
import com.isep.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRegistrationRepository extends JpaRepository<ActivityRegistration, Long> {
    List<ActivityRegistration> findByActivity(Activity activity);
    List<ActivityRegistration> findByUser(User user);
}

