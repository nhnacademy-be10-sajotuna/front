package shop.nhnteam04.front.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.book.dto.response.CategoryResponse;
import shop.nhnteam04.front.book.dto.request.CategoryCreateRequest;
import shop.nhnteam04.front.feign.book.BookFeignClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCategoryService {
    private final BookFeignClient bookFeignClient;

    public List<CategoryResponse> getAllCategories() {
        Page<CategoryResponse> page = bookFeignClient.getAllCategories();
        return page.getContent();
    }

    public Page<CategoryResponse> getAllCategoriesByParentId(Pageable pageable, Long parentId) {
        return bookFeignClient.getAllCategoriesByParentId(pageable, parentId);
    }

    public List<CategoryResponse> getParentCategories(Long id) {
        return bookFeignClient.getParentCategories(id);
    }

    public void createCategory(CategoryCreateRequest createRequest) {
        bookFeignClient.createCategory(createRequest);
    }

    public void deleteCategory(String id) {
        bookFeignClient.deleteCategory(id);
    }
}
