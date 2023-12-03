package com.project.contactbook.repository;

import com.project.contactbook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Contact repository responsible for managing db operations for contact entity.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
