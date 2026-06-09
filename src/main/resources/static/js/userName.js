const loadEvent = async () =>{
  const res = await fetch(`api/auth/userinfo`, {
    method: "GET",
    headers:{
      "Content-Type": "application/json"
    }
  })

  if (!res.ok) {
    console.log("잘못")
    location.href="/login"
  }else {
    const data = await res.json();
    document.querySelector("#user").textContent = `${data.userName}님`;
  }
}

window.addEventListener("DOMContentLoaded", ()=>{
  loadEvent();
})