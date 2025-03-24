/***
 * mng/admin/admin.js
 * 메인 > 접근IP 관리
 * */
$(function(){

});

function f_admin_access_ip_init_set(){
    window.location.href = '/mng/admin/detail.do';
}

function f_admin_access_ip_save(){

    Swal.fire({
        title: '입력된 정보로 저장하시겠습니까?',
        icon: 'info',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonColor: '#00a8ff',
        confirmButtonText: '저장',
        cancelButtonColor: '#A1A5B7',
        cancelButtonText: '취소'
    }).then((result) => {
        if (result.isConfirmed) {

            let accessIp = $('#accessIp').val();

            let jsonArr = [];
            if(nvl(accessIp,'') !== ''){
                let accessIpList = accessIp.split('\n');
                for(let i=0; i<accessIpList.length; i++){
                    let jsonObj = {
                        ip: accessIpList[i]
                    }

                    jsonArr.push(jsonObj);
                } // for
            }

            $.ajax({
                url: '/mng/admin/access/save.do',
                method: 'post',
                async: false,
                data: JSON.stringify(jsonArr),
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if (data.resultCode === "0") {

                        Swal.fire({
                            title: '[접근 IP 관리]',
                            text: '접근 IP 정보가 저장되었습니다.',
                            icon: 'info',
                            allowOutsideClick: false,
                            confirmButtonColor: '#3085d6',
                            confirmButtonText: '확인'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                f_admin_access_ip_init_set(); // 재조회
                            }
                        });

                    } else {
                        showMessage('', 'error', '에러 발생', '접근 IP 정보 저장을 실패하였습니다. 관리자에게 문의해주세요. ' + data.resultMessage, '');
                    }
                },
                error: function (xhr, status) {
                    alert('오류가 발생했습니다. 관리자에게 문의해주세요.\n오류명 : ' + xhr + "\n상태 : " + status);
                }
            })

        }//isConfirmed
    })//then

}