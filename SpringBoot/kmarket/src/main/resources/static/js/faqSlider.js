
/*** cs - faq slider 관련 js ***/

$(function(){
    $('.open').click(function(e){
      e.preventDefault();

      let items = $(this).parent().find('.hide');
      let txt = $(this).children();

      if ( items.is(":visible") ) {
        // faq 질문 펼치기
        txt.text('더보기');
        items.slideUp();
      } else {
        // faq 질문 접기
        txt.text('접기');
        items.slideDown();
      }
    });
});
