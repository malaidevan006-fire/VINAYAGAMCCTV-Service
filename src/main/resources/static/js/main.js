// Scroll to top button
const scrollBtn = document.getElementById('scrollTopBtn');
window.addEventListener('scroll', () => {
    if (window.scrollY > 300) {
        scrollBtn.style.display = 'block';
    } else {
        scrollBtn.style.display = 'none';
    }
});

// Smooth scroll for anchor links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            e.preventDefault();
            target.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }
    });
});

// Counter animation for stats
function animateCounters() {
    document.querySelectorAll('.stat-item h3').forEach(counter => {
        const target = parseInt(counter.innerText.replace(/\D/g, ''));
        const suffix = counter.innerText.replace(/[0-9]/g, '');
        let current = 0;
        const increment = Math.ceil(target / 60);
        const timer = setInterval(() => {
            current += increment;
            if (current >= target) {
                counter.innerText = target + suffix;
                clearInterval(timer);
            } else {
                counter.innerText = current + suffix;
            }
        }, 30);
    });
}

// Trigger counter when stats section is visible
const statsSection = document.querySelector('.stats-section');
if (statsSection) {
    const observer = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting) {
            animateCounters();
            observer.disconnect();
        }
    });
    observer.observe(statsSection);
}

// Auto-dismiss alerts after 4 seconds
document.querySelectorAll('.alert-success-custom').forEach(alert => {
    setTimeout(() => {
        alert.classList.remove('show');
        alert.classList.add('fade');
    }, 4000);
});
