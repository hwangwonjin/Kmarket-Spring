/**
    날짜 : 2023/02/15
    이름 : 황원진
**/
$(function(){
      var num = document.querySelector('input[name=num]');

    // 장바구니 담기
    var cart = document.getElementById('cart')
    cart.addEventListener('click', function(){



        let prodNo = document.getElementById('prodNo').innerText;           //상품번호
        console.log("prodNo : " + prodNo);
        let count = num.value;                                              // 수량
        console.log("count : " + count);
        let price = document.getElementById('price').innerText.split(',').join(''); //가격
        console.log("price : "+price);
        let discount = document.getElementById('discount').innerText;       // 할인율
        console.log("discount :" + discount);
        let point = Math.round(price * 0.01 * count);                       // 포인트
        console.log("point : " +point);
        let delivery = document.getElementById('delivery').innerText.replace('원', '');       // 배송비
        if(delivery == '무료배송'){delivery = 0;}
        console.log("delivery : "+delivery);
        let totalPrice = document.getElementById('totalPrice').innerText.split(',').join('');   //최종가격
        console.log("totalPrice : "+totalPrice);

        let jsonData = {
                "type" : 'cart',
                "prodNo" : prodNo,
                "count" : count,
                "price" : price * count,
                "discount" : discount,
                "point" : point,
                "delivery" : delivery,
                "total" : totalPrice
        }

        console.log("jsonData : " +jsonData);


        $.ajax({
                url : '/kmarket/product/cart',
                method:'POST',
                data: jsonData,
                dataType:'JSON',
                success:function(data){
                    console.log("data : "+data)

                    if(data.result == 1){
                        if(confirm('장바구니에 상품이 담겼습니다. 확인하시겠습니까?')){
                            location.href = "/kmarket/product/cart"
                        }
                    }else{
                        alert('다시 시도하여 주십시오');

                    }

                }
        });
    });

    // 주문하기
    var order = document.getElementById('order')
        order.addEventListener('click', function(){

            let checkList = [];

            let prodNo   = document.getElementById('prodNo').innerText;   // 상품 번호
            console.log("prodNo : " +prodNo);
            let count = num.value;                                              // 수량
            console.log("count : " + count);
            let price = document.getElementById('price').innerText.split(',').join(''); //가격
            console.log("price : "+price);
            let discount = document.getElementById('discount').innerText;       // 할인율
            console.log("discount :" + discount);
            let point = Math.round(price * 0.01 * count);                       // 포인트
            console.log("point : " +point);
            let delivery = document.getElementById('delivery').innerText.replace('원', '');       // 배송비
            if(delivery == '무료배송'){delivery = 0;}
            console.log("delivery : "+delivery);
            let totalPrice = document.getElementById('totalPrice').innerText.split(',').join('');   //최종가격
            console.log("totalPrice : "+totalPrice);
            checkList.push(prodNo);

            let jsonData = {
                    "checkList" : checkList,
                    "type" : 'order',
                    "prodNo": prodNo,
                    "count" : count,
                    "price" : price * count,
                    "discount" : price * discount / 100,
                    "point" : point,
                    "delivery" : delivery,
                    "total" : totalPrice
            }


           $.ajax({
                    url : '/kmarket/product/cart',
                    method:'POST',
                    data: jsonData,
                    dataType:'JSON',
                    success:function(data){
                        console.log("data : "+data)

                        if(data.result == 1111){
                             if(confirm('구매 하시겠습니까?')){

                                 location.href = "/kmarket/product/order"
                             }
                        }else{
                            alert('다시 시도하여 주십시오' + data.result);

                        }

                    }
                });


          });



});