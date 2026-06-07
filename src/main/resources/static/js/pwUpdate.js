let pwPhoneVerified = false;

const sendPwPhoneCode = async () => {
  const bId = document.querySelector("#bId").value.trim();
  const name = document.querySelector("#name").value.trim();
  const phone = document.querySelector("#phone").value.trim();

  if (!bId || !name || !phone) {
    alert("아이디, 이름, 휴대폰 번호를 모두 입력해주세요.");
    return;
  }

  pwPhoneVerified = false;
  try {
    const res = await fetch(`http://127.0.0.1:15000/api/auth/pw/phone/code`, {
      method: "post",
      body: JSON.stringify(phone),

    });
    const data = await res.json();
  } catch (err){
    console.error(err)
  }

}

const verifyPwPhoneCode = async () => {
  const phone = document.querySelector("#phone").value.trim();
  const phoneCode = document.querySelector("#phoneCode").value.trim();

  if (!phoneCode) {
    alert("인증번호를 입력해주세요.");
    return;
  }

  try{
  const res = await fetch(`http://127.0.0.1:15000`, {
    method: "post",
    body: JSON.stringify(phoneCode)
  })

  const data = await res.json();

  } catch (err) {
    console.error(err)
  }

}

function checkPw() {
  const pw = document.querySelector("#pw").value;
  const pwConfirm = document.querySelector("#pwConfirm").value;

  if (!pwPhoneVerified) {
    alert("휴대폰 인증을 먼저 완료해주세요.");
    return false;
  }

  if (pw !== pwConfirm) {
    alert("비밀번호가 일치하지 않습니다.");
    return false;
  }

  return true;
}

document.querySelector("#bId").addEventListener("input", function () {
  pwPhoneVerified = false;
});
document.querySelector("#name").addEventListener("input", function () {
  pwPhoneVerified = false;
});
document.querySelector("#phone").addEventListener("input", function () {
  pwPhoneVerified = false;
});

document.querySelector("#pwUpdateBtn").addEventListener("click",()=>{
  checkPw()
})


document.querySelector("#sendPhoneBtn").addEventListener("click", () => {
  sendPwPhoneCode();
})

document.querySelector("#verifyBtn").addEventListener("click", () => {
verifyPwPhoneCode();
})

