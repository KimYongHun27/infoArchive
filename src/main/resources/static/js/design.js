const arrowButtons = document.querySelectorAll('.arrow-btn');

arrowButtons.forEach(button => {

  button.addEventListener('mouseenter', () => {
    button.style.transform = 'scale(1.08)';
  });

  button.addEventListener('mouseleave', () => {
    button.style.transform = 'scale(1)';
  });

});