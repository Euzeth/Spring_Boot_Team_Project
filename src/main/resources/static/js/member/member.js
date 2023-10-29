
    document.addEventListener("DOMContentLoaded", function () {
        const form = document.querySelector('.removeuser');
        const searchInput = document.querySelector('#remove_input');

        form.addEventListener('click', async function (event) {
            event.preventDefault();

            const searchValue = searchInput.value;

            try {
                const response = await axios.post('/member/remove', null, {
                    params: { id: searchValue }
                });
                console.log('서버 응답:', response);
                alert('삭제가 완료되었습니다.');
                location.reload();
            } catch (error) {
                if (error.response) {
                    console.error('서버 응답 오류:', error.response.data);
                } else {
                    console.error('요청 오류:', error.message);
                }
                // 오류가 발생했을 때 처리
                alert('삭제에 실패했습니다.');
            }
        });
    });

    document.addEventListener('DOMContentLoaded', function() {
        const searchButton = document.querySelector('#searchButton');
        const searchInput = document.querySelector('#search_input');
        const oneshowblock = document.querySelector('#oneshowblock');

        searchButton.addEventListener('click', async function(event) {
            event.preventDefault();

            const searchValue = searchInput.value;

            try {
                const response = await axios.post('/member/search', null, {
                    params: { username: searchValue }
                });
                console.log('서버 응답:', response);

                const searchResults = response.data; // 응답에서 검색 결과 가져오기

                // 검색 결과를 표시하기 위한 HTML 생성
                let html = '<table>';
                html += '<thead><tr><th>유저아이디</th><th>이름</th><th>주소</th><th>전화번호</th></tr></thead>';
                html += '<tbody>';
                searchResults.forEach(function(member) {
                    html += '<tr><td>' + member.username + '</td><td>' + member.name + '</td><td>' + (member.addr1 + ' ' + member.addr2) + '</td><td>' + member.phone + '</td></tr>';
                });
                html += '</tbody></table>';

                // 검색 결과를 표시할 요소를 업데이트
                oneshowblock.innerHTML = html;

                alert('회원 검색 완료.');
            } catch (error) {
                if (error.response) {
                    console.error('서버 응답 오류:', error.response.data);
                } else {
                    console.error('요청 오류:', error.message);
                }
                // 오류 처리 및 사용자에게 오류 메시지를 표시
                alert('회원 검색 실패.');
            }
        });


    });


    document.addEventListener('DOMContentLoaded', function() {
        // 버튼 클릭 이벤트 핸들러
        var showButton = document.getElementById('showButton');
        var selectAllDiv = document.querySelector('.selectall');

         // 요소가 존재하는지 확인
        if (showButton && selectAllDiv) {
            // 버튼에 클릭 이벤트 리스너 추가
            showButton.addEventListener('click', function() {

                // selectAllDiv의 표시를 토글
                if (selectAllDiv.style.display === 'none' || selectAllDiv.style.display === '') {
                    selectAllDiv.style.display = 'block'; // 보이게 함
                } else {
                    selectAllDiv.style.display = 'none'; // 숨김
                }
            });
        }
    });
