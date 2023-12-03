package com.project.contactbook.dto;


import lombok.*;

/**
 * Data transfer object for Contact.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {
    private Long id;
    private Long creatorId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String mail;
    private String address;
}
