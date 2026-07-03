const button = document.querySelector(".submitbutton");


const login = async () => {
  const bId = document.querySelector("#bId").value;
  const pw = document.querySelector("#pw").value;

  const res = await fetch("/api/auth/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      bId: bId,
      pw: pw
    })
  });

  const data = await res.json();

  console.log("status:", res.status);

  if (!res.ok) {
    alert(data.message || "로그인 실패");
    return;
  }

  location.href = data.redirectUrl;
};

button.addEventListener("click", () => {
  login();
});