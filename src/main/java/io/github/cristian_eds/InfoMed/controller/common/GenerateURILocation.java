package io.github.cristian_eds.InfoMed.controller.common;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public abstract class GenerateURILocation {

    public static URI generateURI(UUID uuid) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(uuid)
                .toUri();
    }
}
