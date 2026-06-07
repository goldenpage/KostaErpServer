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

  console.log("status:", res.status);

  if (res.ok) {
    console.log("로그인 성공");
    bid = '';
    location.href = "/revenuestatistics";
  } else {
    console.log("로그인 실패");
    alert("로그인 실패");
  }
};

button.addEventListener("click", () => {
  login();
});