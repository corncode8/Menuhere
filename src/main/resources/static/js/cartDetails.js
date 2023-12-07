
let cnt = 0;

// 장바구니 담기
function addCart(menuName, menuPrice) {
    // Ajax를 사용하여 서버에 메뉴 정보를 전송
    $.ajax({
        type: "POST",
        url: "/add/cart",
        data: { menuName: menuName, menuPrice:menuPrice },
        success: function (response) {
            // 성공 시 처리
            // alert('장바구니에 추가되었습니다.');
            cnt++;
            updateCartCnt(cnt);
        },
        error: function (error) {
            // 에러 시 처리
            alert('장바구니 추가에 실패했습니다.');
        }
    });
}

function updateCartCnt(count) {
    // span 태그 업데이트
    document.getElementById('cart-count').innerText = count;
}
