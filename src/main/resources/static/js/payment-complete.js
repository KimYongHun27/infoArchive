// 아이콘
lucide.createIcons();

// 타이머
let total = 3600;

setInterval(()=>{
  if(total <= 0) return;

  total--;

  let h = Math.floor(total/3600);
  let m = Math.floor((total%3600)/60);
  let s = total%60;

  document.getElementById('h').innerText = h;
  document.getElementById('m').innerText = m;
  document.getElementById('s').innerText = s;

},1000);