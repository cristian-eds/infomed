package io.github.cristian_eds.InfoMed.repository.specs;

import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.models.MedicineItem;
import org.springframework.data.jpa.domain.Specification;

public class MedicineSpecs {

    public static Specification<Medicine> nameLike(String name) {
        return (root, query, cb) ->
                cb.like(
                        cb.upper(root.get("name"))
                        , "%"+name.toUpperCase()+"%"
                );
    }
}
