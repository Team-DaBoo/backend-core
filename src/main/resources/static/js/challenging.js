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



