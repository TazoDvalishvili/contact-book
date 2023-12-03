package com.project.contactbook.service;

import com.project.contactbook.dto.ContactDTO;

import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Responsible for contact services.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

public interface ContactService {
    List<ContactDTO> getUserContactBook(Pageable pageable);

    List<ContactDTO> searchContact(ContactDTO contactDTO, Pageable pageable);
    ContactDTO addContact(ContactDTO contactDTO);
    ContactDTO updateContact(Long contactId, ContactDTO contactDTO);
    void deleteContact(Long contactId);
}
