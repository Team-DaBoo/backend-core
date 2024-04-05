// const navItems = document.querySelectorAll(".nav-item");
// navItems.forEach((navItem, i) => {
//   navItem.addEventListener("click", () => {
//     navItems.forEach((item, j) => {
//       item.className = "nav-item";
//     });
//     navItem.className = "nav-item active";
//   });
// });

document.addEventListener("DOMContentLoaded", function() {
    const navItems = document.querySelectorAll('.nav-item');
    const currentPath = window.location.pathname;

    navItems.forEach(function(item) {
        item.addEventListener('click', function(e) {
            // Prevent default anchor behavior
            e.preventDefault();

            // Add active class to clicked nav-item
            window.location.href = this.querySelector('a').getAttribute('href');
        });

        if (currentPath.startsWith(item.querySelector('a').getAttribute('href'))) {
            item.classList.add('active');
        } else {
            item.classList.remove('active');
        }
    });
});

function submitFormWithAction(action) {
    let confirmed = confirm("폼을 제출하시겠습니까?");
    if (!confirmed) {
        return confirmed;
    }
    let form = document.getElementById('actionForm');
    form.action = action; // 폼의 action 속성을 동적으로 변경
    form.submit(); // 폼 제출
}

document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('figure.media').forEach(media => {
        const oembed = media.querySelector('oembed');
        if (!oembed) {
            // oembed 요소가 없으면 다음 media로 넘어갑니다.
            return;
        }
        const src = oembed.getAttribute('url');
        const iframe = document.createElement('iframe');

        iframe.setAttribute('src', src);
        iframe.setAttribute('width', '560');
        iframe.setAttribute('height', '315');
        iframe.setAttribute('frameborder', '0');
        iframe.setAttribute('allow', 'accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share');
        iframe.setAttribute('referrerpolicy', 'strict-origin-when-cross-origin');
        iframe.setAttribute('allowfullscreen', '');

        media.parentNode.replaceChild(iframe, media);
    });
});

function movePage(page) {
    document.getElementById('page').value = page;
    document.getElementById('actionForm').submit();
}

// 현재 날짜를 가져오는 함수
// function getCurrentDate() {
//     var today = new Date();
//     var year = today.getFullYear();
//     var month = (today.getMonth() + 1).toString().padStart(2, '0'); // 1월부터 9월까지 앞에 0을 붙임
//     var day = today.getDate().toString().padStart(2, '0'); // 1일부터 9일까지 앞에 0을 붙임
//     return year + '-' + month + '-' + day;
// }
//
// // 최근 일주일 전 날짜를 계산하는 함수
// function getOneWeekAgoDate() {
//     var oneWeekAgo = new Date();
//     oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
//     var year = oneWeekAgo.getFullYear();
//     var month = (oneWeekAgo.getMonth() + 1).toString().padStart(2, '0');
//     var day = oneWeekAgo.getDate().toString().padStart(2, '0');
//     return year + '-' + month + '-' + day;
// }
//
// // 시작일과 종료일 입력 필드에 값을 설정하는 함수
// function setDefaultDateValues() {
//     var startDateInput = document.getElementById('startDate');
//     var endDateInput = document.getElementById('endDate');
//     startDateInput.value = getOneWeekAgoDate(); // 시작일은 최근 일주일 전
//     endDateInput.value = getCurrentDate(); // 종료일은 현재 날짜
// }
//
// // 페이지 로드 시 기본 날짜 값을 설정
// window.onload = function() {
//     setDefaultDateValues();
// };
