   $(function (){
    let count      = $('.final').children().children().children('tr:eq(0)').children('td:eq(1)').text().replace('건','');
    let price      = $('.final').children().children().children('tr:eq(1)').children('td:eq(1)').text().replace(',','');
    let discount   = $('.final').children().children().children('tr:eq(2)').children('td:eq(1)').text().replace(',','');
    let delivery   = $('.final').children().children().children('tr:eq(3)').children('td:eq(1)').text().replace(',','');
    let savePoint  = $('.final').children().children().children('tr:eq(4)').children('td:eq(1)').text().replace(',','');
    let totalPrice = $('.final').children().children().children('tr:eq(6)').children('td:eq(1)').text().replace(',','');

    console.log("totalPrice : " + totalPrice);
    console.log("delivery : " + delivery);
    console.log("discount : " + discount);
    console.log("savepoint : " + savePoint);

    $(function(){
        let applyPoint = document.getElementById('applyPoint');
        let userPoint = Number(document.querySelector('span[class=userPoint]').innerHTML.replace(',',''));
        let finalPoint = $('.final').children().children().children('tr:eq(5)').children('td:eq(1)');
        let total = $('.final').children().children().children('tr:eq(6)').children('td:eq(1)');
        var point = document.querySelector('input[name=point]');

        console.log("finalPoint : " + finalPoint);

        if(userPoint < 5000){
            $('input[name=point]').prop('readonly', true);
            $('input[name=point]').prop('value', 0);
        }

        applyPoint.addEventListener('click', function(){

            console.log("pointValue : " + point.value);

            if(point.value > userPoint) {
                alert('보유중인 포인트보다 많습니다.');
                $('input[name=point]').prop('value', 0);
            }else if(point.value < 5000){
                alert('5000원 미만은 사용 하실 수 없습니다.');
                $('input[name=point]').prop('value', 0);
            }else{
                 finalPoint.text(Number(point.value).toLocaleString());
                 let num = (Number(totalPrice)-Number(point.value));
                 total.text(Number(num).toLocaleString());
            }
        });

        $('.order > form ').submit(function(e){


            console.log("click");

            let orderer = $('input[name=orderer]').val();
            let hp = $('input[name=hp]').val();
            let zip = $('input[name=zip]').val();
            let addr1 = $('input[name=addr1]').val();
            let addr2 = $('input[name=addr2]').val();
            totalPrice = $('.final').children().children().children('tr:eq(6)').children('td:eq(1)').text().replace(',','');

            console.log("orderer : " +orderer);
            console.log("addr1 : " + addr1);
            console.log("addr2 : " + addr2);
            console.log("clickTotalPrice : " + totalPrice);

            let jsonData = {
                "ordCount": count,
                "ordPrice": price,
                "ordDiscount": discount,
                "ordDelivery": delivery,
                "usedPoint": point.value,
                "ordTotPrice": totalPrice,
                "recipName": orderer,
                "recipHp": hp,
                "recipZip": zip,
                "recipAddr1": addr1,
                "recipAddr2": addr2,
                "ordPayment": $('input[name=payment]:checked').val()
            }

            console.log("jsonData : " +JSON.stringify(jsonData));

            $.ajax({
                    url:'/kmarket/product/complete',
                    method:'POST',
                    data: jsonData,
                    dataType: 'JSON',
                    success: function(data){
                        console.log("data : " + data.result);
                        if(data.result > 0){
                           location.href = "/kmarket/product/complete";
                        }else {
                            alert('다시 시도해 주십시오.');
                        }
                    }
            });
            e.preventDefault();
        });

    });


});












