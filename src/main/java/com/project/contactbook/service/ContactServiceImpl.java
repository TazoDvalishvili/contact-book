package com.project.contactbook.service;


import com.project.contactbook.dto.ContactDTO;
import com.project.contactbook.model.Contact;
import com.project.contactbook.repository.ContactRepository;
import com.sun.jdi.InternalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ContactServiceImpl implements ContactService interface.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService{

    private final ContactRepository contactRepository;

    private final UserService userService;

    @Override
    public List<ContactDTO> getUserContactBook(Pageable pageable) {
        try {
            List<Contact> contacts = contactRepository.findAllByCreatorId(userService.getCurrentUser().getId(), pageable);
            return contacts.stream().map(contact -> new ContactDTO(contact.getId(), contact.getFirstName(),
                    contact.getLastName(), contact.getPhoneNumber(), contact.getMail(), contact.getAddress())).collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalException("Couldn't fetch contact book for current user.");
        }
    }

    @Override
    public List<ContactDTO> searchContact(ContactDTO contactDTO, Pageable pageable) {
        try {
            List<Contact> contacts = contactRepository.searchContacts(contactDTO.getId(), userService.getCurrentUser().getId(),
                    contactDTO.getFirstName(), contactDTO.getLastName(), contactDTO.getPhoneNumber(), contactDTO.getMail(), contactDTO.getAddress(), pageable);
            return contacts.stream().map(contact -> new ContactDTO(contact.getId(), contact.getFirstName(),
                    contact.getLastName(), contact.getPhoneNumber(), contact.getMail(), contact.getAddress())).collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalException("Couldn't execute contact search");
        }
    }

    @Override
    public ContactDTO addContact(ContactDTO contactDTO) {
        try {
            Contact contact = new Contact();
            contact.setCreatorId(userService.getCurrentUser().getId());
            contact.setFirstName(contactDTO.getFirstName());
            contact.setLastName(contactDTO.getLastName());
            contact.setPhoneNumber(contactDTO.getPhoneNumber());
            contact.setMail(contactDTO.getMail());
            contact.setAddress(contactDTO.getAddress());
            Contact newContact = contactRepository.save(contact);
            return new ContactDTO(newContact.getId(), newContact.getFirstName(),
                    newContact.getLastName(), newContact.getPhoneNumber(), newContact.getMail(), newContact.getAddress());
        } catch (Exception e) {
            throw new InternalException("Couldn't save contact");
        }
    }

    @Override
    public ContactDTO updateContact(Long contactId, ContactDTO contactDTO) {
        Optional<Contact> contactOptional = contactRepository.findByIdAndCreatorId(contactId, userService.getCurrentUser().getId());
        if(contactOptional.isEmpty()) {
            throw new InternalException("Contact with provided id doesn't exist");
        }
        try {
            Contact contact = contactOptional.get();
            contact.setFirstName(contactDTO.getFirstName());
            contact.setMail(contactDTO.getMail());
            contact.setLastName(contactDTO.getLastName());
            contact.setPhoneNumber(contactDTO.getPhoneNumber());
            contact.setAddress(contactDTO.getAddress());
            Contact updatedContact = contactRepository.save(contact);
            return new ContactDTO(updatedContact.getId(), updatedContact.getFirstName(),
                    updatedContact.getLastName(), updatedContact.getPhoneNumber(), updatedContact.getMail(), updatedContact.getAddress());
        } catch (Exception e) {
            throw new InternalException("Couldn't update contact");
        }
    }

    @Override
    public void deleteContact(Long contactId) {
        if(!contactRepository.existsByIdAndCreatorId(contactId, userService.getCurrentUser().getId())) {
            throw new InternalException("Contact with provided id doesn't exist");
        }
        try {
            contactRepository.deleteById(contactId);
        } catch (Exception e) {
            throw new InternalException("Couldn't delete contact");

        }

    }
}
