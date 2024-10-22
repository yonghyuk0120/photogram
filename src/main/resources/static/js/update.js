// (1) 회원정보 수정
function update(userId) {
    event.preventDefault(); // 폼태그 액션을 막기!!

    let data = $("#profileUpdate").serialize();
    // 폼태그의 모든 input값을 시리얼라이즈 한다는 것은
    // data에 받아온 form을 키 밸류 형태로 담는 다는 이야기.

    console.log(data);

    $.ajax({
        type: "put",
        url : `/api/user/${userId}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        // 폼형식으로 보낸다.
        dataType: "json"
    }).done(res=>{ // HttpStatus 상태코드 200번대
        console.log("성공", res);
        location.href = `/user/${userId}`;
    }).fail(error=>{ // HttpStatus 상태코드 200번대가 아닐 때

        if(error.responseJSON.data == null){
            alert(error.responseJSON.message);
        }else{
            let obj = error.responseJSON.data;
            let ajaxStr = "";
            for (let key in obj) {
                const value = obj[key]

                ajaxStr =ajaxStr+value+"\n";
            }

             alert(ajaxStr);
        }
    });
}