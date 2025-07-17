document.addEventListener('DOMContentLoaded', function () {
    // 카테고리 토글 기능
    document.querySelectorAll('.parent-toggle').forEach(button => {
        button.addEventListener('click', () => {
            const childList = button.nextElementSibling;
            if (!childList) return;
            childList.style.display = childList.style.display === 'block' ? 'none' : 'block';
        });
    });

    // 캐러셀 초기화
    initializeCarousels();
});

// 캐러셀 상태 관리
const carouselStates = {};

function initializeCarousels() {
    const carousels = ['popular-carousel', 'newest-carousel'];

    carousels.forEach(carouselId => {
        const carousel = document.getElementById(carouselId);
        const inner = document.getElementById(carouselId + '-inner');

        if (!carousel || !inner) return;

        const items = inner.querySelectorAll('.book-item');
        const itemsPerView = getItemsPerView();

        carouselStates[carouselId] = {
            currentIndex: 0,
            totalItems: items.length,
            itemsPerView: itemsPerView,
            maxIndex: Math.max(0, items.length - itemsPerView)
        };

        updateCarouselButtons(carouselId);
    });
}

function getItemsPerView() {
    const width = window.innerWidth;
    if (width <= 480) return 1;
    if (width <= 1024) return 2;
    return 3;
}

function moveCarousel(carouselId, direction) {
    const state = carouselStates[carouselId];
    if (!state) return;

    const newIndex = state.currentIndex + direction;

    if (newIndex < 0 || newIndex > state.maxIndex) return;

    state.currentIndex = newIndex;

    const inner = document.getElementById(carouselId + '-inner');
    const itemWidth = inner.querySelector('.book-item').offsetWidth + 20; // 20px는 gap

    inner.style.transform = `translateX(-${state.currentIndex * itemWidth}px)`;

    updateCarouselButtons(carouselId);
}

function updateCarouselButtons(carouselId) {
    const carousel = document.getElementById(carouselId);
    const state = carouselStates[carouselId];

    if (!carousel || !state) return;

    const prevBtn = carousel.querySelector('.carousel-nav.prev');
    const nextBtn = carousel.querySelector('.carousel-nav.next');

    prevBtn.disabled = state.currentIndex === 0;
    nextBtn.disabled = state.currentIndex >= state.maxIndex;
}

// 반응형 처리
window.addEventListener('resize', function() {
    initializeCarousels();
});