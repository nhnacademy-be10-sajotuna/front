package shop.nhnteam04.front.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import shop.nhnteam04.front.book.dto.response.CategoryResponse;
import shop.nhnteam04.front.feign.book.BookFeignClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final BookFeignClient bookFeignClient;

    @Cacheable(value = "categories")
    public List<CategoryResponse> getAll(){
        Page<CategoryResponse> page = bookFeignClient.getAllCategories();
        return page.getContent();
    }
}
