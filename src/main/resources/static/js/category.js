document.addEventListener('DOMContentLoaded', function () {
    loadSubcategories();
});

async function loadSubcategories() {
    const urlParams = new URLSearchParams(window.location.search);
    const categoryId = urlParams.get('category');

    try {
        // 모든 카테고리 정보를 가져오기
        const response = await fetch(`/api/categories/all`);
        if (!response.ok) {
            throw new Error('카테고리를 불러올 수 없습니다');
        }

        const allCategories = await response.json();
        const categoryTree = document.getElementById('category-tree');

        // 현재 선택된 카테고리의 경로 찾기
        const categoryPath = categoryId ? findCategoryPath(categoryId, allCategories) : [];
        
        let rootCategories;
        if (categoryId) {
            const selectedCategory = allCategories.find(cat => cat.id.toString() === categoryId);
            if (selectedCategory) {
                // 선택된 카테고리에 하위 카테고리가 있는지 확인
                const hasChildren = allCategories.some(cat => 
                    cat.parentId && cat.parentId.toString() === categoryId
                );
                
                if (hasChildren) {
                    // 하위 카테고리가 있다면 선택된 카테고리를 루트로 설정
                    rootCategories = [selectedCategory];
                } else {
                    // 하위 카테고리가 없다면 (마지막 레벨) 부모 카테고리를 루트로 설정
                    if (selectedCategory.parentId) {
                        const parentCategory = allCategories.find(cat => 
                            cat.id === selectedCategory.parentId
                        );
                        rootCategories = parentCategory ? [parentCategory] : [selectedCategory];
                    } else {
                        rootCategories = [selectedCategory];
                    }
                }
            } else {
                rootCategories = allCategories.filter(cat => cat.parentId === null);
            }
        } else {
            // 선택된 카테고리가 없다면 최상위 카테고리들 표시
            rootCategories = allCategories.filter(cat => cat.parentId === null);
        }
        
        if (rootCategories.length === 0) {
            categoryTree.innerHTML = '<p>카테고리가 없습니다</p>';
            return;
        }

        // 카테고리 트리 렌더링 (breadcrumb 포함)
        renderFullCategoryTree(rootCategories, allCategories, categoryPath, categoryTree, categoryId);

    } catch (error) {
        console.error('카테고리 로드 실패:', error);
        document.getElementById('category-tree').innerHTML = '<p>카테고리를 불러올 수 없습니다</p>';
    }
}

// 카테고리 경로 찾기 함수
function findCategoryPath(categoryId, allCategories) {
    const path = [];
    let currentId = categoryId;
    
    while (currentId) {
        const category = allCategories.find(cat => cat.id.toString() === currentId.toString());
        if (category) {
            path.unshift(category.id);
            currentId = category.parentId;
        } else {
            break;
        }
    }
    
    return path;
}

// 최상위 부모 카테고리 찾기 함수
function findTopParent(category, allCategories) {
    let currentCategory = category;
    
    while (currentCategory.parentId) {
        const parent = allCategories.find(cat => cat.id === currentCategory.parentId);
        if (parent) {
            currentCategory = parent;
        } else {
            break;
        }
    }
    
    return currentCategory;
}

