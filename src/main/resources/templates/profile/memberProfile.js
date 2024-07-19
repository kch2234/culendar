let isLogin = [[${#authorization.expression('isAuthenticated()')}]];
console.log("isLogin : " + isLogin);

let memberIdVal = [[${member.id}]];

console.log("memberIdVal : " + memberIdVal);

// 팔로잉 페이징 관련

let followingStart = 0;
let loadingFollowing = true;

let followerStart = 0;
let loadingFollower = true;

// 필터링 페이징 관련
let myFilterStart = 0;
let myFilterLoading = true;
let myFilterType = "MYREVIEW";
let myFilterPage = 0;
const myFilterSize = 4;

// ********* 페이지 로딩시 함수 **********

// 팔로잉 인원수 가져오기
getFollowingNum();
// 팔로워 인원수 가져오기
getFollowerNum();

// ********** 클릭시 이벤트 **********

// 팔로잉 버튼 클릭시 이벤트
$("#followNum").on("click", function () {
    console.log("팔로잉 버튼 클릭");
    followingStart = 0;
    getFollowingListWithpaging();
});

// 팔로워 버튼 클릭시 이벤트
$("#followerNum").on("click", function () {
    console.log("팔로워 버튼 클릭");
    followerStart = 0;
    getFollowerListWithpaging();
});

// 팔로잉 모달 내에 언팔로우 버튼 클릭시 이벤트
$("#followingListModalBody").on("click", ".unFollowBtn", function () {
    console.log("언팔로우 버튼 클릭");
    let unfollowId = $(this).data("unfollowid");
    console.log("unfollowId : " + unfollowId);

    let thisBtn = $(this);

    let data = {
        memberId: memberIdVal,
        followId: unfollowId,
    };

    $.ajax({
        type: "POST",
        url: "/follows/follow/" + unfollowId,
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            console.log(result);
            if (result === "unfollow") {
                thisBtn.text("Follow");
                thisBtn.toggleClass("btn-dark", false);
                thisBtn.toggleClass("btn-outline-secondary", true);
            } else {
                thisBtn.text("UnFollow");
                thisBtn.toggleClass("btn-outline-secondary", false);
                thisBtn.toggleClass("btn-dark", true);
            }
        },
        error: function (error) {
            console.log(error);
            alert("팔로우 추가에 실패했습니다.");
        },
    });
});

// 팔로워 모달 내에 팔로우 버튼 클릭시 이벤트 구현
$("#followerListModalBody").on("click", ".unFollowBtn", function () {
    console.log("버튼 클릭");
    let unfollowId = $(this).data("unfollowid");
    console.log("unfollowId : " + unfollowId);

    let thisBtn = $(this);

    let data = {
        memberId: memberIdVal,
        followId: unfollowId,
    };

    $.ajax({
        type: "POST",
        url: "/follows/follow/" + unfollowId,
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (result) {
            console.log(result);
            if (result === "unfollow") {
                thisBtn.text("Follow");
                thisBtn.toggleClass("btn-dark", false);
                thisBtn.toggleClass("btn-outline-secondary", true);
            } else {
                thisBtn.text("UnFollow");
                thisBtn.toggleClass("btn-outline-secondary", false);
                thisBtn.toggleClass("btn-dark", true);
            }
        },
        error: function (error) {
            console.log(error);
            alert("팔로우 추가에 실패했습니다.");
        },
    });
});

// ********** 각종 함수들 **********

// 팔로잉 하고 있는 인원수 가져오기
function getFollowingNum() {
    $.ajax({
        type: "GET",
        url: "/follows/" + memberIdVal + "/following",
        success: function (follow) {
            console.log("팔로잉 인원수 가져오기 성공");
            console.log(follow);
            $("#followNum").text(follow.length + "명");
        },
        error: function (e) {
            console.log("팔로잉 인원수 가져오기 실패");
            console.log(e);
        },
    });
}

// 회원을 팔로우 하고 있는 인원수 가져오기
function getFollowerNum() {
    $.ajax({
        type: "GET",
        url: "/follows/" + memberIdVal + "/follower",
        success: function (follower) {
            console.log("팔로워 인원수 가져오기 성공");
            console.log(follower);
            $("#followerNum").text(follower.length + "명");
        },
        error: function (e) {
            console.log("팔로워 인원수 가져오기 실패");
            console.log(e);
        },
    });
}

// 팔로잉 리스트 가져오기
function getFollowingListWithpaging() {
    if (loadingFollowing) {
        $.ajax({
            type: "GET",
            url: "/follows/" + memberIdVal + "/following/" + followingStart,
            success: function (follow) {
                console.log("팔로잉 리스트 가져오기 성공");
                console.log(follow);

                if (follow.length === 0) {
                    console.log("팔로잉 리스트가 없습니다.");
                    loadingFollowing = false;

                    if (followingStart === 0) {
                        printEmptyFollowingList();
                    }
                } else {
                    // 리스트
                    printFollowingList(follow);
                }
            },
            error: function (e) {
                console.log("팔로잉 리스트 가져오기 실패");
                console.log(e);
            },
        });
    }
}

// 팔로워 리스트 가져오기
function getFollowerListWithpaging() {
    if (loadingFollower) {
        $.ajax({
            type: "GET",
            url: "/follows/" + memberIdVal + "/follower/" + followerStart,
            success: function (follower) {
                console.log("팔로워 리스트 가져오기 성공");
                console.log(follower);

                if (follower.length === 0) {
                    console.log("팔로워 리스트가 없습니다.");
                    loadingFollower = false;

                    if (followerStart === 0) {
                        printEmptyFollowerList();
                    }
                } else {
                    // 리스트
                    printFollowerList(follower);
                }
            },
            error: function (e) {
                console.log("팔로워 리스트 가져오기 실패");
                console.log(e);
            },
        });
    }
}

// 팔로잉 리스트 출력하기
function printFollowingList(follow) {
    let followingListModalBody = $("#followingListModalBody");
    let html = "";
    for (let i = 0; i < follow.length; i++) {
        html += '<div class="row mt-2">';
        html += '<div class="col-2">';
        html +=
            '<i class="fa-solid fa-circle-user fa-2x d-flex justify-content-center align-items-center"></i>';
        html += "</div>";
        html += '<div class="col">';
        html +=
            '<div class="row d-flex justify-content-between align-items-center">';
        html += '<div class="col-auto">';

        html += '<div class="input-group">';
        // 버튼 클릭시 해당 회원의 프로필 페이지로 이동
        html +=
            '<button type="button" class="btn btn-outline-secondary" onclick="location.href=\'/members/' +
            follow[i].followDTO.id +
            "'\">" +
            follow[i].followDTO.username +
            "</button>";
        let locationString = locationEnumToString(follow[i].followDTO.location);
        html += '<span class="input-group-text">' + locationString + "</span>";
        html += "</div>";
        html += "</div>";
        html += '<div class="col-auto">';
        html += '<div class="input-group">';
        if (isLogin) {
            html +=
                '<button type="button" class="btn btn-dark unFollowBtn" data-unfollowId="' +
                follow[i].followDTO.id +
                '">UnFollow</button>';
        }
        html += "</div>";
        html += "</div>";
        html += "</div>";
        html += "</div>";
        html += "</div>";
    }
    // 팔로잉 리스트 html 추가하기
    followingListModalBody.append(html);
}

// 팔로워 리스트 출력하기
function printFollowerList(follow) {
    let followerListModalBody = $("#followerListModalBody");
    let html = "";
    for (let i = 0; i < follow.length; i++) {
        html += '<div class="row mt-2">';
        html += '<div class="col-2">';
        html +=
            '<i class="fa-solid fa-circle-user fa-2x d-flex justify-content-center align-items-center"></i>';
        html += "</div>";
        html += '<div class="col">';
        html +=
            '<div class="row d-flex justify-content-between align-items-center">';
        html += '<div class="col-auto">';
        html += '<div class="input-group">';
        html +=
            '<button type="button" class="btn btn-outline-secondary" onclick="location.href=\'/members/' +
            follow[i].memberDTO.id +
            "'\">" +
            follow[i].memberDTO.username +
            "</button>";
        let locationString = locationEnumToString(follow[i].memberDTO.location);
        html += '<span class="input-group-text">' + locationString + "</span>";
        html += "</div>";
        html += "</div>";
        html += '<div class="col-auto">';
        html += '<div class="input-group">';

        if (isLogin) {
            // 팔로우 여부에 따라 버튼 변경
            if (follow[i].isFollow) {
                html +=
                    '<button type="button" class="btn btn-dark unFollowBtn" data-unfollowId="' +
                    follow[i].memberDTO.id +
                    '">UnFollow</button>';
            } else {
                html +=
                    '<button type="button" class="btn btn-outline-secondary unFollowBtn" data-unfollowId="' +
                    follow[i].memberDTO.id +
                    '">Follow</button>';
            }
        }
        html += "</div>";
        html += "</div>";
        html += "</div>";
        html += "</div>";
        html += "</div>";
    }
    // 팔로잉 리스트 html 추가하기
    followerListModalBody.append(html);
}

// 빈 팔로잉 리스트 출력하기
function printEmptyFollowingList() {
    let followingListModalBody = $("#followingListModalBody");
    let html = "";
    html += '<div class="row mt-2">';
    html += '<div class="col">';
    html += '<div class="input-group">';
    html += '<span class="input-group-text">팔로잉 리스트가 없습니다.</span>';
    html += "</div>";
    html += "</div>";
    html += "</div>";

    // 팔로잉 리스트 html 추가하기
    followingListModalBody.append(html);
}

// 빈 팔로워 리스트 출력하기
function printEmptyFollowerList() {
    let followerListModalBody = $("#followerListModalBody");
    let html = "";
    html += '<div class="row mt-2">';
    html += '<div class="col">';
    html += '<div class="input-group">';
    html += '<span class="input-group-text">팔로워가 없습니다.</span>';
    html += "</div>";
    html += "</div>";
    html += "</div>";

    // 팔로잉 리스트 html 추가하기
    followerListModalBody.append(html);
}

// 팔로잉 모달이 닫힐 때 팔로잉 리스트 초기화
$("#followList-modal").on("hidden.bs.modal", function () {
    $("#followingListModalBody").empty();
    followingStart = 0;
    loadingFollowing = true;
    getFollowingNum();
    getFollowerNum();
});

// 팔로워 모달이 닫힐 때 팔로잉 리스트 초기화
$("#followList-modal").on("hidden.bs.modal", function () {
    $("#followerListModalBody").empty();
    followerStart = 0;
    loadingFollower = true;
    getFollowingNum();
    getFollowerNum();
});

// 팔로잉 모달내에  스크롤이 바닥에 닿았을 때
$("#followingListModalBody").on("scroll", function () {
    console.log("스크롤 이벤트 발생");
    let scrollHeight = $(this)[0].scrollHeight;
    let scrollTop = $(this).scrollTop();
    let followingListModalBodyHeight = $("#followingListModalBody").height();
    let padding_top = $("#followingListModalBody").css("padding-top");
    let padding_bottom = $("#followingListModalBody").css("padding-bottom");

    // padding-top, padding-bottom 값 제거
    padding_top = padding_top.replace("px", "");
    padding_bottom = padding_bottom.replace("px", "");
    // padding-top, padding-bottom 값 int로 변환
    padding_top = parseInt(padding_top);
    padding_bottom = parseInt(padding_bottom);
    followingListModalBodyHeight =
        followingListModalBodyHeight + padding_top + padding_bottom;

    // modal-body

    if (scrollTop + followingListModalBodyHeight + 1 >= scrollHeight) {
        console.log("스크롤이 바닥에 닿았습니다.");
        followingStart += 20;
        getFollowingListWithpaging();
    }
});

// 팔로워 모달내에  스크롤이 바닥에 닿았을 때
$("#followerListModalBody").on("scroll", function () {
    console.log("스크롤 이벤트 발생");
    let scrollHeight = $(this)[0].scrollHeight;
    let scrollTop = $(this).scrollTop();
    let followierListModalBodyHeight = $("#followerListModalBody").height();
    let padding_top = $("#followerListModalBody").css("padding-top");
    let padding_bottom = $("#followerListModalBody").css("padding-bottom");

    // padding-top, padding-bottom 값 제거
    padding_top = padding_top.replace("px", "");
    padding_bottom = padding_bottom.replace("px", "");
    // padding-top, padding-bottom 값 int로 변환
    padding_top = parseInt(padding_top);
    padding_bottom = parseInt(padding_bottom);
    followierListModalBodyHeight =
        followierListModalBodyHeight + padding_top + padding_bottom;

    // modal-body

    if (scrollTop + followierListModalBodyHeight + 1 >= scrollHeight) {
        console.log("스크롤이 바닥에 닿았습니다.");
        followerStart += 20;
        getFollowerListWithpaging();
    }
});

// 지역 ENUM 값 변환
function locationEnumToString(locationEnum) {
    switch (locationEnum) {
        case "SEOUL":
            return "서울";
        case "BUSAN":
            return "부산";
        case "DAEGU":
            return "대구";
        case "INCHEON":
            return "인천";
        case "GWANGJU":
            return "광주";
        case "DAEJEON":
            return "대전";
        case "ULSAN":
            return "울산";
        case "SEJONG":
            return "세종";
        case "GYEONGGI":
            return "경기";
        case "KANGWON":
            return "강원";
        case "CHUNGBUK":
            return "충북";
        case "CHUNGNAM":
            return "충남";
        case "JEONBUK":
            return "전북";
        case "JEONNAM":
            return "전남";
        case "GYEONGBUK":
            return "경북";
        case "GYEONGNAM":
            return "경남";
        case "JEJU":
            return "제주";
        default:
            return "지역";
    }
}

// ********** 필터 관련 함수 **********

// ********** 메인 필터 클릭시

// 나 버튼 클릭시
$("#myBtn").on("click", function () {
    console.log("나 버튼 클릭");
    let html = "";
    html += '<div class="col">';
    html +=
        '<button type="button" class="btn btn-outline-secondary w-100 fs-6" id="myReviewBtn">후기</button>';
    html += "</div>";
    html += '<div class="col">';
    html +=
        '<button type="button" class="btn btn-outline-secondary w-100 fs-6" id="myInfoBtn">정보</button>';
    html += "</div>";
    html += '<div class="col">';
    html +=
        '<button type="button" class="btn btn-outline-secondary w-100 fs-6" id="myProgramBtn">작품</button>';
    html += "</div>";
    $("#activeFilterContainer").html(html);

    $("#myBtn")
        .toggleClass("btn-outline-secondary", false)
        .toggleClass("btn-secondary", true);
    $("#bkMarkBtn")
        .toggleClass("btn-secondary", false)
        .toggleClass("btn-outline-secondary", true);
    $("#timeLineBtn")
        .toggleClass("btn-secondary", false)
        .toggleClass("btn-outline-secondary", true);
});

// 나 버튼 세부 필터 클릭시

// 나 후기 버튼 클릭시
$("#activeFilterContainer").on("click", "#myReviewBtn", function () {
    console.log("나 후기 버튼 클릭");
    $("#myReviewBtn")
        .toggleClass("btn-outline-secondary", false)
        .toggleClass("btn-secondary", true);
    $("#myInfoBtn")
        .toggleClass("btn-secondary", false)
        .toggleClass("btn-outline-secondary", true);
    $("#myProgramBtn")
        .toggleClass("btn-secondary", false)
        .toggleClass("btn-outline-secondary", true);
    $("#activeFilterName").text("후기");
    $("#content-area").empty();
    myFilterStart = 0;
    myFilterLoading = true;
    myFilterType = "MYREVIEW";
    getListWithPaging(myFilterType);
});

// 나 정보 버튼 클릭시
$("#activeFilterContainer").on("click", "#myInfoBtn", function () {
    console.log("나 정보 버튼 클릭");
    $("#myReviewBtn")
        .toggleClass("btn-secondary", false)
        .toggleClass("btn-outline-secondary", true);
    $("#myInfoBtn")
        .toggleClass("btn-outline-secondary", false)
        .toggleClass("btn-secondary", true);
    $("#myProgramBtn")
        .toggleClass("btn-secondary", false)
        .toggleClass("btn-outline-secondary", true);
    $("#activeFilterName").text("정보");
    $("#content-area").empty();
    myFilterStart = 0;
    myFilterLoading = true;
    myFilterType = "MYINFO";
    getListWithPaging(myFilterType);
});

// 나 작품 버튼 클릭시
$("#activeFilterContainer").on("click", "#myProgramBtn", function () {
    console.log("나 작품 버튼 클릭");
    $("#myReviewBtn")
        .toggleClass("btn-secondary", false)
        .toggleClass("btn-outline-secondary", true);
    $("#myInfoBtn")
        .toggleClass("btn-secondary", false)
        .toggleClass("btn-outline-secondary", true);
    $("#myProgramBtn")
        .toggleClass("btn-outline-secondary", false)
        .toggleClass("btn-secondary", true);
    $("#activeFilterName").text("작품");
    $("#content-area").empty();
    myFilterStart = 0;
    myFilterLoading = true;
    myFilterType = "MYPROGRAM";
    getListWithPaging(myFilterType);
});

// ********** 필터링 관련 ajax 함수 **********

// 내 후기 리스트 무한 스크롤 페이징 가져오기
function getMyReviewListWithPaging() {
    if (myFilterLoading) {
        $.ajax({
            type: "GET",
            url:
                "/boards/findMyReviewWithPaging/" +
                memberIdVal +
                "/" +
                myFilterStart,
            success: function (reviews) {
                console.log("내 후기 리스트 가져오기 성공");
                console.log(reviews);

                if (reviews.length === 0) {
                    console.log("내 후기 리스트가 없습니다.");
                    myFilterLoading = false;

                    if (myFilterStart === 0) {
                        printEmptyContentAreaList();
                    }
                } else {
                    // 리스트
                    myFilterStart += 16;
                    printBoardsList(reviews);
                }
            },
            error: function (e) {
                console.log("내 후기 리스트 가져오기 실패");
                console.log(e);
            },
        });
    }
}

// 내 정보 리스트 무한 스크롤 페이징 가져오기
function getMyInfoListWithPaging() {
    if (myFilterLoading) {
        $.ajax({
            type: "GET",
            url:
                "/boards/findMyInfoWithPaging/" +
                memberIdVal +
                "/" +
                myFilterStart,
            success: function (infos) {
                console.log("내 정보 리스트 가져오기 성공");
                console.log(infos);

                if (infos.length === 0) {
                    console.log("내 정보 리스트가 없습니다.");
                    myFilterLoading = false;

                    if (myFilterStart === 0) {
                        printEmptyContentAreaList();
                    }
                } else {
                    // 리스트
                    myFilterStart += 16;
                    printBoardsList(infos);
                }
            },
            error: function (e) {
                console.log("내 정보 리스트 가져오기 실패");
                console.log(e);
            },
        });
    }
}

// 내 작품 평가 리스트 페이징 가져오기

function getMyProgramListWithPaging() {
    if (myFilterLoading) {
        $.ajax({
            type: "GET",
            url:
                "/bkmark/findMyProgramWithPaging/" +
                memberIdVal +
                "/" +
                myFilterPage +
                "/" +
                myFilterSize,
            success: function (programs) {
                console.log("내 북마크 작품 가져오기 성공");
                console.log(programs);

                if (programs.content.length === 0) {
                    console.log("내 작품 리스트가 없습니다.");
                    if (myFilterPage === 0) {
                        printEmptyContentAreaList();
                    }
                } else {
                    // 리스트
                    printProgramsList(programs.content);
                    // 다음 페이지를 위한 페이지 수 증가
                    myFilterPage += 1;
                }
            },
            error: function (e) {
                console.log("내 작품 리스트 가져오기 실패");
                console.log(e);
            },
        });
    }
}

// 내 게시글 리스트 출력하기
function printBoardsList(boards) {
    let contentArea = $("#content-area");
    let html = "";
    html += '<div class="row mt-5">';
    for (let i = 0; i < boards.length; i++) {
        html += '<div class="col-3">';
        html += '<div class="card">';
        html +=
            '<img src="' +
            boards[i].programDTO.thumbnail +
            '" class="card-img-top">';
        html += '<div class="card-body">';
        // 제목 글자수 10글자로 제한
        let title = boards[i].title;
        if (title.length > 10) {
            title = title.substring(0, 10) + "...";
        }
        html +=
            '<button class="card-title fs-5 btn btn-outline-secondary" onclick="location.href=\'/boards/' +
            boards[i].id +
            "'\">" +
            title +
            "</button>";

        // 내용 글자수 20글자로 제한
        let content = boards[i].content;
        if (content.length > 20) {
            content = content.substring(0, 20) + "...";
        }
        html += '<p class="card-text">' + content + "</p>";
        html += "</div>";
        html += "</div>";
        html += "</div>";
    }
    html += "</div>";
    // 내 후기 리스트 html 추가하기
    contentArea.append(html);
}

// 내 작품 리스트 출력하기
function printProgramsList(programs) {
    let contentArea = $("#content-area");
    let html = "";
    html += '<div class="row mt-5">';

    for (let i = 0; i < programs.length; i++) {
        let program = programs[i]; // 직접 ProgramDTO 객체에 접근

        // `thumbnail` 속성 확인
        let thumbnail = program.thumbnail
            ? program.thumbnail
            : "default-thumbnail.jpg";

        // 제목 글자수 10글자로 제한
        let title = program.title;
        if (title.length > 10) {
            title = title.substring(0, 10) + "...";
        }

        // 내용 글자수 20글자로 제한
        let content = program.description || ""; // `description` 속성이 없는 경우 빈 문자열로 대체
        if (content.length > 20) {
            content = content.substring(0, 20) + "...";
        }

        // 카드 HTML 생성
        html += '<div class="col-3">';
        html += '<div class="card">';
        html +=
            '<img src="' +
            thumbnail +
            '" class="card-img-top" alt="Program Thumbnail">';
        html += '<div class="card-body">';
        html +=
            '<button class="card-title fs-5 btn btn-outline-secondary" onclick="location.href=\'/programs/' +
            program.id +
            "'\">" +
            title +
            "</button>";
        html += '<p class="card-text">' + content + "</p>";
        html += "</div>";
        html += "</div>";
        html += "</div>";
    }

    html += "</div>";

    // 내 작품 리스트 html 추가하기
    contentArea.append(html);
}

// 빈 리스트일 경우 출력
function printEmptyContentAreaList() {
    let contentArea = $("#content-area");
    let html = '<div class="row mt-5">';
    html += '<div class="col text-center">';
    html += '<p class="text-muted">게시물이 없습니다.</p>';
    html += "</div>";
    html += "</div>";
    contentArea.html(html);
}

// 필터에 따른 리스트 불러오기
function getListWithPaging(filterType) {
    switch (filterType) {
        case "MYREVIEW":
            getMyReviewListWithPaging();
            break;
        case "MYINFO":
            getMyInfoListWithPaging();
            break;
        case "MYPROGRAM":
            getMyProgramListWithPaging();
            break;
    }
}

// 무한 스크롤 이벤트
$(document).scroll(function () {
    // 스크롤바의 위치가 문서의 맨 아래쪽에 위치할 경우
    let scrollTop = $(document).scrollTop();
    let documentHeight = $(document).height();
    let scrollHeight = $(window).height();
    console.log("scrollTop : " + scrollTop);
    console.log("documentHeight : " + documentHeight);
    console.log("windowHeight : " + scrollHeight);

    if (scrollTop + scrollHeight >= documentHeight - 1) {
        console.log("스크롤 이벤트 발생");
        getListWithPaging(myFilterType);
    }
});

// 문장형 평가 ENUM 값 변환
function ratingEnumToString(rating) {
    if (rating === 1) {
        return "매우 싫어요";
    } else if (rating === 2) {
        return "싫어요";
    } else if (rating === 3) {
        return "보통이에요";
    } else if (rating === 4) {
        return "좋아요";
    } else if (rating === 5) {
        return "매우 좋아요";
    } else {
        return "평가없음";
    }
}