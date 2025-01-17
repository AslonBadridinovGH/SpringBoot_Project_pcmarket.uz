package uz.pdp.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Attachment;
import uz.pdp.entity.Category;
import uz.pdp.payload.CategoryDto;
import uz.pdp.payload.Result;
import uz.pdp.repository.AttachmentContentRepository;
import uz.pdp.repository.AttachmentRepository;
import uz.pdp.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    // CREATE
    public Result addCategory(CategoryDto categoryDto){

    boolean exists = categoryRepository.existsByNameAndParentCategoryId(categoryDto.getName(), categoryDto.getParentCategoryId());
    if (exists)
        return new Result(" such category is already exist",false);

       Category category=new Category();

      category.setName(categoryDto.getName());
      category.setDescription(categoryDto.getDescription());

      if (categoryDto.getParentCategoryId()!=null){
      Optional<Category> optionalParentCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
      if (!optionalParentCategory.isPresent()) {
          return new Result("Category was not found",false);
      }
      category.setParentCategory(optionalParentCategory.get());
    }

        List<Attachment> attachmentList = new ArrayList<>();
        List<Integer> photosIds = categoryDto.getPhotosIds();

        for (Integer photosId : photosIds) {
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(photosId);
            if (!optionalAttachment.isPresent()) {
                return new Result("Attachment was not found",false);
            }
            attachmentList.add(optionalAttachment.get());
        }
      category.setPhotos(attachmentList);

      categoryRepository.save(category);
      return new Result("Category was saved",true);

}

    // GET
    public List<Category> getCategory(){
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    // GET BY ID
    public Category getCategoryById(Integer id){
        Optional<Category> byId = categoryRepository.findById(id);
        if (!byId.isPresent())
         return new Category();
        Category category = byId.get();
        return category;
    }

    // EDIT BY ID
    public Result editCategoryById(CategoryDto categoryDto,Integer id){

        boolean exists = categoryRepository.existsByNameAndParentCategoryIdAndIdNot(categoryDto.getName(),categoryDto.getParentCategoryId(),id);
        if (exists)
            return new Result("such category is already exist",false);

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            return new Result("Category was not found",false);
        }

        Category category = optionalCategory.get();
        category.setName(categoryDto.getName());

        if (categoryDto.getParentCategoryId()!=null){
            Optional<Category> optionalParentCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (!optionalParentCategory.isPresent())
                return new Result("such category is not exist",false);
            category.setParentCategory(optionalParentCategory.get());
        }

        List<Attachment> attachmentList = new ArrayList<>();
        List<Integer> photosIds = categoryDto.getPhotosIds();
        for (Integer photosId : photosIds) {
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(photosId);
            if (!optionalAttachment.isPresent())
                return new Result("Attachment wasn't found",false);
            attachmentList.add(optionalAttachment.get());
        }
        category.setPhotos(attachmentList);

        categoryRepository.save(category);
        return new Result("Category was edited",true);
    }

    // DELETE BY ID
    public Result deleteCategory(Integer id){
        try {
            categoryRepository.deleteById(id);
            return new Result("Category was deleted",true);
        }catch (Exception e){
            return new Result("Category was not deleted",false);
        }

    }


}
