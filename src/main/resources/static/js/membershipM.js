
    //멤버십회원 개별조회
    document.addEventListener('DOMContentLoaded', function() {
        var select = document.querySelector('#select');
        var searchForm = document.querySelector('.search_form');
        var searchInput = document.querySelector('.search_input');

        select.addEventListener('change', function() {
            var selectedValue = select.value;

            // 동적으로 form action 변경
            if (selectedValue === 'username') {
                searchInput.placeholder = '검색할 USERNAME를 입력하세요';
                searchInput.name = 'username';
                searchForm.action = '/membership/selectId';
            } else if (selectedValue === 'membershipcode') {
                searchInput.placeholder = '검색할 멤버십 코드를 입력하세요';
                searchInput.name = 'membershipcode';
                searchForm.action = '/membership/selectCode';
            } else if (selectedValue === 'enddate') {
                searchInput.placeholder = '검색할 종료 날짜를 입력하세요';
                searchInput.name = 'enddate';
                searchForm.action = '/membership/selectDate';
            }
        });
    });


        //회원 삭제 script
        const form = document.querySelector('.ms_delete');
        const removeInput = document.querySelector('#remove_input');

        form.addEventListener('click', async function (event) {
            event.preventDefault();

            const removeValue = removeInput.value;

            axios.post('/membership/delete', null, {
                params: { username: removeValue }
            })
            .then(response => {
                if (response.status === 200) {
                    alert(response.data);
                    location.reload();
                }
            })
            .catch(error => {
                alert(removeValue+"은 존재하지 않는 유저입니다.");
                location.reload();
                console.error("Error:", error);
            });
        });
