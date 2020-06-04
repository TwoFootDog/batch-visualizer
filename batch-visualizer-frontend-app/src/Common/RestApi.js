import axios from 'axios';

/*
const batchListUrl = "http://198.13.47.188:8080/tp";
const signInUrl = "http://127.0.0.1:8080/signin";
const signUpUrl = "http://127.0.0.1:8080/signup";
const getUserInfoUrl = "http://127.0.0.1:8080/getUserInfo"
*/

/*const batchListUrl = "https://66.42.43.41:6443/api/v1/namespaces/ns-project/services/batch-visualizer-backend-provider/tp";
const signInUrl = "https://66.42.43.41:6443/api/v1/namespaces/ns-project/services/batch-visualizer-auth-service/signin";
const signUpUrl = "https://66.42.43.41:6443/api/v1/namespaces/ns-project/services/batch-visualizer-auth-service/signup";
const getUserInfoUrl = "https://66.42.43.41:6443/api/v1/namespaces/ns-project/services/batch-visualizer-auth-service/getUserInfo"
*/

/*const batchListUrl = "https://66.42.43.41:31798/tp";
const signInUrl = "https://66.42.43.41:32650/signin";
const signUpUrl = "https://66.42.43.41:32650/signup";
const getUserInfoUrl = "https://66.42.43.41:32650/getUserInfo"
*/
const providerUrl = "http://66.42.43.41:31700";
const batchHistoryListUrl = providerUrl + "/exechistory";
const batchListUrl = "http://66.42.43.41:31700/tp";
const signInUrl = "http://66.42.43.41:31700/auth/signin";
const signUpUrl = "http://66.42.43.41:31700/auth/signup";
const deleteAccountUrl = "http://66.42.43.41:31700/auth/member";
const setUserInfoUrl = "http://66.42.43.41:31700/auth/member";
const getUserInfoUrl = "http://66.42.43.41:31700/auth/member";
const getAllUserInfoUrl = "http://66.42.43.41:31700/auth/admin/member";

 

// 전체 배치 히스토리 호출 API
export const getBatchHistoryListAll = () => {
    console.log("batchHistoryListUrl>>>>>>>>>>>>>>>" + batchHistoryListUrl);
    return axios.get(batchHistoryListUrl);
}

// 배치 개별 히스토리 호출 API
export const getBatchHistoryList = (masterBatchId) => {
    const url = batchHistoryListUrl + "/" + masterBatchId;
    return axios.get(url);
}

// 배치 전체 리스트 호출 API
export const getBatchList = () => {
    return axios.get(batchListUrl);
} 

// 배치 상세정보 호출 API
export const getBatchDetailInfo = (masterBatchId) => {
    const url = batchListUrl + "/" + masterBatchId
    return axios.get(url);
}

// 로그인 API
export const signIn = (user) => {
    return axios.post(signInUrl, JSON.stringify(user), 
        { headers: {'Content-type': 'application/json'}} )
}

// 회원가입 API
export const signUp = (user) => {
    return axios.post(signUpUrl, JSON.stringify(user), 
        { headers: {'Content-type': 'application/json'}} )
}

// 회원탈퇴 API
export const deleteAccount = (user) => {
    const url = deleteAccountUrl + "/" + user.userId;
    return axios.delete(url, 
        { headers: { 'X-AUTH-TOKEN' : window.localStorage.getItem('token'),
                     'Content-type' : 'application/json'}});
}

// USER 정보 업데이트 API
export const setUserInfo = (user) => {
    const url = setUserInfoUrl + "/" + user.userId;
    return axios.put(url, JSON.stringify(user), 
        { headers: { 'X-AUTH-TOKEN' : window.localStorage.getItem('token'),
                     'Content-type' : 'application/json'}});
}

// 접속한 user의 token정보로 USER 정보 조회 or 웹페이지 접속 시 token 체크
export const getUserInfoByToken = () => {
    console.log('token>>>>' + window.localStorage.getItem('token'));
    return axios.get(getUserInfoUrl, 
        { headers: { 'X-AUTH-TOKEN' : window.localStorage.getItem('token'),
                     'Content-type' : 'application/json'}});
}

// userId로 USER정보 조회
export const getUserInfoByUserId = (userId) => {
    const url = getUserInfoUrl + '/' + userId;
    return axios.get(url, 
        { headers: { 'X-AUTH-TOKEN' : window.localStorage.getItem('token'),
                     'Content-type' : 'application/json'}});

}

// 전체 USER 정보 조회
export const getAllUserInfo = () => {
    return axios.get(getAllUserInfoUrl,
        { headers: { 'X-AUTH-TOKEN': window.localStorage.getItem('token'),
                     'Content-type': 'applicaion/json'}});
}