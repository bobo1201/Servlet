// async는 함수 선언시 사용, 해당 함수가 비동기 처리를 위한 함수임을 명시
// await는 async 함수 내에서 비동기 호출하는 부분에 사용
async function get1(bno){
    
    const result = await axios.get(`/replies/list/${bno}`)

    // console.log(result)
    //
    // return result.data

    return result
}


// getList() 함수의 예시
// bno : 현재 게시물 번호, page : 페이지 번호, size : 페이지당 사이즈, goLast : 마지막 페이지 호출 여부
// 새로운 댓글이 등록되어도 화면에는 아무런 변화가 없는 문제 발생
// 페이징 처리가 되면 새로운 댓글이 마지막 페이지에 있으므로 댓글된 결과를 볼 수 없다는 문제 생김
async function getList({bno, page, size, goLast}){
    const result = await axios.get(`/replies/list/${bno}`, {params:{page, size}})

    // 맨 마지막 댓글부터 출력되도록 만들기
    if(goLast){
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))
        return getList({bno:bno, page:lastPage, size:size})
    }

    return result.data
}


// axios.post 이용
async function addReply(replyObj){
    const response = await axios.post(`/replies/`, replyObj)
    
    return response.data
}

async function getReply(rno){
    const response = await axios.get(`/replies/${rno}`)
    return response.data
}

async function modifyReply(replyObj){
    const response = await axios.put(`/replies/${replyObj.rno}`, replyObj)
    return response.data
}

async function removeReply(rno){
    const response = await axios.delete(`/replies/${rno}`)
    return response.data
}
