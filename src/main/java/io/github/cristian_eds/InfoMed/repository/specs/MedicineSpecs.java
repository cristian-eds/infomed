package io.github.cristian_eds.InfoMed.repository.specs;

import io.github.cristian_eds.InfoMed.models.Medicine;
import io.github.cristian_eds.InfoMed.models.MedicineItem;
import io.github.cristian_eds.InfoMed.models.Person;
import io.github.cristian_eds.InfoMed.models.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class MedicineSpecs {

    public static Specification<Medicine> nameLike(String name) {
        return (root, query, cb) ->
                cb.like(
                        cb.upper(root.get("name"))
                        , "%"+name.toUpperCase()+"%"
                );
    }

    public static Specification<Medicine> userEquals(User user) {
        return (root, query, cb) ->
                cb.equal(root.get("user"),user);
    }

    public static Specification<Medicine> personNameLike(String name) {
        return (root, query, cb) ->{
                Join<Medicine, Person > personJoin = root.join("person", JoinType.LEFT);
                return cb.like(
                        cb.upper(personJoin.get("name")),
                        "%"+name.toUpperCase()+"%"
                );
        };
    }

    public static Specification<Medicine> nameOrPersonNameLike(String name) {
        return (root, query, cb) -> {
            Join<Medicine, Person > personJoin = root.join("person", JoinType.LEFT);
            Predicate namePredicate = cb.like(
                    cb.upper(root.get("name")),
                    "%" + name.toUpperCase() + "%"
            );

            Predicate personNamePredicate = cb.like(
                    cb.upper(personJoin.get("name")),
                    "%" + name.toUpperCase() + "%"
            );
            return cb.or(namePredicate,personNamePredicate);
        };
    }
}
