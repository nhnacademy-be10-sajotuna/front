package shop.nhnteam04.front.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import shop.nhnteam04.front.book.domain.request.TagRequest;
import shop.nhnteam04.front.book.domain.response.TagResponse;
import shop.nhnteam04.front.feign.book.BookFeignClient;

@Service
@RequiredArgsConstructor
public class AdminTagService {

    private final BookFeignClient bookFeignClient;

    public Page<TagResponse> getTags(Pageable pageable) {
        return bookFeignClient.getAllTags(pageable);
    }

    public void createTag(@RequestBody TagRequest tagRequest) {
        bookFeignClient.createTag(tagRequest);
    }

    public void deleteTag(Long id) {
        bookFeignClient.deleteTag(id);
    }
}
