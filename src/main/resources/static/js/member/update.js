
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


   const isValid = ()=>{
        const form = document.update;
        form.submit();
    }


       //패스워드 일치 검증 코드
        const form = document.update;
        const passwordConfirmBtn = document.querySelector('.password_btn');
        passwordConfirmBtn.addEventListener('click',function(){
            const requestData = {
              oldPassword: form.password.value
            };

            axios.post('/member/passwordConfirm', requestData)
              .then(response => {
                console.log('Success:', response);
                const msgtg = document.querySelector('.pwmsg');
                msgtg.innerHTML=response.data;
                msgtg.style.color='green';

                form.repassword.readOnly = false;
              })
              .catch(error => {

                    console.log('Unauthorized: Incorrect password');
                    const msgtg = document.querySelector('.pwmsg');
                    msgtg.innerHTML='Unauthorized: Incorrect password';
                    msgtg.style.color='orange';

              });

        })
