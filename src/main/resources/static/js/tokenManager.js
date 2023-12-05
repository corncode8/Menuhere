
function getToken() {
    const accessToken = localStorage.getItem('AccessToken');
    const refreshToken = localStorage.getItem("RefreshToken");
    if (isTokenValid(accessToken)) {
        return accessToken;
    } else if (isTokenValid(refreshToken)) {
        return refreshToken;
    }
    else {
        console.error('no token');
    }
}

async function isTokenValid(token) {
    try {
        const response = await fetch(`/api/validate-token?token=${token}`);

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.text();
        return data === 'true'; // 서버에서 받은 값이 'true'이면 true, 그렇지 않으면 false 반환
    } catch (error) {
        console.error('Error validating token:', error);
        throw error; // 에러가 발생하면 에러를 전파
    }
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