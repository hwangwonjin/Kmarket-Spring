/*** selectbox 동적 추가 ***/
  function qnaOption(){

  /*** type category 배열 만들기 ***/
  let user = ['가입', '탈퇴', '회원정보', '로그인'];
  let event = ['쿠폰·할인·혜택', '포인트', '제휴', '이벤트'];
  let order = ['상품', '결제', '구매내역', '영수증·증빙'];
  let delivery = ['배송상태·기간', '배송정보확인·변경', '해외배송', '당일배송', '해외직구'];
  let cancel = ['반품신청·철회', '반품정보확인·변경', '교환AS신청·철회', '교환정보확인·변경', '취소신청·철회', '취소확인·환불정보'];
  let travel = ['여행·숙박', '항공'];
  let safeDeal = ['서비스 이용규칙 위반', '지식재산권침해', '법령 및 정책위반 상품', '게시물 정책위반', '직거래·외부거래유도', '표시광고', '청소년 위해상품·이미지'];

  let cate2 = $('.cate2').val();

  let change;

  /*** cate2 선택 value에 따라서 append할 배열 선택 ***/
  switch (cate2) {
    case 'user':
      change = user;
      break;

    case 'coupon':
      change = event;
      break;

    case 'order':
      change = order;
      break;

    case 'delivery':
      change = delivery;
      break;

    case 'cancel':
      change = cancel;
      break;

    case 'travel':
      change = travel;
      break;

    case 'safeDeal':
      change = safeDeal;
      break;
  }

  /*** selectbox append 이전에 먼저 비워주기 ***/
  $('.type').empty();
  let option;

  /*** append ***/
  option =  $("<option value='none' disabled selected>2차 선택</option>");
  $('.type').append(option);

  for (let i=0; i<change.length; i++){
    option = $("<option>"+change[i]+"</option>");
    $('.type').append(option);
  }

  }