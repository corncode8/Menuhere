const mindelivery = Number($("#min_delivery").data("min_delivery"));
const deliveryTip = Number($("#delivery_tip").data("delivery_tip"));

const cart = (function () {
    // 장바구니 상품 수
    let cartSize = 0;
    const getCartSize = function () {
        return cartSize;
    }
    const setCartSize = function (setData) {
        cartSize = setData;
    }
    // 장바구니 메뉴 가격 총합
    let cartTotalPrice = 0;
    const getCartToTalPrice = function () {
        return cartTotalPrice;
    }
    const setCartTotalPrice = function (setData) {
        cartTotalPrice = setData;
    }

    return {
        getCartSize : getCartSize,
        setCartSize : setCartSize,
        getCartToTalPrice : getCartToTalPrice,
        setCartTotalPrice : setCartTotalPrice,
    };
})();



// 장바구니 담기
function addCart(menuName, menuPrice) {
    // Ajax를 사용하여 서버에 메뉴 정보를 전송
    $.ajax({
        type: "POST",
        url: "/add/cart",
        data: { menuName: menuName, menuPrice:menuPrice },
        success: function (response) {
            // 성공 시 처리
            alert('장바구니에 추가되었습니다.');
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