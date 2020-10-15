package com.example.adapters.web;

import org.openapitools.api.PetsApi;
import org.openapitools.model.Pet;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class PetsController implements PetsApi {
    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    @Override
    public ResponseEntity<Void> createPets() {
        return null;
    }

    @Override
    public ResponseEntity<List<Pet>> listPets(@Valid Integer limit) {
        return null;
    }

    @Override
    public ResponseEntity<Pet> showPetById(String petId) {
        return null;
    }
}
