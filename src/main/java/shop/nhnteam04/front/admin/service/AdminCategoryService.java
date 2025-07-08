package shop.nhnteam04.front.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.book.domain.CategoryResponse;
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
}
