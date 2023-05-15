/*** 그냥 삭제 버튼 눌렀을 때 confirm ***/
function delConfirm() {
    return confirm("정말 삭제하시겠습니까?");
}

/** 체크박스 모두선택 **/
function selectAll(selectAll) {
    const checkboxes
        =
    document.getElementsByName('check');

    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    })
}

$(function(){

    let checkObj = document.getElementsByName("check");
    let rowCount = checkObj.length;

    /*** 단일체크 처리 ***/
    $("input[name='check']").click(function(){
        if($("input[name='check']:checked").length == rowCount){
            $("input[name='all']")[0].checked = true;
        } else {
            $("input[name='all']")[0].checked = false;
        }
    });
});


/*** 선택삭제 눌러서 삭제처리 ***/
function checkDelete() {
    let url = '/kmarket/admin/cs/delete';
    let valueArr = new Array();
    let list = $("input[name='check']");

    for (let i=0; i<list.length; i++){
        if(list[i].checked){
            valueArr.push(list[i].value);
        }
    }

    if (valueArr.length == 0){
        alert('선택된 상품이 없습니다.');
    } else {
        let chk = confirm('정말 삭제하시겠습니까?');
        $.ajax({
            url: url,
            type: 'post',
            traditional: true,
            data: {valueArr: valueArr},
            dataType: 'json',
            success: function(data){
                if(data.result > 0){
                    alert('삭제되었습니다.');
                    location.reload();
                } else {
                    alert('삭제에 실패했습니다.');
                }
            }
        });
    }

}