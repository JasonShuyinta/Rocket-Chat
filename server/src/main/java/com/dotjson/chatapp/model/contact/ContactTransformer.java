package com.dotjson.chatapp.model.contact;

public class ContactTransformer {

    public ContactResponse modelToDto(Contact model) {
        ContactResponse response = new ContactResponse();
        return response;
    }

    public Contact dtoToModel(ContactRequest dto) {
        Contact contact = new Contact();
        contact.setId(dto.getId());
        return contact;
    }
}
