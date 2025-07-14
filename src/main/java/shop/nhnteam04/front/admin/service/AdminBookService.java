package shop.nhnteam04.front.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.nhnteam04.front.book.dto.request.BookCreateRequest;
import shop.nhnteam04.front.book.dto.response.BookResponse;
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

    public BookResponse getBook(String isbn) {
        return bookFeignClient.getBook(isbn);
    }

    public void updateBook(String isbn, BookCreateRequest bookCreateRequest, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String oldImageUrl = bookCreateRequest.getImageUrl();
            minioService.deleteFile(oldImageUrl.substring(8));
            String filePath = minioService.handleImageUpload(file);
            bookCreateRequest.setImageUrl("/images/"+filePath);
        }
        bookCreateRequest.setIsbn(isbn);
        bookFeignClient.updateBook(isbn, bookCreateRequest);
    }

    public void deleteBook(String isbn) {
        bookFeignClient.deleteBook(isbn);
    }

    public void importBook(String keyword) {
        bookFeignClient.importBook(keyword, 4);
    }
}
