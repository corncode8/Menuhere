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
function addCart() {
    // menuId = parseInt(menuId);
    //
    // if (isNaN(menuId)) {
    //     console.error('유효하지 않은 menuId:', menuId);
    //     return;
    // }
    //
    // console.log(menuId); // 메뉴 ID가 제대로 전달되었는지 확인

    // 예를 들어, 아래와 같이 서버에 장바구니 추가 요청을 보낼 수 있습니다.
    fetch('/add/cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            // menuId: menuId,

        }),
    })
        .then(response => {
            if (!response.ok) {
                console.error('에러 응답:', response.status, response.statusText);
                // 오류 처리 로직 추가
                return;
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            // updateCartCnt(data.cartTotal);
        })
        .catch(error => console.error('Error:', error));
}

function updateCartCnt(count) {
    // span 태그 업데이트
    document.getElementById('cart-count').innerText = count;
}