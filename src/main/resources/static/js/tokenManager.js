
function getToken() {
    const accessToken = localStorage.getItem('AccessToken');
    const refreshToken = localStorage.getItem("RefreshToken");
    return {Token: accessToken, refreshToken};
}

function Logout() {
    localStorage.removeItem('AccessToken');
    localStorage.removeItem('RefreshToken');
    // 페이지 새로고침
    location.reload();
}

function mainPageUpdateButton() {
    const authToken = localStorage.getItem('AccessToken');
    const refreshToken = localStorage.getItem("RefreshToken");


}