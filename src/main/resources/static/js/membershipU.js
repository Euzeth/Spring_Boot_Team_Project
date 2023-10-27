    const subscribe1_el = document.querySelector('.subscribe_1');
    subscribe1_el.addEventListener('click', function () {
        axios.get('/membership/request1')
        .then(response=>{
            console.log("request1 Success");
            console.log(response);
            console.log(response.data);
            console.log(response.data.next_redirect_pc_url);
            window.open(response.data.next_redirect_pc_url, "Payment","width=400,height=700");
        })
        .catch(error=>{});
    });


    const subscribe2_el = document.querySelector('.subscribe_2');
    subscribe2_el.addEventListener('click', function () {
        axios.get('/membership/request2')
        .then(response=>{
            console.log("request2 Success");
            console.log(response);
            console.log(response.data);
            console.log(response.data.next_redirect_pc_url);
            window.open(response.data.next_redirect_pc_url, "Payment","width=400,height=700");
        })
        .catch(error=>{});
    });


        function terminateMembership() {
        const term_btn_el = document.querySelector('.term_btn');
        const username = term_btn_el.dataset.username;
        // 사용자에게 확인 메시지를 표시
        if (confirm("정말 구독을 취소하시겠습니까?") == true){
            axios.delete("/membership/terminate?username=" + username)
            .then(response=>{
                if(response.status === 200){
                    console.log(username+"님 구독취소 완료!");
                    alert(username+"님 구독이 취소되었습니다! "+enddate+"까지 구독이 유지됩니다.")
                    location.reload();
                }
            })
            .catch(error=>{console.log(error)});
        }else{
            console.log("구독 해지를 취소하였습니다.");
            alert("구독 해지가 취소되었습니다!");
        }
    }