// 전체 카테고리 트리 렌더링 함수
function renderFullCategoryTree(rootCategories, allCategories, categoryPath, container, selectedCategoryId) {
    container.innerHTML = '';
    
    // 선택된 카테고리가 있고 상위 경로가 있다면 breadcrumb 추가
    if (selectedCategoryId && categoryPath.length > 1) {
        const breadcrumbContainer = document.createElement('div');
        breadcrumbContainer.className = 'breadcrumb-container';
        breadcrumbContainer.style.marginBottom = '15px';
        breadcrumbContainer.style.paddingBottom = '10px';
        breadcrumbContainer.style.borderBottom = '1px solid #e9ecef';
        
        const breadcrumbText = document.createElement('span');
        breadcrumbText.style.fontSize = '14px';
        breadcrumbText.style.color = '#666';
        
        // 경로 생성 (마지막 카테고리 제외)
        const pathNames = [];
        for (let i = 0; i < categoryPath.length - 1; i++) {
            const categoryId = categoryPath[i];
            const category = allCategories.find(cat => cat.id === categoryId);
            if (category) {
                pathNames.push(category.name);
            }
        }
        
        if (pathNames.length > 0) {
            breadcrumbText.textContent = pathNames.join(' > ');
            breadcrumbContainer.appendChild(breadcrumbText);
            container.appendChild(breadcrumbContainer);
        }
    }

    rootCategories.forEach(category => {
        const categoryItem = document.createElement('div');
        categoryItem.className = 'category-item';

        const categoryHeader = document.createElement('div');
        categoryHeader.className = 'category-header';

        const categoryLink = document.createElement('a');
        categoryLink.href = `/book/search?category=${category.id}`;
        categoryLink.textContent = category.name;
        categoryLink.className = 'category-link';

        // 현재 선택된 카테고리 하이라이트
        if (categoryPath.includes(category.id)) {
            categoryLink.style.color = '#007bff';
            categoryLink.style.fontWeight = 'bold';
        }

        // 하위 카테고리 찾기
        const childCategories = allCategories.filter(cat => 
            cat.parentId && cat.parentId.toString() === category.id.toString()
        );

        if (childCategories.length > 0) {
            const toggleButton = document.createElement('button');
            toggleButton.className = 'category-toggle';
            
            // 경로에 포함된 카테고리는 자동으로 펼쳐진 상태
            const shouldExpand = categoryPath.includes(category.id);
            toggleButton.textContent = shouldExpand ? '-' : '+';
            toggleButton.onclick = () => toggleCategoryNew(category.id, toggleButton, allCategories, categoryPath);

            categoryHeader.appendChild(toggleButton);
        }

        categoryHeader.appendChild(categoryLink);

        const subcategoryContainer = document.createElement('div');
        subcategoryContainer.className = 'subcategory-container';
        subcategoryContainer.id = `subcategory-${category.id}`;
        
        // 경로에 포함된 카테고리의 하위 카테고리들은 자동으로 표시
        if (categoryPath.includes(category.id) && childCategories.length > 0) {
            subcategoryContainer.style.display = 'block';
            renderSubcategoriesNew(childCategories, subcategoryContainer, allCategories, categoryPath);
        } else {
            subcategoryContainer.style.display = 'none';
        }

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
                    
                    if (directSubcategories.length === 0) {
                        // 하위 카테고리가 없으면 버튼 숨기기
                        toggleButton.style.display = 'none';
                        return;
                    }
                    
                    renderSubcategories(directSubcategories, subcategoryContainer);
                }
            } catch (error) {
                console.error('하위 카테고리 로드 실패:', error);
                toggleButton.style.display = 'none';
                return;
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

// 새로운 하위 카테고리 렌더링 함수
function renderSubcategoriesNew(subcategories, container, allCategories, categoryPath) {
    container.innerHTML = '';

    if (subcategories.length === 0) {
        return;
    }

    subcategories.forEach(subcategory => {
        const subcategoryItem = document.createElement('div');
        subcategoryItem.className = 'subcategory-item';

        const subcategoryHeader = document.createElement('div');
        subcategoryHeader.className = 'subcategory-header';

        const subcategoryLink = document.createElement('a');
        subcategoryLink.href = `/book/search?category=${subcategory.id}`;
        subcategoryLink.textContent = subcategory.name;
        subcategoryLink.className = 'subcategory-link';

        // 현재 선택된 카테고리 하이라이트
        if (categoryPath.includes(subcategory.id)) {
            subcategoryLink.style.color = '#007bff';
            subcategoryLink.style.fontWeight = 'bold';
        }

        // 하위 카테고리 찾기
        const childCategories = allCategories.filter(cat => 
            cat.parentId && cat.parentId.toString() === subcategory.id.toString()
        );

        if (childCategories.length > 0) {
            const toggleButton = document.createElement('button');
            toggleButton.className = 'subcategory-toggle';
            
            // 경로에 포함된 카테고리는 자동으로 펼쳐진 상태
            const shouldExpand = categoryPath.includes(subcategory.id);
            toggleButton.textContent = shouldExpand ? '-' : '+';
            toggleButton.onclick = () => toggleCategoryNew(subcategory.id, toggleButton, allCategories, categoryPath);

            subcategoryHeader.appendChild(toggleButton);
        }

        subcategoryHeader.appendChild(subcategoryLink);

        const subSubcategoryContainer = document.createElement('div');
        subSubcategoryContainer.className = 'subcategory-container';
        subSubcategoryContainer.id = `subcategory-${subcategory.id}`;
        
        // 경로에 포함된 카테고리의 하위 카테고리들은 자동으로 표시
        if (categoryPath.includes(subcategory.id) && childCategories.length > 0) {
            subSubcategoryContainer.style.display = 'block';
            renderSubcategoriesNew(childCategories, subSubcategoryContainer, allCategories, categoryPath);
        } else {
            subSubcategoryContainer.style.display = 'none';
        }

        subcategoryItem.appendChild(subcategoryHeader);
        subcategoryItem.appendChild(subSubcategoryContainer);
        container.appendChild(subcategoryItem);
    });
}

// 새로운 토글 함수
function toggleCategoryNew(categoryId, toggleButton, allCategories, categoryPath) {
    const subcategoryContainer = document.getElementById(`subcategory-${categoryId}`);
    
    if (subcategoryContainer.style.display === 'none') {
        // 펼치기
        const childCategories = allCategories.filter(cat => 
            cat.parentId && cat.parentId.toString() === categoryId.toString()
        );
        
        if (childCategories.length > 0) {
            renderSubcategoriesNew(childCategories, subcategoryContainer, allCategories, categoryPath);
        }
        
        subcategoryContainer.style.display = 'block';
        toggleButton.textContent = '-';
    } else {
        // 접기
        subcategoryContainer.style.display = 'none';
        toggleButton.textContent = '+';
    }
}
