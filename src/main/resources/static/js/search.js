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
});
