package shop.nhnteam04.front.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.nhnteam04.front.book.domain.BookCreateRequest;
import shop.nhnteam04.front.book.domain.BookResponse;
import shop.nhnteam04.front.feign.book.BookFeignClient;
import shop.nhnteam04.front.review.service.MinioService;

@Service
@RequiredArgsConstructor
public class AdminBookService {

    private final BookFeignClient bookFeignClient;
    private final MinioService minioService;

    public Page<BookResponse> getBooks(Pageable pageable) {
        return bookFeignClient.getAllBooks(pageable);
    }

    public void createBook(BookCreateRequest bookCreateRequest, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String filePath = minioService.handleImageUpload(file);
            bookCreateRequest.setImageUrl("/images/"+filePath);
        }
        bookFeignClient.createBook(bookCreateRequest);
    }

}
