$(function(){
    // 상품삭제 Product-list
    $('.remove').click(function(e){
        e.preventDefault();

        let delOk = confirm("정말 삭제하시겠습니까?")
        let url = "/kmarket/admin/product/oneDelete"
        let chkNo = $("input[name='check']").val();
        let productNow = $(this);

        if(delOk){
            console.log(productNow);

            $.ajax({
                url:url,
                method:'post',
                data:{chkNo : chkNo},
                dataType:'json',
                success: (data)=>{
                    if(data.result == 1){
                        alert([chkNo] + '번 상품이 성공적으로 삭제되었습니다.')
                        console.log(productNow)
                        productNow.parent().parent().remove();
                    }else{
                        alert('delete fail...')
                    }
                }
            })
        }
    });



});