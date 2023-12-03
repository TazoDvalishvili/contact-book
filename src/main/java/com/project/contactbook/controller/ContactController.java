package com.project.contactbook.controller;


import com.project.contactbook.dto.ContactDTO;
import com.project.contactbook.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contact controller: managing crud operations for contact.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@RestController
@Slf4j
@RequestMapping("contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("add-contact")
    public ResponseEntity<?> addContact(@RequestBody ContactDTO contactDTO) {
        try {
            ContactDTO response = contactService.addContact(contactDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("get-contact-book")
    public ResponseEntity<?> getContactBook(@RequestParam(value = "limit", required = false, defaultValue = "5") int size,
                                            @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        try {
            List<ContactDTO> response = contactService.getUserContactBook(PageRequest.of(page - 1,
                    size, Sort.Direction.DESC, "id"));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("search-contact")
    public ResponseEntity<?> searchContact(@RequestBody ContactDTO contactDTO,
                                           @RequestParam(value = "limit", required = false, defaultValue = "5") int size,
                                           @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        try {
            List<ContactDTO> response = contactService.searchContact(contactDTO, PageRequest.of(page - 1,
                    size, Sort.Direction.DESC, "id"));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("update-contact/{id}")
    public ResponseEntity<?> updateContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        try {
            ContactDTO response = contactService.updateContact(id, contactDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("delete-contact/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        try {
            contactService.deleteContact(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
