




// 주문번호 만들기
function createOrderNum(){
    const date = new Date();
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");

    let orderNum = year + month + day;
    for(let i=0;i<10;i++) {
        orderNum += Math.floor(Math.random() * 8);
    }
    return orderNum;
}

function userOrder(tableNoValue) {
    const orderData = {
        orderStatus: "ORDER",
        requests: document.getElementById('request').innerHTML,
        orderPrice: amount,
        orderType: document.getElementById('order-type').value,
        payType: paytype,
        payStatus: true,
        orderMenus: orderMenus,
        tableNo: tableNoValue
    };
    $.ajax({
        url: '/api/new/order',
        headers: { 'Authorization': 'Bearer ' + token },
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(orderData),
        success: function (response) {
            // 성공 시 처리
            // console.log(response);
            console.log(orderData);
        },
        error: function (error) {
            // 에러 시 처리
            console.error('주문 생성 실패 :', error);
        }
    });
}

function nonUserOeder(tableNoValue) {
    const orderData = {
        orderStatus: "ORDER",
        requests: document.getElementById('request').innerHTML,
        orderPrice: amount,
        orderType: document.getElementById('order-type').value,
        payType: paytype,
        payStatus: true,
        orderMenus: orderMenus,
        tableNo: tableNoValue
    };
    $.ajax({
        url: '/api/new/order',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(orderData),
        success: function (response) {
            // 성공 시 처리
            console.log(orderData);
        },
        error: function (error) {
            // 에러 시 처리
            console.error('주문 생성 실패 :', error);
        }
    });
}