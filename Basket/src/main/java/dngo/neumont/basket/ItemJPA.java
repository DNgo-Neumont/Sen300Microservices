package dngo.neumont.basket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemJPA extends JpaRepository<Item, Integer> {
    Item findItemById(Long id);
}
