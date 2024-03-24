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
    var form = document.getElementById('actionForm');
    form.action = action; // 폼의 action 속성을 동적으로 변경
    form.submit(); // 폼 제출
}
