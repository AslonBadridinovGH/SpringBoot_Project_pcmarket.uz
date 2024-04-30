package uz.pdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.entity.Product;


public interface ProductRepository extends JpaRepository<Product,Integer> {

      boolean existsByNameAndPhotoId(String name, Integer photo_id);

      boolean existsByNameAndPhotoIdNot(String name, Integer photo_id);

   /*

    boolean  existsByNameAndCategoryId(String name, Integer category_id);

    boolean  existsByNameAndCategoryIdAndIdNot(String name, Integer category_id, Integer id);

    boolean existsByNameAndCategories(String name, List<Category> categories);

    boolean existsByNameAndCategoriesNot(String name, List<Category> categories);
*/

}
