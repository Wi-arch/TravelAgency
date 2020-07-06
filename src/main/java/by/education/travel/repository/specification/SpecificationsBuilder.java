package by.education.travel.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpecificationsBuilder<T> {

    private final List<SearchCriteria> params = new ArrayList<>();

    public SpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<T> build() {
        if (params.isEmpty()) {
            return null;
        }
        List<Specification> specifications = mapSearchCriteriaToSpecification();
        Specification result = specifications.get(0);
        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                    .isOrPredicate()
                    ? Specification.where(result)
                    .or(specifications.get(i))
                    : Specification.where(result)
                    .and(specifications.get(i));
        }
        return result;
    }

    private List<Specification> mapSearchCriteriaToSpecification() {
        return params.stream()
                .map(UserSpecification::new)
                .collect(Collectors.toList());
    }
}
