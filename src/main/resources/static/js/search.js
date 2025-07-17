document.addEventListener('DOMContentLoaded', function () {
    const input = document.getElementById('searchInput');
    const autoList = document.getElementById('autocomplete-list');

    input.addEventListener('input', async () => {
        const keyword = input.value.trim();
        if (!keyword) {
            autoList.innerHTML = '';
            return;
        }

        const response = await fetch('/book/search/autocomplete?keyword=' + encodeURIComponent(keyword));
        if (!response.ok) return;
        const suggestions = await response.json();

        autoList.innerHTML = '';
        suggestions.forEach(suggestion => {
            const li = document.createElement('li');
            li.textContent = suggestion;
            li.style.padding = '4px';
            li.style.cursor = 'pointer';
            li.addEventListener('click', () => {
                input.value = suggestion;
                autoList.innerHTML = '';
                document.querySelector('.search-form').submit();
            });
            autoList.appendChild(li);
        });

    });

    document.addEventListener('click', (e) => {
        if (!autoList.contains(e.target) && e.target !== input) {
            autoList.innerHTML = '';
        }
    });

    // 하위 카테고리 로드
    loadSubcategories();
});

async function loadSubcategories() {
    const urlParams = new URLSearchParams(window.location.search);
    const categoryId = urlParams.get('category');
    
    if (!categoryId) {
        document.getElementById('category-tree').innerHTML = '<p>카테고리를 선택해주세요</p>';
        return;
    }

    try {
        const response = await fetch(`/categories/${categoryId}/subcategories`);
        if (!response.ok) {
            throw new Error('하위 카테고리를 불러올 수 없습니다');
        }
        
        const allSubcategories = await response.json();
        const categoryTree = document.getElementById('category-tree');
        
        // 현재 카테고리의 직접 하위 카테고리들만 필터링
        const directSubcategories = allSubcategories.filter(cat => 
            cat.parentId && cat.parentId.toString() === categoryId
        );
        
        if (directSubcategories.length === 0) {
            categoryTree.innerHTML = '<p>하위 카테고리가 없습니다</p>';
            return;
        }
        
        // 직접 하위 카테고리들을 트리 구조로 렌더링
        renderCategoryTree(directSubcategories, categoryTree);
        
    } catch (error) {
        console.error('하위 카테고리 로드 실패:', error);
        document.getElementById('category-tree').innerHTML = '<p>하위 카테고리를 불러올 수 없습니다</p>';
    }
}

function renderCategoryTree(categories, container) {
    container.innerHTML = '';
    
    categories.forEach(category => {
        const categoryItem = document.createElement('div');
        categoryItem.className = 'category-item';
        
        const categoryHeader = document.createElement('div');
        categoryHeader.className = 'category-header';
        
        const toggleButton = document.createElement('button');
        toggleButton.className = 'category-toggle';
        toggleButton.textContent = '+';
        toggleButton.onclick = () => toggleCategory(category.id, toggleButton);
        
        const categoryLink = document.createElement('a');
        categoryLink.href = `/book/search?category=${category.id}`;
        categoryLink.textContent = category.name;
        categoryLink.className = 'category-link';
        
        categoryHeader.appendChild(toggleButton);
        categoryHeader.appendChild(categoryLink);
        
        const subcategoryContainer = document.createElement('div');
        subcategoryContainer.className = 'subcategory-container';
        subcategoryContainer.id = `subcategory-${category.id}`;
        subcategoryContainer.style.display = 'none';
        
        categoryItem.appendChild(categoryHeader);
        categoryItem.appendChild(subcategoryContainer);
        container.appendChild(categoryItem);
    });
}

async function toggleCategory(categoryId, toggleButton) {
    const subcategoryContainer = document.getElementById(`subcategory-${categoryId}`);
    
    if (subcategoryContainer.style.display === 'none') {
        // 펼치기
        if (subcategoryContainer.children.length === 0) {
            // 하위 카테고리가 아직 로드되지 않았다면 로드
            try {
                const response = await fetch(`/categories/${categoryId}/subcategories`);
                if (response.ok) {
                    const allSubcategories = await response.json();
                    // 해당 카테고리의 직접 하위 카테고리들만 필터링
                    const directSubcategories = allSubcategories.filter(cat => 
                        cat.parentId && cat.parentId.toString() === categoryId.toString()
                    );
                    renderSubcategories(directSubcategories, subcategoryContainer);
                }
            } catch (error) {
                console.error('하위 카테고리 로드 실패:', error);
                subcategoryContainer.innerHTML = '<p class="error">하위 카테고리를 불러올 수 없습니다</p>';
            }
        }
        subcategoryContainer.style.display = 'block';
        toggleButton.textContent = '-';
    } else {
        // 접기
        subcategoryContainer.style.display = 'none';
        toggleButton.textContent = '+';
    }
}

function renderSubcategories(subcategories, container) {
    container.innerHTML = '';
    
    if (subcategories.length === 0) {
        container.innerHTML = '<p class="no-subcategories">하위 카테고리가 없습니다</p>';
        return;
    }
    
    subcategories.forEach(subcategory => {
        const subcategoryItem = document.createElement('div');
        subcategoryItem.className = 'subcategory-item';
        
        const subcategoryHeader = document.createElement('div');
        subcategoryHeader.className = 'subcategory-header';
        
        const toggleButton = document.createElement('button');
        toggleButton.className = 'subcategory-toggle';
        toggleButton.textContent = '+';
        toggleButton.onclick = () => toggleCategory(subcategory.id, toggleButton);
        
        const subcategoryLink = document.createElement('a');
        subcategoryLink.href = `/book/search?category=${subcategory.id}`;
        subcategoryLink.textContent = subcategory.name;
        subcategoryLink.className = 'subcategory-link';
        
        subcategoryHeader.appendChild(toggleButton);
        subcategoryHeader.appendChild(subcategoryLink);
        
        const subSubcategoryContainer = document.createElement('div');
        subSubcategoryContainer.className = 'subcategory-container';
        subSubcategoryContainer.id = `subcategory-${subcategory.id}`;
        subSubcategoryContainer.style.display = 'none';
        
        subcategoryItem.appendChild(subcategoryHeader);
        subcategoryItem.appendChild(subSubcategoryContainer);
        container.appendChild(subcategoryItem);
    });
}
