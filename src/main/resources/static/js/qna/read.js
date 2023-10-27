    //작성자가 아닐 경우 수정, 삭제버튼 비활성화
        axios.get('/user/info')
            .then(function(response) {
                username = response.data;
                console.log("User: " + username);
                console.log("Post : " + qnaUsername);

                const update_btn_el = document.querySelector('.update_btn');
                const delete_btn_el = document.querySelector('.delete_btn');

                 if (username !== qnaUsername) {
                    //작성자가 아닐 때 버튼 비활성화
                    console.log("작성자가 다릅니다.")
                    update_btn_el.removeAttribute('href');
                    update_btn_el.addEventListener('click', function() {alert('작성자만 수정할 수 있습니다.');});
                    delete_btn_el.removeAttribute('href');
                    delete_btn_el.addEventListener('click', function() {alert('작성자만 삭제할 수 있습니다.');});
                } else if(username == qnaUsername){
                //작성자일 때 삭제 가능
                delete_btn_el.addEventListener('click',function(){
                const no = delete_btn_el.dataset.no;
                    axios.delete("/qna/delete?no="+no)
                        .then(
                            response=>{console.log(response);
                                if(response.status === 200){
                                    location.href="/qna/list";
                                }
                            })
                        .catch(error=>console.log(error));
                    });
                }
            })
            .catch(function(error){
                console.error(error);
            });



    //
      //---------------------------
    //댓글 추가 (비동기)
    //---------------------------
    const reply_btn_el = document.querySelector('.reply_btn');

    /*<![CDATA[*/
            var qno = [[${qnaDto.no}]];
     /*]]>*/

    reply_btn_el.addEventListener('click',function(){
        const reply_contents_el = document.querySelector('.reply_contents');

        //비동기 Add요청
         axios.get("/qna/reply/add?qno="+qno+"&contents="+reply_contents_el.value)
        .then(response=>{
            console.log(response);
            //기존list삭제
            const replyBody_items = document.querySelector('.reply-body .items');
            while(replyBody_items.firstChild){
                 replyBody_items.firstChild.remove()
            }

            //새로list불러오기
            getReplylist();


            //Input Clear
            reply_contents_el.value="";
        })
        .catch(error=>console.log(error));

    });
   //----------------------------------------------------------------
    //엔터키 댓글 추가
    //----------------------------------------------------------------
    const reply_contents_el = document.querySelector('.reply_contents');
    reply_contents_el.addEventListener('keydown',function(e){

        if(e.keyCode==13){

                axios.get("/qna/reply/add?qno="+qno+"&contents="+reply_contents_el.value)
                .then(response=>{
                    console.log(response);

                    //기존list삭제
                    const replyBody_items = document.querySelector('.reply-body .items');
                    while(replyBody_items.firstChild){
                         replyBody_items.firstChild.remove()
                    }

                    //새로list불러오기
                    getReplylist();



                    //Input Clear
                    reply_contents_el.value="";

                })
                .catch(error=>console.log(error));

        }
    });



    //---------------------------
    // 댓글 조회하기(비동기)
    //---------------------------
    const getReplylist = ()=>{

     /*<![CDATA[*/
            let qno = [[${qnaDto.no}]];
     /*]]>*/

        console.log("getReplylist qno :",qno);
        axios.get("/qna/reply/list?qno="+qno)
        .then(response=>{
            console.log(response.data);
            if(response.data!=null)
            {

                //댓글 Item 만들기
                const reply_list = response.data;
                reply_list.forEach(
                    reply=>{
                        CreateItem(reply);
                    }
                );

            }
        })
        .catch(error=>console.log(error));
    };
    getReplylist();


    //----------------------------------------------------------------
    // Item 만들기
    //----------------------------------------------------------------
    const items_el = document.querySelector(".items");
    const CreateItem = (reply)=>{
        console.log("reply : ",reply);

        // div 엘리먼트 생성
        var divElement = document.createElement("div");
        divElement.className = "item";

        // header div 엘리먼트 생성
        var headerElement = document.createElement("div");
        headerElement.className = "header";

        // img 엘리먼트 생성
        var imgElement = document.createElement("img");
        imgElement.src = "/images/account.png";
        imgElement.alt = "";

        // header div에 img 엘리먼트 추가
        headerElement.appendChild(imgElement);

        // body div 엘리먼트 생성
        var bodyElement = document.createElement("div");
        bodyElement.className = "body";

        // 내용 엘리먼트 생성(계정 | 날짜 )
        var contentDiv = document.createElement("div");

        var span1 = document.createElement("span");
        span1.className = "reply_manager";
        span1.textContent = "관리자입니다^__________^";
        var span2 = document.createElement("span");
        span2.className = "reply_regdate";
        span2.textContent = formatter(reply.regdate);


        var delete_a = document.createElement("a");
        delete_a.setAttribute("href","/qna/reply/delete/"+reply.qno+"/"+reply.rno);       //DELETE HREF!!!!!!!!
        var span3 = document.createElement("span");
        span3.className = "reply_delete material-symbols-outlined";
        span3.textContent = "delete";
        delete_a.appendChild(span3);

        contentDiv.appendChild(span1);
        contentDiv.appendChild(span2);
        contentDiv.appendChild(delete_a);

        // 내용 엘리먼트 추가
        bodyElement.appendChild(contentDiv);



        // contents div 엘리먼트 생성
        var contentsDiv = document.createElement("div");
        contentsDiv.className = "contents";
        contentsDiv.textContent = reply.content;

        // contents div 엘리먼트 추가
        bodyElement.appendChild(contentsDiv);

        // header와 body 엘리먼트를 div에 추가
        divElement.appendChild(headerElement);
        divElement.appendChild(bodyElement);

        // div 엘리먼트를 body에 추가
        items_el.appendChild(divElement);
    }

    //----------------------------------------------------------------
    // LocalDate Formatter
    //----------------------------------------------------------------
    const formatter = (regDate)=>{
      // 분리한 정보를 사용하여 Date 객체를 생성합니다.
      const year = parseInt(regDate[0]);
      const month = parseInt(regDate[1]) - 1; // 월은 0부터 시작하므로 1을 빼줍니다.
      const day = parseInt(regDate[2]);
      const hours = parseInt(regDate[3]);
      const minutes = parseInt(regDate[4]);
      const seconds = parseInt(regDate[5]);

      const date = new Date(year, month, day, hours, minutes, seconds);

      // Date 객체를 원하는 형식으로 포맷팅합니다.
      const formattedDate = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}`;

      return formattedDate;
    };
