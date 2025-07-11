/**
 * 도서 관리(생성/수정) 페이지의 카테고리 선택 로직을 처리하는 모듈
 * @param {object} config - 설정 객체
 * @param {string} config.allCategoriesJson - 모든 카테고리 정보 JSON 문자열
 * @param {string} [config.existingCategoriesJson='[]'] - (수정 시) 기존에 선택된 카테고리 경로 정보 JSON 문자열
 */
function setupCategorySelector(config) {
    const { allCategoriesJson, existingCategoriesJson = '[]' } = config;

    // JSON 문자열을 객체로 파싱
    const allCategories = JSON.parse(allCategoriesJson);
    const existingCategoryPaths = JSON.parse(existingCategoriesJson);

    // DOM 요소 가져오기
    const container = document.getElementById('category-selects');
    const selectedList = document.getElementById('selected-categories-list');
    const hiddenInput = document.getElementById('categories');
    const addBtn = document.getElementById('add-category-btn');

    // 필수 DOM 요소가 없는 경우 오류를 출력하고 종료
    if (!container || !selectedList || !hiddenInput || !addBtn) {
        console.error('카테고리 관리에 필요한 DOM 요소가 없습니다.');
        return;
    }

    let currentSelectPath = [];      // 현재 드롭다운에서 선택 중인 카테고리 ID 경로
    let finalSelectedCategoryIds = []; // 최종적으로 선택 완료된 카테고리 ID 목록

    /**
     * Hidden Input의 값을 업데이트하는 함수
     */
    function updateHiddenInput() {
        hiddenInput.value = finalSelectedCategoryIds.join(',');
    }

    /**
     * 선택된 카테고리 경로를 UI에 리스트 아이템으로 추가하는 함수
     * @param {number[]} idPath - 카테고리 ID 경로 배열
     * @param {string[]} namePath - 카테고리 이름 경로 배열
     */
    function addCategoryPathToList(idPath, namePath) {
        if (!idPath || idPath.length === 0) return;

        const lastId = idPath[idPath.length - 1];
        if (finalSelectedCategoryIds.includes(lastId)) {
            alert('이미 추가된 카테고리입니다.');
            return;
        }

        const li = document.createElement('li');
        li.textContent = namePath.join(' > ');
        li.dataset.id = lastId; // 삭제를 위해 ID 저장

        const removeBtn = document.createElement('button');
        removeBtn.textContent = '❌';
        removeBtn.type = 'button';
        removeBtn.style.marginLeft = '0.5rem';
        removeBtn.onclick = () => {
            finalSelectedCategoryIds = finalSelectedCategoryIds.filter(id => id !== lastId);
            selectedList.removeChild(li);
            updateHiddenInput();
        };

        li.appendChild(removeBtn);
        selectedList.appendChild(li);

        finalSelectedCategoryIds.push(lastId);
        updateHiddenInput();
    }

    /**
     * 부모 ID에 따라 하위 카테고리 select 엘리먼트를 렌더링하는 함수
     * @param {number|null} parentId - 부모 카테고리 ID (최상위는 null)
     * @param {number} depth - 현재 카테고리의 깊이
     */
    function renderCategorySelect(parentId = null, depth = 0) {
        // 현재 깊이 이후의 select 박스는 모두 제거
        while (container.children.length > depth) {
            container.removeChild(container.lastChild);
        }
        currentSelectPath = currentSelectPath.slice(0, depth);

        const filtered = allCategories.filter(c => parentId === null ? c.parentId === null : c.parentId === parentId);
        if (filtered.length === 0) return; // 하위 카테고리가 없으면 종료

        const select = document.createElement('select');
        select.innerHTML = `<option value="">선택하세요</option>`;
        filtered.forEach(c => {
            const option = document.createElement('option');
            option.value = c.id;
            option.textContent = c.name;
            select.appendChild(option);
        });

        select.addEventListener('change', () => {
            const selectedId = select.value ? parseInt(select.value, 10) : null;
            if (selectedId) {
                currentSelectPath = currentSelectPath.slice(0, depth);
                currentSelectPath.push(selectedId);
                renderCategorySelect(selectedId, depth + 1);
            } else {
                renderCategorySelect(parentId, depth); // 선택 취소 시 현재 레벨 다시 렌더링
            }
        });

        container.appendChild(select);
    }

    // "카테고리 추가" 버튼 이벤트 리스너
    addBtn.addEventListener('click', () => {
        if (currentSelectPath.length === 0) {
            alert('카테고리를 선택하세요.');
            return;
        }
        const namePath = currentSelectPath.map(id => allCategories.find(c => c.id === id)?.name);
        addCategoryPathToList(currentSelectPath, namePath);
    });

    // --- 초기화 로직 ---

    // (수정 페이지용) 기존 카테고리가 있으면 먼저 목록에 추가
    if (existingCategoryPaths && existingCategoryPaths.length > 0) {
        existingCategoryPaths.forEach(path => {
            const idPath = path.map(cat => cat.id);
            const namePath = path.map(cat => cat.name);
            addCategoryPathToList(idPath, namePath);
        });
    }

    // 첫번째 카테고리 select 렌더링
    renderCategorySelect();
}