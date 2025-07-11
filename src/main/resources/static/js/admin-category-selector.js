document.addEventListener('DOMContentLoaded', () => {
    // 카테고리 선택 기능이 필요한 페이지인지 확인
    const container = document.getElementById('category-selects');
    if (!container) return;

    // 공통으로 사용하는 DOM 요소들
    const selectedList = document.getElementById('selected-categories-list');
    const hiddenInput = document.getElementById('categories');
    const addBtn = document.getElementById('add-category-btn');

    // Thymeleaf로부터 전달받은 JSON 데이터
    const allCategoriesDataEl = document.getElementById('all-categories-data');
    const existingCategoriesDataEl = document.getElementById('existing-categories-data');

    const allCategories = JSON.parse(allCategoriesDataEl.textContent);
    const existingCategoryPaths = existingCategoriesDataEl ? JSON.parse(existingCategoriesDataEl.textContent) : [];

    let currentPath = []; // 현재 선택 중인 카테고리 ID 경로
    let selectedFinalCategoryIds = []; // 최종 선택된 카테고리 ID 목록

    /**
     * 숨겨진 input 태그의 값을 업데이트하는 함수
     */
    function updateHiddenInput() {
        hiddenInput.value = selectedFinalCategoryIds.join(',');
    }

    /**
     * 선택된 카테고리 경로를 화면의 리스트(ul)에 추가하는 함수
     * @param {Array<Object>} path - 카테고리 객체들의 배열 (예: [{id:1, name:'국내도서'}, {id:2, name:'소설'}])
     */
    function addCategoryPathToList(path) {
        if (!Array.isArray(path) || path.length === 0) return;

        const finalCategory = path[path.length - 1];
        if (selectedFinalCategoryIds.includes(finalCategory.id)) {
            alert('이미 추가된 카테고리입니다.');
            return;
        }

        const pathNames = path.map(cat => cat.name).join(' > ');

        const li = document.createElement('li');
        li.textContent = pathNames;
        li.dataset.id = finalCategory.id;

        const removeBtn = document.createElement('button');
        removeBtn.textContent = '❌';
        removeBtn.type = 'button';
        removeBtn.style.cssText = 'background:none; border:none; cursor:pointer; margin-left:0.5rem; vertical-align:middle;';
        removeBtn.onclick = () => {
            selectedFinalCategoryIds = selectedFinalCategoryIds.filter(id => id !== finalCategory.id);
            selectedList.removeChild(li);
            updateHiddenInput();
        };

        li.appendChild(removeBtn);
        selectedList.appendChild(li);

        selectedFinalCategoryIds.push(finalCategory.id);
        updateHiddenInput();
    }

    /**
     * 하위 카테고리 select 박스를 렌더링하는 함수
     * @param {number|null} parentId - 부모 카테고리 ID (최상위는 null)
     * @param {number} depth - 현재 렌더링 깊이
     */
    function renderCategorySelect(parentId = null, depth = 0) {
        // 현재 깊이보다 더 깊은 select 박스들 제거
        while (container.children.length > depth) {
            container.removeChild(container.lastChild);
        }

        const children = allCategories.filter(c => parentId === null ? c.parentId === null : c.parentId === parentId);
        if (children.length === 0) return; // 하위 카테고리가 없으면 종료

        const select = document.createElement('select');
        select.innerHTML = `<option value="">-- ${depth + 1}차 카테고리 --</option>`;
        children.forEach(c => {
            const option = new Option(c.name, c.id);
            select.add(option);
        });

        select.addEventListener('change', () => {
            currentPath = currentPath.slice(0, depth); // 현재 깊이 이전 경로 유지
            const selectedId = select.value ? parseInt(select.value) : null;
            if (selectedId) {
                currentPath.push(selectedId);
            }
            renderCategorySelect(selectedId, depth + 1);
        });

        container.appendChild(select);
    }

    // "카테고리 추가" 버튼 이벤트 리스너
    addBtn.addEventListener('click', () => {
        if (currentPath.length === 0) return alert('추가할 카테고리를 선택하세요.');
        const fullPath = currentPath.map(id => allCategories.find(c => c.id === id));
        addCategoryPathToList(fullPath);
    });

    // --- 초기화 로직 ---
    // 1. 도서 수정 페이지의 경우, 기존 카테고리 목록을 렌더링
    existingCategoryPaths.forEach(path => {
        addCategoryPathToList(path);
    });
    // 2. 첫 번째 카테고리 select 박스 렌더링
    renderCategorySelect();
});