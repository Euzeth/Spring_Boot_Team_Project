   // EMAIL 발송
    const email_auth_btn_el = document.querySelector('.email_auth_btn');
    const email = document.querySelector('.email');
    email_auth_btn_el.addEventListener('click', function(){
        //alert('TEST');
        axios.get('/member/auth/email/' + email.value)
        .then(response => { console.log(response); })
        .catch(error => {});
    });

    // CODE 전송
    const email_auth_btn_2_el = document.querySelector('.email_auth_btn_2');
    const code = document.querySelector('.code');
    email_auth_btn_2_el.addEventListener('click', function(){
        //alert('TEST');

        const email_msg = document.querySelector('.email_msg');

        axios.get('/member/auth/confirm/' + code.value)
        .then(response => {
            console.log(response);
            if(response.data === 'success'){
                email_msg.innerHTML = "이메일 인증 성공..!";
                email_msg.style.color="green";
            }else{
                email_msg.innerHTML = "이메일 인증 실패..!";
                email_msg.style.color="red";
            }
            //모달창 종료
            const modal_exit = document.querySelector('.modal_exit');
            modal_exit.click();
         })
        .catch(error => {});
    });


        const isValid = ()=>{
            const form = document.joinform;
            /*
            if(form.username.value.trim()==""){
                alert("USERNAME을 입력하세요.");
                return;
            }
            if(form.password.value.trim()==""){
                alert("PASSWORD을 입력하세요.");
                return;
            }
            */
            form.submit();
        }


            const addr_btn = document.querySelector('#addr_btn');
            addr_btn.addEventListener('click', function(){
                new daum.Postcode({
                oncomplete: function(data) {
                    console.log(data);
                    const zipcode = document.querySelector('.zipcode');
                    const addr1 = document.querySelector('.addr1');

                    if(data.userSelectedType === 'R')
                    {
                        addr1.value = data.roadAddress;
                    }
                    else
                    {
                        addr1.value = data.jibunAddress;
                    }
                    zipcode.value = data.zonecode;
                }
            }).open();
            });

            async function checkDuplicateId() {
        			var id = document.getElementById("id_input").value;

        			try {
        				const response = await axios.get(`/member/checkDuplicate`, {
        							params : { username : id },
        				});

        				const isDuplicate = response.data;
        				if (!isDuplicate) {
        					alert("이미 사용 중인 아이디입니다.");
        				} else {
        					alert("사용 가능한 아이디입니다.");
        				}
        			} catch (error) {
        				alert("중복 확인 중에 오류가 발생했습니다.");
        			}

        		}
        		document.getElementById("idcheck_btn").addEventListener("click", checkDuplicateId);
