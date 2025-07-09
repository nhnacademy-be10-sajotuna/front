// 스크립트가 여러 번 로드되더라도 초기화는 한 번만 실행되도록 방지합니다.
if (typeof window.reviewScriptInitialized === 'undefined') {
    window.reviewScriptInitialized = true;

    document.addEventListener('DOMContentLoaded', () => {
        // 별점 평가 기능 초기화
        initializeStarRating();

        // 이미지 파일 입력 검증 기능 초기화
        initializeImageValidation();
    });

    /**
     * 페이지 내의 모든 별점 평가 UI를 초기화합니다.
     */
    function initializeStarRating() {
        const ratingContainer = document.querySelector('.star-rating');
        if (!ratingContainer) return; // 관련 요소가 없으면 실행 중단

        const ratingInputs = ratingContainer.querySelectorAll('.star-rating__input input[type="radio"]');
        const ratingDisplay = ratingContainer.querySelector('.star-rating__display');

        const updateRatingDisplay = () => {
            const checkedInput = ratingContainer.querySelector('input[type="radio"]:checked');
            if (checkedInput) {
                ratingDisplay.textContent = `( ${checkedInput.value}점 )`;
            } else {
                ratingDisplay.textContent = '( 점수 선택 )';
            }
        };

        ratingInputs.forEach(input => {
            input.addEventListener('change', updateRatingDisplay);
        });

        // 페이지 로드 시 현재 값으로 초기화
        updateRatingDisplay();
    }

    /**
     * 파일 입력(input[type=file])에 대한 이미지 파일 유형 검증을 초기화합니다.
     */
    function initializeImageValidation() {
        const fileInput = document.getElementById('file'); // HTML의 input ID와 일치시킴
        if (!fileInput) return; // 파일 입력 요소가 없으면 실행 중단

        fileInput.addEventListener('change', (event) => {
            const file = event.target.files[0];
            if (file && !file.type.startsWith('image/')) {
                alert('이미지 파일만 업로드할 수 있습니다. (JPG, PNG, GIF 등)');
                event.target.value = ''; // 잘못된 파일 선택 시 입력 초기화
            }
        });
    }
}