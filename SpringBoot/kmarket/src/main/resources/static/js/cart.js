    let  prodCount = 0;
    let totalCount = 0;
    let totalPrice = 0;
    let totalDiscount = 0;
    let totalDelivery = 0;
    let totalPoint = 0;
    let totalTotal = 0;

    // 중복 사용되는 스크립트 묶어 사용

    function totalChange(prodCount, totalPrice, totalPoint, totalDelivery ,totalTotal){
    	document.querySelector('td[class=count]').innerText = prodCount.toLocaleString();
    	document.querySelector('td[class=price]').innerText = totalPrice.toLocaleString();
    	document.querySelector('td[class=delivery]').innerText = totalDelivery.toLocaleString();
    	document.querySelector('td[class=point]').innerText = totalPoint.toLocaleString();
    	document.querySelector('td[class=total]').innerText = totalTotal.toLocaleString();
    	document.querySelector('td[class=discount]').innerText = (((totalPrice + totalDelivery) - totalTotal)).toLocaleString();
    }

    // cartHtml 체크박스에 onclick으로 들어간 함수
    function checkProd(prodNo){
    	// prodNo에 맞는 클래스가 체크되어 있다면
    	if($('.'+prodNo).is(':checked')){
    		prodCount     += 1;
    		totalCount    += Number(document.getElementById(prodNo + 'count').innerText.replace(',', ''));
    		totalPrice    += Number(document.getElementById(prodNo + 'price').innerText.replace(',', ''));
    		totalDelivery += Number(document.getElementById(prodNo + 'delivery').innerText.replace(',', ''));
    		totalPoint    += Number(document.getElementById(prodNo + 'point').innerText.replace(',', ''));
    		totalTotal    += Number(document.getElementById(prodNo + 'total').innerText.replace(',', ''));

    		// 상단 totalChange function
    		totalChange(prodCount, totalPrice, totalPoint, totalDelivery, totalTotal);

    	}else if(!$('.'+prodNo).is(':checked')) {
    		prodCount     -= 1;
    		totalCount    -= Number(document.getElementById(prodNo + 'count').innerText.replace(',', ''));
    		totalPrice    -= Number(document.getElementById(prodNo + 'price').innerText.replace(',', ''));
    		totalDelivery -= Number(document.getElementById(prodNo + 'delivery').innerText.replace(',', ''));
    		totalPoint    -= Number(document.getElementById(prodNo + 'point').innerText.replace(',', ''));
    		totalTotal    -= Number(document.getElementById(prodNo + 'total').innerText.replace(',', ''));

    		// 상단 totalChange function
    		totalChange(prodCount, totalPrice, totalPoint, totalDelivery, totalTotal);
    	}
    }

     $(function(){

        var checkList = [];

        // 전체선택
        $('input[name=all]').click(function(){
            // 리스트 초기화
            checkList = [];

            // 전체 선택 시 전부 체크
            $('input[name=prodNo]').prop('checked', true);

            // checkList에 담는 이유는 선택삭제시 필요
            $('input[name=prodNo]:checked').each(function(){checkList.push($(this).val()); });

            $.ajax({
                    url:'/kmarket/product/cart/total',
                    method:'post',
                    success: function(data){
                    // 전체 버튼 체크
                    if($('input[name=all]').is(':checked')){

                        $('input[name=prodNo]').prop('checked', true);

                        console.log("true");

                        // 이전에 선택이 되어진 상태에서 전체 선택을 눌렀을 시
                        totalChange(0, 0, 0, 0, 0)

                        // 전체 선택 상태에서 일부 상품을 체크 혹은 해제 했을때 새롭게 계산을 하여야 하기에 변수 수정
                        prodCount = data.result.count
                        totalPrice = data.result.price
                        totalDelivery = data.result.delivery
                        totalPoint = data.result.point
                        totalTotal = data.result.total

                        console.log("prodCount : " +prodCount );

                        totalChange(prodCount, totalPrice, totalPoint, totalDelivery, totalTotal);

                        }else {
                            $('input[name=prodNo]').prop('checked', false);
                            console.log("false");
                            totalChange(0, 0, 0, 0, 0);
                            totalCount = 0
                            totalPrice = 0
                            totalDelivery = 0
                            totalPoint = 0
                            totalTotal = 0
                            checkList = [];
                        }
                    }

            });
        });

        // 선택 삭제
        $('.del').click(function(){

                checkList = [];

                $('input[name=prodNo]:checked').each(function(){ checkList.push($(this).val()); });

                if(checkList == ''){
                    alert('선택된 상품이 없습니다');

                    return;
                }


                let jsonData = {'checkList' : checkList};

                console.log("jsonData : "+jsonData);


                    $.ajax({
                        url:'/kmarket/product/checkCart',
                        method:'post',
                        data:jsonData,
                        dataType:'JSON',
                        success: function(data){
                             console.log("data : " +data.result);
                            if(checkList.length == data.result){
                                alert('삭제되었습니다');
                                location.href = "/kmarket/product/cart";
                            }else{
                                alert('다시 시도해 주세요');
                            }

                        }

                    });


            });

        // 주문하기
        $('.cart > form').submit(function(e){
            e.preventDefault();
            let checkList = [];

            $('input[name=prodNo]:checked').each(function(){
                checkList.push($(this).val());
            });

            let count = $('.total .count').text().replace(",", "");
            let price = $('.total .price').text().replace(",", "");
            let discount = $('.total .discount').text().replace(",", "");
            let delivery = $('.total .delivery').text().replace(",", "");
            let point = $('.total .point').text().replace(",", "");
            let total = $('.total .total').text().replace(",", "");

            let jsonData = {
                'checkList': checkList,
                'count': count,
                'price': price,
                'discount': discount,
                'delivery': delivery,
                'point': point,
                'total': total
            };

            console.log("jsonData : " + jsonData);

            if(!$('input[name=prodNo]').is(':checked')){ alert('상품을 선택하세요'); return;}

            if(confirm('선택하신 상품으로 주문하시겠습니까?')){

                    $.ajax({
                            url:'/kmarket/product/order',
                            method:'POST',
                            data: jsonData,
                            dataType:'JSON',
                            success: function(data){
                                console.log("data : " + data.result);
                                if(data.result == 1){
                                    location.href = "/kmarket/product/order";
                                }
                            }
                    });
                }

            });

    });



