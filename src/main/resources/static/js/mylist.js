
    const mTopBtn = document.querySelector(".top_btn");

    window.addEventListener("scroll", () => {
      let scVal = window.scrollY;

      if (scVal === 0) {
        mTopBtn.style.opacity = 0;
      } else {
        mTopBtn.style.opacity = 1;
        mTopBtn.classList.remove("active");
      }
    });


    mTopBtn.addEventListener("click", () => {
      window.scrollTo({
        top: 0,
        behavior: "smooth"
      });
    });


     //체크박스로 체크한 곡 삭제하기
        window.onload = function() {
            var allCheck = document.getElementById("allCheck");
            var rowCheckboxes = document.getElementsByName("rowCheck");
            var selectedMusicCodes = [];
            var username = "";  // 사용자 이름

            // username 가져오기
            axios.get('/user/info')
                .then(function(response) {
                    username = response.data;
                    console.log("User: " + username);
                })
                .catch(function(error){
                    console.error(error);
                });

            // allcheck 선택되었을 때 모두 선택
            allCheck.addEventListener("change", function() {
                for (var i = 0; i < rowCheckboxes.length; i++) {
                    rowCheckboxes[i].checked = allCheck.checked;
                    if (allCheck.checked) {
                        var musicCode = rowCheckboxes[i].parentElement.nextElementSibling.textContent;
                        addMusicCode(musicCode);
                    } else {
                        // allCheck가 선택이 해제되었을 때 배열에서 제거
                        var musicCode = rowCheckboxes[i].parentElement.nextElementSibling.textContent;
                        removeMusicCode(musicCode);
                    }
                }
                console.log("Selected Music Codes: " + selectedMusicCodes.join(", "));
            });

            for (var i = 0; i < rowCheckboxes.length; i++) {
                rowCheckboxes[i].addEventListener("change", function() {
                    if (this.checked) {
                        // 선택된 체크박스의 music_code 값을 얻기
                        var musicCode = this.parentElement.nextElementSibling.nextElementSibling.textContent;
                        // musicCode를 배열에 추가
                        addMusicCode(musicCode);
                    } else {
                        var musicCode = this.parentElement.nextElementSibling.nextElementSibling.textContent;
                        // 체크가 해제된 경우 배열에서 제거
                        removeMusicCode(musicCode);
                    }
                    console.log("Selected Music Codes: " + selectedMusicCodes.join(", "));
                });
            }

            // "선택삭제" 버튼 클릭 시 이벤트 처리
            document.querySelector("#selectDelete").addEventListener("click", function() {

                // 선택된 music_code 배열과 username을 서버로 보내기
                axios.post('/mylist/delete', {
                    musicCodes: selectedMusicCodes,
                    username: username
                })
                .then(function (response) {
                    console.log("Mylist delete Success");
                    alert("음악이 Mylist에서 삭제되었습니다!")
                    location.reload();
                })
                .catch(function (error) {
                    console.log(username);
                    console.log(selectedMusicCodes);
                    console.error("Mylist delete fail.. : ", error);
                });
            });

            function addMusicCode(musicCode) {
                if (selectedMusicCodes.indexOf(musicCode) === -1) {
                    selectedMusicCodes.push(musicCode);
                }
            }

            function removeMusicCode(musicCode) {
                var index = selectedMusicCodes.indexOf(musicCode);
                if (index > -1) {
                    selectedMusicCodes.splice(index, 1);
                }
            }
        }
