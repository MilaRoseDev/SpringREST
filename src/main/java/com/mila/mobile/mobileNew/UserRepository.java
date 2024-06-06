package com.mila.mobile.mobileNew;

import com.mila.mobile.mobileNew.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


// -- query database  with CRUD--
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);


}
