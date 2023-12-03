package com.project.contactbook.repository;

import com.project.contactbook.model.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Contact repository responsible for managing db operations for contact entity.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByCreatorId(Long creatorId, Pageable pageable);

    Optional<Contact> findByIdAndCreatorId(Long id, Long creatorId);

    boolean existsByIdAndCreatorId(Long id, Long creatorId);

    @Query(value = """
            SELECT * FROM contacts c
            WHERE (:id IS NULL OR c.id = :id)
              AND (c.creator_id = :creatorId)
              AND (:firstName IS NULL OR c.first_name LIKE %:firstName%)
              AND (:lastName IS NULL OR c.last_name LIKE %:lastName%)
              AND (:phoneNumber IS NULL OR c.phone_number LIKE %:phoneNumber%)
              AND (:mail IS NULL OR c.mail LIKE %:mail%)
              AND (:address IS NULL OR c.address LIKE %:address%)
            """, nativeQuery = true)
    List<Contact> searchContacts(
            @Param("id") Long id,
            @Param("creatorId") Long creatorId,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("phoneNumber") String phoneNumber,
            @Param("mail") String mail,
            @Param("address") String address,
            Pageable pageable
    );

}
